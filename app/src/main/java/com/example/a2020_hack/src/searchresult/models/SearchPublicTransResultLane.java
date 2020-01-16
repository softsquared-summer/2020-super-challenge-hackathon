package com.example.a2020_hack.src.searchresult.models;

import com.google.gson.annotations.SerializedName;

public class SearchPublicTransResultLane {

    public SearchPublicTransResultLane(int busNo, int type, int busID) {
        this.busNo = busNo;
        this.type = type;
        this.busID = busID;
    }

    public SearchPublicTransResultLane(String name, int subwayCode) {
        this.name = name;
        this.subwayCode = subwayCode;
    }

    @SerializedName("busNo")
    int busNo;

    @SerializedName("type")
    int type;

    @SerializedName("busID")
    int busID;

    @SerializedName("name")
    String name;

    @SerializedName("subwayCode")
    int subwayCode;

    public int getBusNo() {
        return busNo;
    }

    public int getType() {
        return type;
    }

    public int getBusID() {
        return busID;
    }

    public String getName() {
        return name;
    }

    public int getSubwayCode() {
        return subwayCode;
    }
}
