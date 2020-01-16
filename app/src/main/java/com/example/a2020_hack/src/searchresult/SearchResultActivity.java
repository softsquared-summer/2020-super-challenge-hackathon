package com.example.a2020_hack.src.searchresult;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import com.example.a2020_hack.R;
import com.example.a2020_hack.src.Main.MainActivity;
import com.example.a2020_hack.src.base.BaseActivity;
import com.example.a2020_hack.src.searchresult.models.PointSearchResult;
import com.example.a2020_hack.src.searchresult.models.SearchModel;
import com.example.a2020_hack.src.searchresult.models.SearchPublicTransResult;
import com.example.a2020_hack.src.searchresult.models.SearchPublicTransResultInfo;
import com.example.a2020_hack.src.searchresult.models.SearchPublicTransResultLane;
import com.example.a2020_hack.src.searchresult.models.SearchPublicTransResultSubPath;
import com.odsay.odsayandroidsdk.API;
import com.odsay.odsayandroidsdk.ODsayData;
import com.odsay.odsayandroidsdk.ODsayService;
import com.odsay.odsayandroidsdk.OnResultCallbackListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

import static com.example.a2020_hack.src.Main.MainActivity.destLat;
import static com.example.a2020_hack.src.Main.MainActivity.destLng;
import static com.example.a2020_hack.src.Main.MainActivity.srcLat;
import static com.example.a2020_hack.src.Main.MainActivity.srcLng;

public class SearchResultActivity extends BaseActivity {

    private Context mContext;

    private ODsayService oDsayService;
    private OnResultCallbackListener onResultCallbackListener;
    private ArrayList<SearchPublicTransResult> mArrayList = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private SearchResultAdapter mSearchResultAdapter;

    private String arsID;
    private int busNo;
    private String busRouteID;

    boolean inadirection = false, inarrmsg1 = false;
    String adirection = null, arrmsg1 = null;
    ArrayList<String> mArrayLisyBusRouteId = new ArrayList<>();
    ArrayList<String> mArrayListarr = new ArrayList<>();
    ArrayList<String> mArrayListNextBus = new ArrayList<>();

    boolean busRouteId, busRouteNum, nextBus;
    String stringBusRouteId, stringBusRouteNum, stringNextBus;
    ArrayList<String> mArrayLisyBusId = new ArrayList<>();
    ArrayList<String> mArrayListBusNum = new ArrayList<>();

    ArrayList<SearchModel> mSearchModels = new ArrayList<>();
    private static int mSearchModelPosition = 0;

    private boolean loopFlag = true;
    private boolean loopFlag2 = true;
    private boolean loopFlag2End = false;

    ArrayList<PointSearchResult> pointSearchResults = new ArrayList<>();
    int searchCount = 0;
    boolean isStart = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        mContext = this;
        mRecyclerView = findViewById(R.id.searchResult_rv);
        mSearchResultAdapter = new SearchResultAdapter(mContext, mArrayList);
        mRecyclerView.setAdapter(mSearchResultAdapter);

        StrictMode.enableDefaults();

        oDsayService = ODsayService.init(this, getString(R.string.odsay_key));
        oDsayService.setReadTimeout(5000);
        oDsayService.setConnectionTimeout(5000);

        onResultCallbackListener = new OnResultCallbackListener() {
            @Override
            public void onSuccess(ODsayData oDsayData, API api) {
                hideProgressDialog();
                if(api == API.SEARCH_PUB_TRANS_PATH){
                    try {
                        System.out.println("대중교통 길찾기");
                        int pointDistance = oDsayData.getJson().getJSONObject("result").getInt("pointDistance"); // 목적지까지의 직선거리

                        for(int i=0; i<oDsayData.getJson().getJSONObject("result").getJSONArray("path").length(); i++){
                            JSONObject jsonObject = oDsayData.getJson().getJSONObject("result").getJSONArray("path").getJSONObject(i);
                            double trafficDistance = jsonObject.getJSONObject("info").getDouble("trafficDistance");
                            int totalWalk = jsonObject.getJSONObject("info").getInt("totalWalk");
                            int totalTime = jsonObject.getJSONObject("info").getInt("totalTime");
                            int payment = jsonObject.getJSONObject("info").getInt("payment");
                            int busTransitCount = jsonObject.getJSONObject("info").getInt("busTransitCount");
                            int subwayTransitCount = jsonObject.getJSONObject("info").getInt("subwayTransitCount");
                            String mapObj = jsonObject.getJSONObject("info").getString("mapObj");
                            String firstStartStation = jsonObject.getJSONObject("info").getString("firstStartStation");
                            String lastEndStation = jsonObject.getJSONObject("info").getString("lastEndStation");
                            int totalStationCount = jsonObject.getJSONObject("info").getInt("totalStationCount");
                            int busStationCount = jsonObject.getJSONObject("info").getInt("busStationCount");
                            int subwayStationCount = jsonObject.getJSONObject("info").getInt("subwayStationCount");
                            double totalDistance = jsonObject.getJSONObject("info").getDouble("totalDistance");
                            int totalWalkTime = jsonObject.getJSONObject("info").getInt("totalWalkTime");






                            SearchPublicTransResultInfo searchPublicTransResultInfo = new SearchPublicTransResultInfo(trafficDistance, totalWalk, totalTime, payment, busTransitCount, subwayTransitCount, mapObj, firstStartStation, lastEndStation, totalStationCount, busStationCount, subwayStationCount, totalDistance, totalWalkTime);
                            JSONArray jsonArray = jsonObject.getJSONArray("subPath");
                            ArrayList<SearchPublicTransResultSubPath> searchPublicTransResultSubPath = new ArrayList<>();
                            for(int j=0; j<jsonArray.length(); j++){
                                JSONObject jsonObject1 = jsonArray.getJSONObject(j);
                                if(jsonObject1.getInt("trafficType")==3){
                                    int trafficType = jsonObject1.getInt("trafficType");
                                    double distance = jsonObject1.getDouble("distance");
                                    int sectionTime = jsonObject1.getInt("sectionTime");
                                    searchPublicTransResultSubPath.add(new SearchPublicTransResultSubPath(trafficType, distance, sectionTime));
                                }
                                else if(jsonObject1.getInt("trafficType")==2){
                                    int trafficType = jsonObject1.getInt("trafficType");
                                    int distance = jsonObject1.getInt("distance");
                                    int sectionTime = jsonObject1.getInt("sectionTime");
                                    String startName = jsonObject1.getString("startName");
                                    double startX = jsonObject1.getDouble("startX");
                                    double startY = jsonObject1.getDouble("startY");
                                    String endName = jsonObject1.getString("endName");
                                    double endX = jsonObject1.getDouble("endX");
                                    double endY = jsonObject1.getDouble("endY");
                                    int busNo = jsonObject1.getJSONArray("lane").getJSONObject(0).getInt("busNo");
                                    int type = jsonObject1.getJSONArray("lane").getJSONObject(0).getInt("type");
                                    int busID = jsonObject1.getJSONArray("lane").getJSONObject(0).getInt("busID");

                                    double x1 = endX;
                                    double y1 = endY;

                                    double x2 = destLng;
                                    double y2 = MainActivity.destLat;

                                    double dis = distance(y1, x1, y2, x2,"meter");
                                    int won = 0;
                                    if(dis<2000) {
                                        won = 3800;
                                    }
                                    else {
                                        won +=(int) ((dis-2000)/132)* 100;
                                    }


                                    Log.i("TESTTESTx1", String.valueOf(x1));
                                    Log.i("TESTTESTy1", String.valueOf(y1));
                                    Log.i("TESTTESTx2", String.valueOf(x2));
                                    Log.i("TESTTESTy2", String.valueOf(y2));


                                    Log.i("TEST", String.valueOf(won));

                                    searchPublicTransResultInfo.setPayment(searchPublicTransResultInfo.getPayment()+won);


                                    SearchPublicTransResultLane searchPublicTransResultLane = new SearchPublicTransResultLane(busNo, type, busID);
                                    ArrayList<SearchPublicTransResultLane> mSearchPublicTransResultLane = new ArrayList<>();
                                    mSearchPublicTransResultLane.add(searchPublicTransResultLane);
                                    searchPublicTransResultSubPath.add(new SearchPublicTransResultSubPath(trafficType, distance, sectionTime, startName, startX, startY, endName, endX, endY, mSearchPublicTransResultLane));
                                }
                                else if(jsonObject1.getInt("trafficType")==1){
                                    int trafficType = jsonObject1.getInt("trafficType");
                                    int distance = jsonObject1.getInt("distance");
                                    int sectionTime = jsonObject1.getInt("sectionTime");
                                    String startName = jsonObject1.getString("startName");
                                    double startX = jsonObject1.getDouble("startX");
                                    double startY = jsonObject1.getDouble("startY");
                                    String endName = jsonObject1.getString("endName");
                                    double endX = jsonObject1.getDouble("endX");
                                    double endY = jsonObject1.getDouble("endY");
                                    String name = jsonObject1.getJSONArray("lane").getJSONObject(0).getString("name");
                                    int subwayCode = jsonObject1.getJSONArray("lane").getJSONObject(0).getInt("subwayCode");
                                    SearchPublicTransResultLane searchPublicTransResultLane = new SearchPublicTransResultLane(name, subwayCode);
                                    ArrayList<SearchPublicTransResultLane> mSearchPublicTransResultLane = new ArrayList<>();
                                    mSearchPublicTransResultLane.add(searchPublicTransResultLane);
                                    searchPublicTransResultSubPath.add(new SearchPublicTransResultSubPath(trafficType, distance, sectionTime, startName, startX, startY, endName, endX, endY, mSearchPublicTransResultLane));
                                }
                            }
                            mArrayList.add(new SearchPublicTransResult(jsonObject.getInt("pathType"), searchPublicTransResultInfo, searchPublicTransResultSubPath));
                        }
//                        GraphLoadLane("0:0@"+mArrayList.get(0).getInfo().getMapObj());
//                        System.out.println(mArrayList.get(0).getSubPath().get(1).getStartName()+"아아아아아아");

//                        SearchStation(mArrayList.get(1).getSubPath().get(1).getStartName());
                    } catch (JSONException e) {
                        e.printStackTrace();
                        System.out.println("에러: " + e.toString());
                    }
                    System.out.println("사이즈2: " + mArrayList.size());
                    for(int i=0; i<mArrayList.size(); i++) {
                        if (mArrayList.get(i).getPathType() == 2) {
                            for (int j = 0; j < mArrayList.get(i).getSubPath().size(); j++) {
                                if (mArrayList.get(i).getSubPath().get(j).getTrafficType() == 2) {
                                    for (int k = 0; k < mArrayList.get(i).getSubPath().get(j).getLane().size(); k++) {
                                        loopFlag = true;
                                        System.out.println("size : " + mArrayList.get(i).getSubPath().get(j).getLane().size());
                                        System.out.println("loop K: " + k);
                                        busNo = mArrayList.get(i).getSubPath().get(j).getLane().get(k).getBusNo();
                                        System.out.println("buisNo: " + busNo);
                                        mSearchModels.add(new SearchModel(busNo, i, j, k, mArrayList.get(i).getSubPath().get(j).getStartName()));
                                        System.out.println("startName: " + mArrayList.get(i).getSubPath().get(j).getStartName());
//                                            SearchStation(mArrayList.get(i).getSubPath().get(j).getStartName());
                                    }
                                }
                            }
                        }
                    }

                    Collections.sort(mArrayList, new Comparator<SearchPublicTransResult>() {
                        @Override
                        public int compare(SearchPublicTransResult left, SearchPublicTransResult right) {

                            return (int) (left.getInfo().getPayment() - right.getInfo().getPayment());
                        }
                    });

                    // 슬립 걸어줘야함?
//                        busNo = mArrayList.get(1).getSubPath().get(1).getLane().get(0).getBusNo();
                    mSearchResultAdapter.notifyDataSetChanged();
                }
                else if(api == API.LOAD_LANE){

                }
                else if(api == API.POINT_SEARCH){
                    try {
                        JSONArray jsonArray = oDsayData.getJson().getJSONObject("result").getJSONArray("station");
                        for(int i=jsonArray.length()-7; i<jsonArray.length(); i++){
                            String stationName = jsonArray.getJSONObject(i).getString("stationName");
                            int stationID = jsonArray.getJSONObject(i).getInt("stationID");
                            double x = jsonArray.getJSONObject(i).getDouble("x");
                            double y = jsonArray.getJSONObject(i).getDouble("y");
                            String arsID = jsonArray.getJSONObject(i).getString("arsID");
                            pointSearchResults.add(new PointSearchResult(stationName, stationID, x, y, arsID));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        System.out.println("에러: " + e.toString());
                    }
                    System.out.println("사이즈: " + pointSearchResults.size());
                    for(int i=0; i<pointSearchResults.size(); i++){
                        SearchPublicTransPath(srcLng+"", srcLat+"", Double.toString(pointSearchResults.get(i).getX()), Double.toString(pointSearchResults.get(i).getY()), "0", "0", "0");
                    }
                }
//                else if(api == API.SEARCH_STATION){
//                    try {
////                        System.out.println(oDsayData.getJson().getJSONObject("result").getJSONArray("station"));
//                        for(int i=0; i<oDsayData.getJson().getJSONObject("result").getJSONArray("station").length(); i++){
//                            System.out.println("가져오는 busNo: " + busNo);
//                            JSONObject jsonObject = oDsayData.getJson().getJSONObject("result").getJSONArray("station").getJSONObject(i);
//                            for(int j = 0; j<jsonObject.getJSONArray("businfo").length(); j++){
////                                System.out.println("busNo: " + jsonObject.getJSONArray("businfo").getJSONObject(j).getString("busNo"));
//                                if(jsonObject.getJSONArray("businfo").getJSONObject(j).getString("busNo").equals(busNo+"")){
//                                    loopFlag2 = true;
//                                    arsID = jsonObject.getString("arsID");
//                                    arsID = arsID.replace("-","");
////                                    System.out.println("arsID: " + arsID);
////                                    getBusRouteNum(arsID);
////                                    Thread th = new Thread(new Runnable() {
////                                        @Override
////                                        public void run() {
////                                            while (loopFlag2){
////
////                                            }
////                                        }
////                                    });
////                                    th.start();
//                                    return;
//                                }
//                                if(j==jsonObject.getJSONArray("businfo").length()-1){
//                                    loopFlag2End=true;
//                                }
//                            }
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }
            }

            @Override
            public void onError(int i, String s, API api) {
                hideProgressDialog();
                showCustomToast("네트워크 연결 에러입니다. " + s);
            }
        };

//        SearchPublicTransPath("126.926493082645", "37.6134436427887", "127.126936754911", "37.5004198786564", "0", "0", "0");
//        getBusRouteNum("12021");
        NearStation(destLng+"", destLat+"");

    }

    void SearchPublicTransPath(String startX, String startY, String endX, String endY, String opt, String searchType, String searchPathType){
        showProgressDialog(this);
        oDsayService.requestSearchPubTransPath(startX, startY, endX, endY, opt, searchType, searchPathType, onResultCallbackListener);

    }

    void GraphLoadLane(String mapObj){
        showProgressDialog(this);
        oDsayService.requestLoadLane(mapObj, onResultCallbackListener);
    }

    void SearchStation(String stationName){
        System.out.println("stationName: " + stationName);
        showProgressDialog(this);
        oDsayService.requestSearchStation(stationName, "", "", "", "", "", new OnResultCallbackListener() {
            @Override
            public void onSuccess(ODsayData oDsayData, API api) {
                try {
//                        System.out.println(oDsayData.getJson().getJSONObject("result").getJSONArray("station"));
                    System.out.println("가져오는 busName: " + mSearchModels.get(mSearchModelPosition).getBusNo() + "   " + mSearchModels.size());
                    getBusRouteNum("12021");
                    for(int i=0; i<oDsayData.getJson().getJSONObject("result").getJSONArray("station").length(); i++){
                        JSONObject jsonObject = oDsayData.getJson().getJSONObject("result").getJSONArray("station").getJSONObject(i);
                        for(int j = 0; j<jsonObject.getJSONArray("businfo").length(); j++){
//                                System.out.println("busNo: " + jsonObject.getJSONArray("businfo").getJSONObject(j).getString("busNo"));
                            if(jsonObject.getJSONArray("businfo").getJSONObject(j).getString("busNo").equals(mSearchModels.get(mSearchModelPosition).getBusNo()+"")){
                                loopFlag2 = true;
                                arsID = jsonObject.getString("arsID");
                                arsID = arsID.replace("-","");
                                System.out.println("arsID: " + arsID);
//                                getBusRouteNum(arsID);
//                                try {
//                                    Thread.sleep(1000);
//                                } catch (InterruptedException e) {
//                                    e.printStackTrace();
//                                }
//                                    System.out.println("arsID: " + arsID);
//
//                                    Thread th = new Thread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            while (loopFlag2){
//
//                                            }
//                                        }
//                                    });
//                                    th.start();
                                return;
                            }
                            if(j==jsonObject.getJSONArray("businfo").length()-1){
                                loopFlag2End=true;
                            }
                        }
                    }
                    mSearchModelPosition++;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                hideProgressDialog();
            }

            @Override
            public void onError(int i, String s, API api) {

            }
        });
    }

    private static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {

        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;

        if (unit == "kilometer") {
            dist = dist * 1.609344;
        } else if(unit == "meter"){
            dist = dist * 1609.344;
        }

            dist += (int) (dist/1.5);

        return (dist);
    }


    // This function converts decimal degrees to radians
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    // This function converts radians to decimal degrees
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

    void getBusRouteNum(String arsID) {
        try {
            showProgressDialog(this);
            URL url = new URL("http://ws.bus.go.kr/api/rest/stationinfo/getRouteByStation?serviceKey=sVZDRtsumTQzV33eDPQGmV%2Fz32MrR7Ttp7bl9XoiOaLUbftNwMTHrGUpi9UPMu58CWL6TQ8xImYNdIJQDV8ENg%3D%3D&arsId=" + arsID); //검색 URL부
            InputStream is = url.openStream();
            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();
            parser.setInput(new InputStreamReader(is, StandardCharsets.UTF_8));
            int parserEvent = parser.getEventType();
//            System.out.println("파싱시작합니다.");

            while (parserEvent != XmlPullParser.END_DOCUMENT) {
                switch (parserEvent) {
                    case XmlPullParser.START_TAG://parser가 시작 태그를 만나면 실행
                        if (parser.getName().equals("busRouteId")) { //title 만나면 내용을 받을수 있게 하자
                            busRouteId = true;
                        }
                        if (parser.getName().equals("busRouteNm")) { //address 만나면 내용을 받을수 있게 하자
                            busRouteNum = true;
                        }
                        if(parser.getName().equals("nextBus")){
                           nextBus = true;
                        }
                        break;

                    case XmlPullParser.TEXT://parser가 내용에 접근했을때
                        if (busRouteId) { //isTitle이 true일 때 태그의 내용을 저장.
                            stringBusRouteId = parser.getText();
                            mArrayLisyBusId.add(stringBusRouteId);
//                            System.out.println(stringBusRouteId);
                            busRouteId = false;

                        }
                        if (busRouteNum) { //isAddress이 true일 때 태그의 내용을 저장.
                            stringBusRouteNum = parser.getText();
                            mArrayListBusNum.add(stringBusRouteNum);
//                            System.out.println(stringBusRouteNum);
                            busRouteNum = false;
                        }
                        if(nextBus){
                            stringNextBus = parser.getText();
                            mArrayListNextBus.add(stringNextBus);
                            nextBus = false;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("itemList")) {

                        }
                        break;
                }
                parserEvent = parser.next();
            }

            System.out.println("busRouteID: " + busRouteID);

            for(int i=0; i<mArrayListBusNum.size(); i++){
                if(mArrayListBusNum.get(i).equals(Integer.toString(busNo))){
                    if(Integer.parseInt(mArrayListNextBus.get(i))<0) { // 막차 끊김

                    }
                    busRouteID = mArrayLisyBusId.get(i);
                }
            }
            mArrayLisyBusId.clear();
            mArrayListBusNum.clear();
//            System.out.println(mArrayLisyBusId.size() + " " +mArrayListBusNum.size());
//            SearcharsID(arsID);
            hideProgressDialog();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void SearcharsID(String arsID){
        try {
            showProgressDialog(this);
            URL url = new URL("http://ws.bus.go.kr/api/rest/stationinfo/getStationByUid?serviceKey=sVZDRtsumTQzV33eDPQGmV%2Fz32MrR7Ttp7bl9XoiOaLUbftNwMTHrGUpi9UPMu58CWL6TQ8xImYNdIJQDV8ENg%3D%3D&arsId="+arsID); //검색 URL부
            InputStream is = url.openStream();
            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();
            parser.setInput(new InputStreamReader(is, StandardCharsets.UTF_8));
            int parserEvent = parser.getEventType();
//            System.out.println("파싱시작합니다.");

            while (parserEvent != XmlPullParser.END_DOCUMENT) {
                switch (parserEvent) {
                    case XmlPullParser.START_TAG://parser가 시작 태그를 만나면 실행
                        if (parser.getName().equals("arrmsg1")) { //address 만나면 내용을 받을수 있게 하자
                            inarrmsg1 = true;
                        }
                        if (parser.getName().equals("busRouteId")) { //busRouteId 만나면 내용을 받을수 있게 하자
                            inadirection = true;
                        }
                        break;

                    case XmlPullParser.TEXT://parser가 내용에 접근했을때
                        if (inarrmsg1) { //isAddress이 true일 때 태그의 내용을 저장.
                            arrmsg1 = parser.getText();
                            mArrayListarr.add(arrmsg1);
                            inarrmsg1 = false;
                        }
                        if (inadirection) { //busRouteId true일 때 태그의 내용을 저장.
                            adirection = parser.getText();
                            mArrayLisyBusRouteId.add(adirection);
                            inadirection = false;
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("itemList")) {
                        }
                        break;
                }
                parserEvent = parser.next();
            }
            for(int i=0; i<mArrayLisyBusRouteId.size(); i++){
                if(mArrayLisyBusRouteId.get(i).equals(busRouteID)){
                    System.out.println("최종: " + mArrayListarr.get(i));
                }
            }
            mArrayLisyBusRouteId.clear();
            mArrayListarr.clear();
            loopFlag2 = false;
            if(loopFlag2End){
                loopFlag = false;
                loopFlag2End = false;
            }
            hideProgressDialog();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void NearStation(String x, String y){
        showProgressDialog(this);
        oDsayService.requestPointSearch(x,y,"5000","1", onResultCallbackListener);
    }


}

