package com.cornez.tapbuttoncounter;

public class HI {
    private double mHI;
    private double T;
    private double R;
    private double c1 = -8.78469475556;
    private double c2 = 1.61139411;
    private double c3 = 2.33854883889;
    private double c4 = -0.14611605;
    private double c5 = -0.012308094;
    private double c6 = -0.0164248277778;
    private double c7 = 0.002211732;
    private double c8 = 0.00072546;
    private double c9 = -0.000003582;

    public HI(){}

    public void reset(){mHI=0;}

    public void calHI(double T, double R){
        mHI = c1 + c2*T + c3*R + c4*T*R + c5*T*T + c6*R*R + c7*T*T*R + c8*T*R*R + c9*T*T*R*R;
    }

    public Double getHI() {
        double buf = Math.round(mHI*10)/10.0;
        return buf;
    }
}