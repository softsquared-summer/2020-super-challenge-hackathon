package com.example.a2020_hack.src.Main;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import com.example.a2020_hack.src.MapSearch.MapSearchActivity;
import com.example.a2020_hack.src.base.BaseActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.a2020_hack.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import static com.example.a2020_hack.src.base.ApplicationClass.sSharedPreferences;


public class MainActivity extends BaseActivity implements View.OnClickListener {

    private Intent intent;
    private LocationManager locationManager;
    private double latitude, longitude;
    private static final int REQUEST_CODE_LOCATION = 2;

    public static String srcName ="";
    public static String srcDetail ="";
    public static String destName = "";
    public static String destDetail = "";

    private EditText mEtSrc, mEtDest;

    Geocoder geocoder = new Geocoder(this);

    //현우코드 최근검색어 리스트뷰 연결
    private ArrayList<MainListItem> mMainListItems = new ArrayList<>();
    private MainAdapter mMainAdapter;
    private ListView mLvHistory;
    private Gson gson;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        getMyLocation();

        getHashKey(this);
        //현우 start
        mLvHistory = findViewById(R.id.lv_recent_history);


        mEtSrc = findViewById(R.id.main_src_et);
        mEtDest = findViewById(R.id.main_dest_et);


        gson = new Gson();
        sSharedPreferences = getApplicationContext().getSharedPreferences("a2020_hack", Context.MODE_PRIVATE);
        String json = sSharedPreferences.getString("recent", null);
        Type type = new TypeToken<ArrayList<MainListItem>>() {
        }.getType();
        if (gson.fromJson(json, type) != null) {
            mMainListItems = gson.fromJson(json, type);
        } else {
            for (int i = 0; i < 3; i++) {
                mMainListItems.add(new MainListItem("인하대후문삼거리", "인천 미추홀구 낙성동로 135(용현동)", "주안역[1호선]", "인천 미추홀구 주안로 95-19"));
            }
        }

        mMainAdapter = new MainAdapter(mMainListItems, this);
        mLvHistory.setAdapter(mMainAdapter);
    }
//
//    public void saveRecent() {
//        mMainAdapter.notifyDataSetChanged();
//        SharedPreferences.Editor editor = sSharedPreferences.edit();
//        String json = gson.toJson(mMainListItems);
//        editor.putString("recent", json);
//        editor.apply();
//    }

    @Override
    protected void onRestart() {
        super.onRestart();

        if(srcName == "") {
            mEtSrc.setText("예) 용현동 12-3 또는 용현아파트");
        }
        else {
            mEtSrc.setText(srcName);
        }


        if(destName == "") {
            mEtDest.setText("예) 용현동 12-3 또는 용현아파트");
        }
        else {
            mEtDest.setText(destName);
        }
    }
    private void getMyLocation() {
        Location currentLocation = null;
        // Register the listener with the Location Manager to receive location updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            System.out.println("////////////사용자에게 권한을 요청해야함");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, this.REQUEST_CODE_LOCATION);
//            getMyLocation(); //이건 써도되고 안써도 되지만, 전 권한 승인하면 즉시 위치값 받아오려고 썼습니다!
        } else {
            System.out.println("////////////권한요청 안해도됨");

            // 수동으로 위치 구하기
            String locationProvider = LocationManager.GPS_PROVIDER;
            currentLocation = locationManager.getLastKnownLocation(locationProvider);
            if (currentLocation != null) {
//                double lng = currentLocation.getLongitude();
//                double lat = currentLocation.getLatitude();

                Location userLocation = currentLocation;
                if (userLocation != null) {
                    latitude = userLocation.getLatitude();
                    longitude = userLocation.getLongitude();
                    System.out.println(latitude + " " + longitude);
                    List<Address> list = null;
                    try {
                        list = geocoder.getFromLocation(latitude, longitude, 5);
                        if (list.size() > 0) {
                            android.location.Address address = list.get(0);
                            String adds = address.getSubLocality();
//                            pos.setText(adds);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            }
        }
//        return currentLocation;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_src_et:
            case R.id.main_src_iv:
                intent = new Intent(getApplicationContext(), MapSearchActivity.class);
                intent.putExtra("mode", 0);
                startActivity(intent);
                break;
            case R.id.main_dest_et:
            case R.id.main_dest_iv:
                intent = new Intent(getApplicationContext(), MapSearchActivity.class);
                intent.putExtra("mode", 1);
                startActivity(intent);
                break;
        }
    }

    @Nullable

    public static String getHashKey(Context context) {

        final String TAG = "KeyHash";

        String keyHash = null;

        try {

            PackageInfo info =

                    context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);


            for (Signature signature : info.signatures) {

                MessageDigest md;

                md = MessageDigest.getInstance("SHA");

                md.update(signature.toByteArray());

                keyHash = new String(Base64.encode(md.digest(), 0));

                Log.d(TAG, keyHash);

            }

        } catch (Exception e) {

            Log.e("name not found", e.toString());

        }


        if (keyHash != null) {

            return keyHash;

        } else {

            return null;

        }

    }
}
