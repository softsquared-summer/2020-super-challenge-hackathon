package com.example.a2020_hack.src.searchresult.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SearchPublicTransResult {

    boolean isValid;

    public boolean isValid() {
        return isValid;
    }

    @SerializedName("pathType")
    int pathType;

    @SerializedName("info")
    SearchPublicTransResultInfo info;

    @SerializedName("subPath")
    ArrayList<SearchPublicTransResultSubPath> subPath;

    public ArrayList<SearchPublicTransResultSubPath> getSubPath() {
        return subPath;
    }

    public int getPathType() {
        return pathType;
    }

    public SearchPublicTransResultInfo getInfo() {
        return info;
    }

    public SearchPublicTransResult(int pathType, SearchPublicTransResultInfo info) {
        this.pathType = pathType;
        this.info = info;
    }

    public SearchPublicTransResult(int pathType, SearchPublicTransResultInfo info, ArrayList<SearchPublicTransResultSubPath> subPath) {
        this.pathType = pathType;
        this.info = info;
        this.subPath = subPath;
        this.isValid = true;
    }
}
