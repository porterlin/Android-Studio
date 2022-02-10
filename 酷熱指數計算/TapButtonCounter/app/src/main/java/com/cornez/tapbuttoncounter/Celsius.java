package com.cornez.tapbuttoncounter;

class  Celsius {
    private int  mCelsius;

    public Celsius(){
        mCelsius = 28;
    }

    public void reset(){
        mCelsius = 28;
    }

    public void addCelsius(){
        mCelsius++;
        if (mCelsius > 50)
            mCelsius = 15;
    }

    public void subCelsius(){
        mCelsius--;
        if (mCelsius < 15)
            mCelsius = 50;
    }

    public Integer getCelsius() {
        return  mCelsius;
    }

}
