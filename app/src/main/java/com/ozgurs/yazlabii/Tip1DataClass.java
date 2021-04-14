package com.ozgurs.yazlabii;

public class Tip1DataClass {
    private int toplam_yolcu_sayisi;
    private String tarih;

    public Tip1DataClass(int toplam_yolcu_sayisi, String tarih){
        this.toplam_yolcu_sayisi = toplam_yolcu_sayisi;
        this.tarih = tarih;
    }

    public int getToplam_yolcu_sayisi() {
        return toplam_yolcu_sayisi;
    }

    public String getTarih() {
        return tarih;
    }

    public void setToplam_yolcu_sayisi(int toplam_yolcu_sayisi) {
        this.toplam_yolcu_sayisi = toplam_yolcu_sayisi;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }
}
