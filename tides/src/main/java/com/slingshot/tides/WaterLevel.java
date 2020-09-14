package com.slingshot.tides;

import java.util.Date;

public class WaterLevel implements Comparable<WaterLevel>{

    private Date timeStamp;
    private Float data;

    public WaterLevel(Date timeStamp, Float data){
        this.timeStamp = timeStamp;
        this.data = data;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public Float getData() {
        return data;
    }

    public void setData(Float data) {
        this.data = data;
    }

    @Override
    public int compareTo(WaterLevel o) {
        return this.timeStamp.compareTo(o.getTimeStamp());
    }

    
}