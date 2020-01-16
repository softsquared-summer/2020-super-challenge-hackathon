package com.example.a2020_hack.src.searchresult.models;

public class SearchModel {

    int busNo;
    int positionI;
    int positionJ;
    int positionK;
    String startName;

    public SearchModel(int busNo, int positionI, int positionJ, int positionK, String startName) {
        this.busNo = busNo;
        this.positionI = positionI;
        this.positionJ = positionJ;
        this.positionK = positionK;
        this.startName = startName;
    }

    public String getStartName() {
        return startName;
    }

    public int getBusNo() {
        return busNo;
    }

    public int getPositionI() {
        return positionI;
    }

    public int getPositionJ() {
        return positionJ;
    }

    public int getPositionK() {
        return positionK;
    }
}
