package com.example.a2020_hack.src.searchresult.models;

import com.google.gson.annotations.SerializedName;

public class SearchPublicTransResultInfo {

    public SearchPublicTransResultInfo(double trafficDistance, int totalWalk, int totalTime, int payment, int busTransitCount, int subwayTransitCount, String mapObj, String firstStartStation, String lastEndStation, int totalStationCount, int busStationCount, int subwayStationCount, double totalDistance, int totalWalkTime) {
        this.trafficDistance = trafficDistance;
        this.totalWalk = totalWalk;
        this.totalTime = totalTime;
        this.payment = payment;
        this.busTransitCount = busTransitCount;
        this.subwayTransitCount = subwayTransitCount;
        this.mapObj = mapObj;
        this.firstStartStation = firstStartStation;
        this.lastEndStation = lastEndStation;
        this.totalStationCount = totalStationCount;
        this.busStationCount = busStationCount;
        this.subwayStationCount = subwayStationCount;
        this.totalDistance = totalDistance;
        this.totalWalkTime = totalWalkTime;
    }

    @SerializedName("trafficDistance")
    double trafficDistance;

    @SerializedName("totalWalk")
    int totalWalk;

    @SerializedName("totalTime")
    int totalTime;

    @SerializedName("payment")
    int payment;

    public void setPayment(int payment) {
        this.payment = payment;
    }

    @SerializedName("busTransitCount")
    int busTransitCount;

    @SerializedName("subwayTransitCount")
    int subwayTransitCount;

    @SerializedName("mapObj")
    String mapObj;

    @SerializedName("firstStartStation")
    String firstStartStation;

    @SerializedName("lastEndStation")
    String lastEndStation;

    @SerializedName("totalStationCount")
    int totalStationCount;

    @SerializedName("busStationCount")
    int busStationCount;

    @SerializedName("subwayStationCount")
    int subwayStationCount;

    @SerializedName("totalDistance")
    double totalDistance;

    @SerializedName("totalWalkTime")
    int totalWalkTime;

    public double getTrafficDistance() {
        return trafficDistance;
    }

    public int getTotalWalk() {
        return totalWalk;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public int getPayment() {
        return payment;
    }

    public int getBusTransitCount() {
        return busTransitCount;
    }

    public int getSubwayTransitCount() {
        return subwayTransitCount;
    }

    public String getMapObj() {
        return mapObj;
    }

    public String getFirstStartStation() {
        return firstStartStation;
    }

    public String getLastEndStation() {
        return lastEndStation;
    }

    public int getTotalStationCount() {
        return totalStationCount;
    }

    public int getBusStationCount() {
        return busStationCount;
    }

    public int getSubwayStationCount() {
        return subwayStationCount;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    public int getTotalWalkTime() {
        return totalWalkTime;
    }
}
