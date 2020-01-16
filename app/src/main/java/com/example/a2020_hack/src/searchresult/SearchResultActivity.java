package com.example.a2020_hack.src.searchresult;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;

import com.example.a2020_hack.R;
import com.example.a2020_hack.src.base.BaseActivity;
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

import java.util.ArrayList;

public class SearchResultActivity extends BaseActivity {

    private Context mContext;

    private ODsayService oDsayService;
    private OnResultCallbackListener onResultCallbackListener;
    private ArrayList<SearchPublicTransResult> mArrayList = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private SearchResultAdapter mSearchResultAdapter;

    private int busNo;
    private boolean returnFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        mContext = this;
        mRecyclerView = findViewById(R.id.searchResult_rv);
        mSearchResultAdapter = new SearchResultAdapter(mContext, mArrayList);
        mRecyclerView.setAdapter(mSearchResultAdapter);

        oDsayService = ODsayService.init(this, getString(R.string.odsay_key));
        oDsayService.setReadTimeout(5000);
        oDsayService.setConnectionTimeout(5000);

        onResultCallbackListener = new OnResultCallbackListener() {
            @Override
            public void onSuccess(ODsayData oDsayData, API api) {
                hideProgressDialog();
                if(api == API.SEARCH_PUB_TRANS_PATH){
                    try {
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
                        busNo = mArrayList.get(1).getSubPath().get(1).getLane().get(0).getBusNo();
                        mSearchResultAdapter.notifyDataSetChanged();
                        SearchStation(mArrayList.get(1).getSubPath().get(1).getStartName());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if(api == API.LOAD_LANE){

                }
                else if(api == API.SEARCH_STATION){
                    try {
                        System.out.println(oDsayData.getJson().getJSONObject("result").getJSONArray("station"));
                        for(int i=0; i<oDsayData.getJson().getJSONObject("result").getJSONArray("station").length(); i++){
                            System.out.println("가져오는 busNo: " + busNo);
                            JSONObject jsonObject = oDsayData.getJson().getJSONObject("result").getJSONArray("station").getJSONObject(i);
                            for(int j = 0; j<jsonObject.getJSONArray("businfo").length(); j++){
                                System.out.println("busNo: " + jsonObject.getJSONArray("businfo").getJSONObject(j).getString("busNo"));
                                if(jsonObject.getJSONArray("businfo").getJSONObject(j).getString("busNo").equals(busNo+"")){
                                    String arsID = jsonObject.getString("arsID");
                                    arsID = arsID.replace("-","");
                                    System.out.println("arsID: " + arsID);
                                    returnFlag = true;
                                    return;
                                }
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(int i, String s, API api) {
                hideProgressDialog();
                showCustomToast("네트워크 연결 에러입니다.");
            }
        };

        SearchPublicTransPath("126.926493082645", "37.6134436427887", "127.126936754911", "37.5004198786564", "0", "0", "0");
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
        System.out.println("호출");
        showProgressDialog(this);
        oDsayService.requestSearchStation(stationName,"","","","","",onResultCallbackListener);
    }
}
