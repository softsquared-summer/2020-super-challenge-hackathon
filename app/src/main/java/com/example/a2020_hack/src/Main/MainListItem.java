package com.example.a2020_hack.src.Main;

public class MainListItem {
    private String srcName;
    private String srcDetail;
    private String destName;
    private String destDetail;

    public void setSrcLat(double srcLat) {
        this.srcLat = srcLat;
    }

    public void setSrcLng(double srcLng) {
        this.srcLng = srcLng;
    }

    public void setDestLat(double destLat) {
        this.destLat = destLat;
    }

    public void setDestLng(double destLng) {
        this.destLng = destLng;
    }

    public double getSrcLat() {
        return srcLat;
    }

    public double getSrcLng() {
        return srcLng;
    }

    public double getDestLat() {
        return destLat;
    }

    public double getDestLng() {
        return destLng;
    }

    private double srcLat;
    private double srcLng;
    private double destLat;
    private double destLng;

    public String getSrcName() {
        return srcName;
    }

    public String getSrcDetail() {
        return srcDetail;
    }

    public String getDestName() {
        return destName;
    }

    public String getDestDetail() {
        return destDetail;
    }

    public void setSrcName(String srcName) {
        this.srcName = "출발 | "+srcName;
    }

    public void setSrcDetail(String srcDetail) {
        this.srcDetail = srcDetail;
    }

    public void setDestName(String destName) {
        this.destName = "도착 | "+destName;
    }

    public void setDestDetail(String destDetail) {
        this.destDetail = destDetail;
    }

    public MainListItem(String srcName, String srcDetail, String destName, String destDetail, double srcLat, double srcLng, double destLat, double destLng) {
        this.srcName = "출발 | "+srcName;
        this.srcDetail = srcDetail;
        this.destName = "도착 | "+destName;
        this.destDetail = destDetail;
        this.srcLat = srcLat;
        this.srcLng = srcLng;
        this.destLat = destLat;
        this.destLng = destLng;
    }


}
