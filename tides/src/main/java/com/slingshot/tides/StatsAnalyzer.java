package com.slingshot.tides;

import java.util.List;
import java.util.stream.Collectors;

public class StatsAnalyzer {

    public Stats analyze(List<WaterLevel> waterLevel){
        Double median = median(waterLevel);

        Double avg = avg(waterLevel);
        Double stdv  = stdev(waterLevel, avg);

        List<WaterLevel> highOutliers = waterLevel.stream().filter(a -> a.getData() > avg + 1.8*stdv ).collect(Collectors.toList());
        List<WaterLevel> lowOutliers = waterLevel.stream().filter(a -> a.getData() < avg - 1.8*stdv ).collect(Collectors.toList());

        return new Stats(avg, stdv, median, lowOutliers, highOutliers);
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
