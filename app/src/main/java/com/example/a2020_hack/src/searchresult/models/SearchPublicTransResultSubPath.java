package com.example.a2020_hack.src.searchresult.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SearchPublicTransResultSubPath {

    public SearchPublicTransResultSubPath(int trafficType, double distance, int sectionTime) {
        this.trafficType = trafficType;
        this.distance = distance;
        this.sectionTime = sectionTime;
    }

    public SearchPublicTransResultSubPath(int trafficType, double distance, int sectionTime, String startName, double startX, double startY, String endName, double endX, double endY, ArrayList<SearchPublicTransResultLane> lane) {
        this.trafficType = trafficType;
        this.distance = distance;
        this.sectionTime = sectionTime;
        this.startName = startName;
        this.startX = startX;
        this.startY = startY;
        this.endName = endName;
        this.endX = endX;
        this.endY = endY;
        this.lane = lane;
    }

    @SerializedName("trafficType")
    int trafficType;

    @SerializedName("distance")
    double distance;

    @SerializedName("sectionTime")
    int sectionTime;

    @SerializedName("startName")
    String startName;

    @SerializedName("startX")
    double startX;

    @SerializedName("startY")
    double startY;

    @SerializedName("endName")
    String endName;

    @SerializedName("endX")
    double endX;

    @SerializedName("endY")
    double endY;

    @SerializedName("lane")
    ArrayList<SearchPublicTransResultLane> lane;

    public int getTrafficType() {
        return trafficType;
    }

    public double getDistance() {
        return distance;
    }

    public int getSectionTime() {
        return sectionTime;
    }

    public String getStartName() {
        return startName;
    }

    public double getStartX() {
        return startX;
    }

    public double getStartY() {
        return startY;
    }

    public String getEndName() {
        return endName;
    }

    public double getEndX() {
        return endX;
    }

    public double getEndY() {
        return endY;
    }

    public ArrayList<SearchPublicTransResultLane> getLane() {
        return lane;
    }
}
