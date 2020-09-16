package com.slingshot.tides;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient.Version;
import java.net.http.HttpResponse.BodyHandlers;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class DataLoader {
    private static List<WaterLevel> cache;
    private static List<WaterLevel> cacheTimestamp;
    private static Stats stats;

    public static  List<WaterLevel> getCache(){
        return cache;
    }

    public static  List<WaterLevel> getCacheTimestamp(){
        return cacheTimestamp;
    }

    public static Stats getStats(){
        return stats;
    }

    static{
        new DataLoader().load();
    }
    private void load() {

        final HttpClient client = HttpClient.newBuilder().version(Version.HTTP_2).build();
        final String URL = "https://api.tidesandcurrents.noaa.gov/api/prod/datagetter?begin_date=20200801%2015:00&end_date=20200830%2015:06&station=8594900&product=water_level&units=metric&time_zone=gmt&application=ports_screen&format=json&datum=MHW";
        final HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URL))
                .timeout(Duration.ofMinutes(1)).header("Content-Type", "application/json").GET().build();

        HttpResponse<String> response;
        cache = new ArrayList<WaterLevel>();
        cacheTimestamp = new ArrayList<WaterLevel>();


        try {
            response = client.send(request, BodyHandlers.ofString());
            System.out.println(response.statusCode());
            final String res = response.body();
            // System.out.println(res);
            final JsonArray arr = JsonParser.parseString(res).getAsJsonObject().get("data").getAsJsonArray();
            final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            for(final JsonElement je: arr){
                JsonObject jo = je.getAsJsonObject();
                Date d = sdf.parse(jo.get("t").getAsString());
                // Calendar.getInstance().setTimeZone(TimeZone.getTimeZone("EDT"));
                Float f = Float.parseFloat(jo.get("v").getAsString());
                // System.out.println(  d.toString() + " - " + f );
                WaterLevel wl = new WaterLevel(d, f);
                cache.add(wl);
                cacheTimestamp.add(wl);
            }
            Collections.sort(cache, (a, b) -> a.getData().compareTo(b.getData()) );
            Collections.sort(cacheTimestamp, (a, b) -> a.getTimeStamp().compareTo(b.getTimeStamp()) );

            System.out.println(arr.size() + " is the size of the data list");

            stats = new StatsAnalyzer().analyze(cache);

        } catch (IOException  | ParseException | InterruptedException e) {
            e.printStackTrace();
        }

        

    }

    public static void main(final String[] args){
        new DataLoader().load();
    }


}