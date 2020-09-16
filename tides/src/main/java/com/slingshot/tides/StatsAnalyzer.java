package com.slingshot.tides;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StatsAnalyzer {

    public Stats analyze(List<WaterLevel> waterLevel){
        
        Double median = median(waterLevel);
        Double avg = avg(waterLevel);
        Double stdv  = stdev(waterLevel, avg);

        List<Float> percentiles = percentiles(waterLevel);

        List<WaterLevel> highOutliers = waterLevel.stream().filter(a -> a.getData() > avg + 1.8*stdv ).collect(Collectors.toList());
        List<WaterLevel> lowOutliers = waterLevel.stream().filter(a -> a.getData() < avg - 1.8*stdv ).collect(Collectors.toList());

        return new Stats(avg, stdv, median, lowOutliers, highOutliers, percentiles);
    }

    private List<Float> percentiles(List<WaterLevel> waterLevels){

        waterLevels.sort((a, b) -> a.getData().compareTo(b.getData()));
        Integer size = waterLevels.size();

        Float tenth = waterLevels.get(  (int)Math.floor(size * 0.10)).getData();
        Float twentyFive = waterLevels.get(  (int)Math.floor(size * 0.25)).getData();
        Float fifty = waterLevels.get(  (int)Math.floor(size * 0.50)).getData();
        Float seventyFive = waterLevels.get(  (int)Math.floor(size * 0.75)).getData();
        Float ninety = waterLevels.get(  (int)Math.floor(size * 0.90)).getData();

        List<Float> percentiles = new ArrayList<>();
        percentiles.add(tenth);
        percentiles.add(twentyFive);
        percentiles.add(fifty);
        percentiles.add(seventyFive);
        percentiles.add(ninety);

        return percentiles;

    }

    private Double median(List<WaterLevel> waterLevels){
        waterLevels.sort((a, b) -> a.getData().compareTo(b.getData()));
        return waterLevels.get(waterLevels.size()/2).getData().doubleValue();
    }

    private Double sum(List<WaterLevel> waterLevels){
        return waterLevels.stream().mapToDouble(WaterLevel::getData ).sum();
    }

    private Double avg(List<WaterLevel> waterLevels){
        return sum(waterLevels)/waterLevels.size();
    }

    private Double stdev(List<WaterLevel> waterLevels, Double average){
        
        Double sum = 0d;
        for(WaterLevel wl : waterLevels){
            sum += Math.pow(wl.getData() - average, 2);
        }
        return Math.sqrt(sum/waterLevels.size());
    }

}
