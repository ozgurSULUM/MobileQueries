package com.ozgurs.yazlabii;

public class Tip3DataClass {
    private String tarih;
    private int pickup_location;
    private int drop_location;
    private double trip_distance;

    public Tip3DataClass(String tarih, int pickup_location, int drop_location, double trip_distance) {
        this.tarih = tarih;
        this.pickup_location = pickup_location;
        this.drop_location = drop_location;
        this.trip_distance = trip_distance;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public int getPickup_location() {
        return pickup_location;
    }

    public void setPickup_location(int pickup_location) {
        this.pickup_location = pickup_location;
    }

    public int getDrop_location() {
        return drop_location;
    }

    public void setDrop_location(int drop_location) {
        this.drop_location = drop_location;
    }

    public double getTrip_distance() {
        return trip_distance;
    }

    public void setTrip_distance(double trip_distance) {
        this.trip_distance = trip_distance;
    }
}
