package com.ozgurs.yazlabii;

import java.io.Serializable;

public class Lat_Lng implements Serializable {
    double lat;
    double lng;

    public Lat_Lng(double lat, double lng){
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    @Override
    public String toString() {
        return "Lat_Lng{" +
                "lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}
