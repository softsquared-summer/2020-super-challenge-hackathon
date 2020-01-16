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
import com.example.a2020_hack.src.searchresult.SearchResultActivity;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
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
    public static double srcLat = 0;//위도
    public static double srcLng = 0;//경도
    public static double destLat = 0;//위도
    public static double destLng = 0;//경도
    public static String mDestName = null;

    public static String HOME_NAME = "은마아파트";
    public static String HOME_DETAIL= "서울 강남구 대치동 316";
    public static double HOME_LAT= 37.4499641433847;
    public static double HOME_LNG= 127.06532735974666;
    public static String SCHOOL_NAME = "인하대학교 용현캠퍼스";
    public static String SCHOOL_DETAIL = "인천 미추홀구 용현동 253";
    public static double SCHOOL_LAT = 37.4499641433847;
    public static double SCHOOL_LNG = 126.653467210032;
    public static String FRIEND_NAME = "인천용현엑슬루타워아파트";
    public static String FRIEND_DETAIL = "인천 미추홀구 용현동 659";
    public static double FRIEND_LAT = 37.45737463922806;
    public static double FRIEND_LNG = 126.63744904712938;




    private EditText mEtSrc, mEtDest;

    Geocoder geocoder = new Geocoder(this);

    //현우코드 최근검색어 리스트뷰 연결
    private ArrayList<MainListItem> mMainListItems = new ArrayList<>();
    private MainAdapter mMainAdapter;
    private ListView mLvHistory;
    private Gson gson;
    private SharedPreferences sSharedPreferences;
    //현우 fab
    private Animation fab_open, fab_close;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab, fab1, fab2,fab3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        getMyLocation();

        ImageView mImageViewTemp = findViewById(R.id.temp);
        mImageViewTemp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SearchResultActivity.class));
            }
        });

        getHashKey(this);
        //현우 start
        mLvHistory = findViewById(R.id.lv_recent_history);

        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab3 = (FloatingActionButton) findViewById(R.id.fab3);

        fab.setOnClickListener(this);
        fab1.setOnClickListener(this);
        fab2.setOnClickListener(this);
        fab3.setOnClickListener(this);

        mEtSrc = findViewById(R.id.main_src_et);
        mEtDest = findViewById(R.id.main_dest_et);

        mLvHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                destLat = mMainListItems.get(position).getDestLat();
                destLng = mMainListItems.get(position).getDestLng();
                srcLat = mMainListItems.get(position).getSrcLat();
                srcLng = mMainListItems.get(position).getSrcLng();
                mDestName = mMainListItems.get(position).getDestName();
                System.out.println("저장된 위경도: " + srcLat+ "  " + srcLng + "  " + destLat + "  " + destLng);
                startActivity(new Intent(MainActivity.this, SearchResultActivity.class));
            }
        });

        gson = new Gson();
        sSharedPreferences = getApplicationContext().getSharedPreferences("a2020_hack", Context.MODE_PRIVATE);
        String json = sSharedPreferences.getString("recent", null);
        Type type = new TypeToken<ArrayList<MainListItem>>() {
        }.getType();
        if (gson.fromJson(json, type) != null) {
            mMainListItems = gson.fromJson(json, type);
        } else {

        }

        mMainAdapter = new MainAdapter(mMainListItems, this);
        mLvHistory.setAdapter(mMainAdapter);
    }


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
            case R.id.main_route_search :
                if (mEtSrc.getText().toString().equals("")){
                    showCustomToast("출발지를 입력해주세요.");
                }
                else if(mEtDest.getText().toString().equals("")){
                    showCustomToast("도착지를 입력해주세요.");
                }
                else{
                    mDestName = mEtDest.getText().toString();
                    Intent intent = new Intent(MainActivity.this, SearchResultActivity.class);
                    startActivity(intent);
                    System.out.println("저장된 위경도: " + srcLat+ "  " + srcLng + "  " + destLat + "  " + destLng);
                    saveRecent();
                }
                break;

            case R.id.fab:
                anim();
                break;
            case R.id.fab1:
                anim();
                destName=HOME_NAME;
                destDetail=HOME_DETAIL;
                destLat=HOME_LAT;
                destLng=HOME_LNG;
                mEtDest.setText(destName);
                break;
            case R.id.fab2:
                anim();
                destName=SCHOOL_NAME;
                destDetail=SCHOOL_DETAIL;
                destLat=SCHOOL_LAT;
                destLng=SCHOOL_LNG;
                mEtDest.setText(destName);
                break;
            case R.id.fab3:
                anim();
                destName=FRIEND_NAME;
                destDetail=FRIEND_DETAIL;
                destLat=FRIEND_LAT;
                destLng=FRIEND_LNG;
                mEtDest.setText(destName);
                break;

        }
    }


    public void anim() {

        if (isFabOpen) {
            fab.setBackgroundResource(R.drawable.ic_plus_24dp);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab3.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            fab3.setClickable(false);
            isFabOpen = false;
        } else {
            fab.setBackgroundResource(R.drawable.ic_close_24dp);
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab3.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            fab3.setClickable(true);
            isFabOpen = true;
        }
    }

        public void saveRecent() {
        mMainListItems.add(0,new MainListItem(srcName,srcDetail,destName,destDetail,srcLat,srcLng,destLat,destLng));
        mMainAdapter.notifyDataSetChanged();
        SharedPreferences.Editor editor = sSharedPreferences.edit();
        String json = gson.toJson(mMainListItems);
        editor.putString("recent", json);
        editor.apply();
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
