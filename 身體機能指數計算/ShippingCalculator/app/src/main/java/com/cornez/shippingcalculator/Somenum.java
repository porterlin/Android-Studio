package com.cornez.shippingcalculator;

public class Somenum {

    // DATA MEMBERS
    private Integer height,activity;
    private Double weight,skg,arb,are,Caro,Kne,Age;

    public Somenum() {
        height = 0;
        activity = 0;
        Kne=0.0;
        Age=0.0;
        Caro = 0.0;
        weight = 0.0;
        arb = 0.0;
        are = 0.0;
        skg = 0.0;
    }

    public void setHeight (int cm){
        height = cm;
    }
    public void setWeight (double kg){
        weight = kg;
    }
    public void setActivity (int ex){
        activity = ex;
    }
    public void setAge (double age){
        Age = age;
    }
    public void setKne (double kne){
        Kne = kne;
    }

    public void computeW(boolean gen) {
        if(gen){
            skg=((double)(height-80))*0.7;//（身高 cm－80）×70﹪
        }else{
            skg=((double)(height-70))*0.6;//（身高 cm－70）×60﹪
        }
        arb=skg*0.9;
        are=skg*1.1;//標準體重 ×90﹪~ 標準體重 ×110﹪
        switch(activity){
            case 1 :
                if(skg<arb)
                    Caro=35*skg;
                else if(skg>are)
                    Caro=25*skg;
                else
                    Caro=30*skg;
                break;
            case 2 :
                if(skg<arb)
                    Caro=40*skg;
                else if(skg>are)
                    Caro=30*skg;
                else
                    Caro=35*skg;
                break;
            case 3 :
                if(skg<arb)
                    Caro=35*skg;
                else if(skg>are)
                    Caro=35*skg;
                else
                    Caro=40*skg;
                break;
            default :
        }
    }

    public Double getSkg() {
        return skg;
    }

    public Double getArb() {
        return arb;
    }

    public Double getAre() {
        return are;
    }

    public Double getCaro() {
        return Caro;
    }

    public Double getAge() {
        return Age;
    }

    public Double getKne() {
        return Kne;
    }

    public Double getWeight() {
        return weight;
    }

    public Integer getHeight() {
        return height;
    }

    public Integer getActivity() {
        return activity;
    }

    public boolean Enough() {
        if(weight!=0&&height!=0&&activity!=0)
            return true;
        return false;
    }

    public boolean EnoughC() {
        if(Age!=0&&Kne!=0)
            return true;
        return false;
    }

}
