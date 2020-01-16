package com.example.a2020_hack.src.MapResult;

import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.a2020_hack.R;
import com.example.a2020_hack.src.Main.MainActivity;

import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import okhttp3.RequestBody;


public class MapResultActivity extends AppCompatActivity implements MapView.MapViewEventListener, View.OnClickListener {
    private MapView mapView;
    private String name;
    private String address;
    private double x;
    private double y;
    private EditText detailEt;
    private Button finishBt;
    private TextView addrTv;
    private ImageView backTv;
    private ImageView marker;
    private ViewGroup mapViewContainer;
    private TextView logoTv;
    private int state = 0;
    private int mode = 0;
    private double inhaLat = 37.450827;
    private double inhaLon = 126.654176;

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_result);

        Intent intent = getIntent();
        name = intent.getExtras().getString("name");
        address = intent.getExtras().getString("address");
        x = intent.getExtras().getDouble("x");
        y = intent.getExtras().getDouble("y");
        state = intent.getIntExtra("mode", 0);
        //Bring to values from MapSearchActivity


        mapViewContainer = (ViewGroup) findViewById(R.id.map_show);
        addrTv = findViewById(R.id.map_result_default_addr_tv);
        marker = findViewById(R.id.marker_result);

        mapView = new MapView(this);
        mapViewContainer.addView(mapView);
        mapView.setMapCenterPointAndZoomLevel(MapPoint.mapPointWithGeoCoord(x, y), 2, true);
        //Set mapview

        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("longitude", String.valueOf(y));
        editor.putString("latitude", String.valueOf(x));
        editor.commit();
        //Initialize basic latitude, longitude for sharedpreference

        mapView.setMapViewEventListener(this);
//        finishBt.setOnClickListener(this);
        //Click event Listener

        marker.bringToFront();
        //Bring to marker from layout

        intent = getIntent();
    }


    @Override
    public void onMapViewInitialized(MapView mapView) {

    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {

    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {
        Geocoder geo = new Geocoder(this, Locale.getDefault());
        List<Address> names = null;
        try {
            names = geo.getFromLocation(mapView.getMapCenterPoint().getMapPointGeoCoord().latitude, mapView.getMapCenterPoint().getMapPointGeoCoord().longitude, 10);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (names.size() > 0) {
            String tmp = names.get(0).getAddressLine(0);
            if (tmp.contains("대한민국 ")) {
                tmp = tmp.replace("대한민국 ", "");
            }
            if (tmp.contains("서울특별시")) {
                tmp = tmp.replace("서울특별시 ", "");
            }
            //Process long address line

            SharedPreferences pref = getSharedPreferences("map", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("addressMain", tmp);
            editor.commit();
            //Set new address

            addrTv.setText(tmp);
        }
    }
    //Set address when drag mapview



    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.result_finish_btn:
                if(state == 0) {
                    if(address == null || address == "") {
                        MainActivity.srcDetail = "";
                    }
                    else {
                        MainActivity.srcDetail = address;
                    }
                    MainActivity.srcName = name;
                    MainActivity.srcLat = x;
                    MainActivity.srcLng = y;
                }
                else {
                    if(address == null || address == "") {
                        MainActivity.destDetail = "";
                    }
                    else {
                        MainActivity.destDetail = address;
                    }
                    MainActivity.destName = name;
                    MainActivity.destLat = x;
                    MainActivity.destLng = y;
                }
//                Intent intent = new Intent(this, MainActivity.class);
//                intent.putExtra("name", name);
//                intent.putExtra("address", address);
//                intent.putExtra("x", x);
//                intent.putExtra("y", y);
//                intent.putExtra("mode", mode);
//                startActivity(intent);
                finish();
                break;
        }
//        if (v.getId() == R.id.map_result_back_tv) {
//            finish();
//        } else if (v.getId() == R.id.map_result_find_tv) {
//
//            if (state == 1) {
////                "startLongitude" : 37.4650456,
////                        "startLatitude" : 126.6785137,
////                        "endLongitude" : 37.4500263,
////                        "endLatitude" : 126.6512993,
////                        "type" : 1
//                Intent intent = new Intent(this, MainActivity.class);
//                intent.putExtra("endLatitude", String.valueOf(inhaLat));
//                intent.putExtra("endLongitude", String.valueOf(inhaLon));
//                intent.putExtra("startLatitude", String.valueOf(x));
//                intent.putExtra("startLongitude", String.valueOf(y));
//                intent.putExtra("type", state);
//                startActivity(intent);
//                finish();
//                //목적지 고정
//            } else if (state == 2) {
//                Intent intent = new Intent(this, ChatRoomActivity.class);
//                intent.putExtra("startLatitude", String.valueOf(inhaLat));
//                intent.putExtra("startLongitude", String.valueOf(inhaLon));
//                intent.putExtra("endLatitude", String.valueOf(x));
//                intent.putExtra("endLongitude", String.valueOf(y));
//                intent.putExtra("type", state);
//                startActivity(intent);
//                finish();
//                // 출발지 고정
//            }
//
//            //finish();
//        } else if (v.getId() == R.id.map_result_make_tv) {
//
//            // API통신해서 방만들기 쏴줘야함
//            // body에서 url에는 아래에있는  buf.toString() 넣으면댐
//            if (mode == 0) {//방 찾기
//                Random rnd = new Random();
//                StringBuffer buf = new StringBuffer();
//                for (int i = 0; i < 10; i++) {
//                    // rnd.nextBoolean() 는 랜덤으로 true, false 를 리턴. true일 시 랜덤 한 소문자를, false 일 시 랜덤 한 숫자를 StringBuffer 에 append 한다.
//                    if (rnd.nextBoolean()) {
//                        buf.append((char) ((int) (rnd.nextInt(26)) + 97));
//                    } else {
//                        buf.append((rnd.nextInt(10)));
//                    }
//                }
//                Intent intent = new Intent(this, ChatActivity.class);
//                intent.putExtra("chatName", buf.toString());
//                intent.putExtra("userName", "영진");
//
//                //방 만들기 api
//                try {
//                    this.postMakeRoom(buf.toString());
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }
//        //store map information
    }
}