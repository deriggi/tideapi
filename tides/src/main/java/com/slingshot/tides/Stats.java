package com.slingshot.tides;

import java.util.List;

public class Stats {

    private Double avg;
    private Double stdv;
    private Double median;
    private Integer lowOutlierCnt;
    private Integer highOutlierCnt;
    private Outliers outliers;

    public Double getAvg() {
        return avg;
    }

    public void setAvg(Double avg) {
        this.avg = avg;
    }

    public Double getStdv() {
        return stdv;
    }

    public void setStdv(Double stdv) {
        this.stdv = stdv;
    }

    public Double getMedian() {
        return median;
    }

    public void setMedian(Double median) {
        this.median = median;
    }

    public Integer getLowOutlierCnt() {
        return lowOutlierCnt;
    }

    public void setLowOutlierCnt(Integer lowOutlierCnt) {
        this.lowOutlierCnt = lowOutlierCnt;
    }

    public Integer getHighOutlierCnt() {
        return highOutlierCnt;
    }

    public void setHighOutlierCnt(Integer highOutlierCnt) {
        this.highOutlierCnt = highOutlierCnt;
    }

    private void setOutliers(List<WaterLevel> highs, List<WaterLevel> lows){
        Outliers outliers = new Outliers(highs, lows);
        this.outliers = outliers;
    }

    public Outliers getOutliers(){
        return this.outliers;
    }

    public Stats(Double avg, Double stdv, Double median, List<WaterLevel> lows, List<WaterLevel> highs) {
        this.avg = avg;
        this.stdv = stdv;
        this.median = median;
        this.lowOutlierCnt = lows.size();
        this.highOutlierCnt = highs.size();
        this.setOutliers(highs, lows);
    }


    
}
