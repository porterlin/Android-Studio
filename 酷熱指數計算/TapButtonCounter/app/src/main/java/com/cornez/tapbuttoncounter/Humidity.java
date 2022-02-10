package com.cornez.tapbuttoncounter;

public class Humidity {
    private int  mHumidity;

    public Humidity(){
        mHumidity = 50;
    }

    public void reset(){
        mHumidity = 50;
    }

    public void addHumidity(){
        mHumidity++;
        if (mHumidity > 90)
            mHumidity = 40;
    }

    public void subHumidity(){
        mHumidity--;
        if (mHumidity < 40)
            mHumidity = 90;
    }

    public Integer getHumidity() {
        return  mHumidity;
    }
}
