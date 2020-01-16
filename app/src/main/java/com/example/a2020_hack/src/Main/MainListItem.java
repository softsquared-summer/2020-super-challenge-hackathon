package com.example.a2020_hack.src.Main;

public class MainListItem {
    private String srcName;
    private String srcDetail;
    private String destName;
    private String destDetail;

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

    public MainListItem(String srcName, String srcDetail, String destName, String destDetail) {
        this.srcName = "출발 | "+srcName;
        this.srcDetail = srcDetail;
        this.destName = "도착 | "+destName;
        this.destDetail = destDetail;
    }
}
