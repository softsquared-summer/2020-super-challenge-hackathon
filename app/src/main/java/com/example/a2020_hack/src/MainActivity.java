package com.example.a2020_hack.src;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.a2020_hack.R;
import com.odsay.odsayandroidsdk.API;
import com.odsay.odsayandroidsdk.ODsayData;
import com.odsay.odsayandroidsdk.ODsayService;
import com.odsay.odsayandroidsdk.OnResultCallbackListener;

import org.json.JSONException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        ODsayService.init(this,getString(R.string.odsay_key)).requestSearchBusLane("10", "1000", "no", "10", "1", new OnResultCallbackListener() {
//            @Override
//            public void onSuccess(ODsayData oDsayData, API api) {
//                try {
//                    System.out.println(oDsayData.getJson().getJSONObject("result").getJSONArray("lane").get(0));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void onError(int i, String s, API api) {
//
//            }
//        });


//        ODsayService.init(this,getString(R.string.odsay_key)).requestSearchPubTransPath("126.926493082645", "37.6134436427887", "127.126936754911", "37.5004198786564", "1", "0", "0", new OnResultCallbackListener() {
//            @Override
//            public void onSuccess(ODsayData oDsayData, API api) {
//                System.out.println("결과: " + oDsayData.getJson());
//            }
//
//            @Override
//            public void onError(int i, String s, API api) {
//
//            }
//        });
    }
}
