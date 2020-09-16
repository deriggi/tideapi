package com.slingshot.tides;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WaterLevelController {
    final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");


    @RequestMapping(value = "/waterlevel", method = RequestMethod.GET)
	public ResponseEntity<?> getWaterLevlels() throws Exception {
	    return ResponseEntity.ok(DataLoader.getCacheTimestamp());
    }

    @RequestMapping(value = "/waterlevel/stats", method = RequestMethod.GET)
	public ResponseEntity<?> getStats() throws Exception {
	    return ResponseEntity.ok(DataLoader.getStats());
    }

    @RequestMapping(value = "/waterlevel/{year}/{month}/{day}/{hh}/{mm}", method = RequestMethod.GET)
	public ResponseEntity<?> getWaterLevelAtTime(@PathVariable Integer year, @PathVariable Integer month, @PathVariable Integer day, @PathVariable Integer hh, @PathVariable Integer mm) throws Exception {
        
        StringBuilder sb=  new StringBuilder();
        sb.append(year).append("-").append(month).append("-")
        .append(day).append(" ").append(" ").append(hh).append(":").append(mm);
        
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        WaterLevel wl = new WaterLevel(sdf.parse(sb.toString()), null);

        List<WaterLevel> cache = DataLoader.getCacheTimestamp();
        Integer index = Collections.binarySearch(cache, wl);

        return ResponseEntity.ok(cache.get(index));
    }

    @RequestMapping(value = "/waterlevel/{year}/{month}/{day}/{hh}/{mm}/{numDays}", method = RequestMethod.GET)
	public ResponseEntity<?> getWaterLevelsRadius(@PathVariable Integer year, @PathVariable Integer month, @PathVariable Integer day, @PathVariable Integer hh, @PathVariable Integer mm, @PathVariable Integer numDays) throws Exception {
        
        StringBuilder sb=  new StringBuilder();
        sb.append(year).append("-").append(month).append("-")
        .append(day).append(" ").append(" ").append(hh).append(":").append(mm);
        
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        WaterLevel wl = new WaterLevel(sdf.parse(sb.toString()), null);

        List<WaterLevel> cache = DataLoader.getCacheTimestamp();
        Integer index = Collections.binarySearch(cache, wl);
        List<WaterLevel> sublist = cache.subList(index, index + numDays > cache.size()-1? cache.size()-1 : index + numDays );
    
        return ResponseEntity.ok(sublist);
    }
}
