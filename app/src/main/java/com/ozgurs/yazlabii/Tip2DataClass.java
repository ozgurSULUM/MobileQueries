package com.ozgurs.yazlabii;

public class Tip2DataClass {
    private String tarih;
    private double gidilen_yol;

    public Tip2DataClass(String tarih, double gidilen_yol) {
        this.tarih = tarih;
        this.gidilen_yol = gidilen_yol;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public double getGidilen_yol() {
        return gidilen_yol;
    }

    public void setGidilen_yol(double gidilen_yol) {
        this.gidilen_yol = gidilen_yol;
    }
}
