package com.slingshot.tides;

import java.util.List;

public class Outliers {

    private List<WaterLevel> highs;
    private List<WaterLevel> lows;

    public List<WaterLevel> getHighs() {
        return highs;
    }

    public void setHighs(List<WaterLevel> highs) {
        this.highs = highs;
    }

    public List<WaterLevel> getLows() {
        return lows;
    }

    public void setLows(List<WaterLevel> lows) {
        this.lows = lows;
    }

    public Outliers(List<WaterLevel> highs, List<WaterLevel> lows) {
        this.highs = highs;
        this.lows = lows;
    }
    
}
