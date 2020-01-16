package com.example.a2020_hack.src.searchresult.models;

public class PointSearchResult {

    int nonstopStation;
    int stationClass;
    String stationName;
    int stationID;
    double x;
    double y;
    String arsID;
    String ebid;

    public PointSearchResult(String stationName, int stationID, double x, double y, String arsID) {
        this.stationName = stationName;
        this.stationID = stationID;
        this.x = x;
        this.y = y;
        this.arsID = arsID;
    }

    public int getNonstopStation() {
        return nonstopStation;
    }

    public int getStationClass() {
        return stationClass;
    }

    public String getStationName() {
        return stationName;
    }

    public int getStationID() {
        return stationID;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public String getArsID() {
        return arsID;
    }

    public String getEbid() {
        return ebid;
    }
}
