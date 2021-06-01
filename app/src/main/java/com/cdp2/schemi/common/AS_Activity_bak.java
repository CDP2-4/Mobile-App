package com.cdp2.schemi.common;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cdp2.schemi.R;

import java.util.HashMap;

public class AS_Activity_bak extends AppCompatActivity {
    final String TAG = "AS_Activity";


    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int _sel = msg.what;

            switch(_sel){
                case HttpClass_bak.ACTION_01:
                    setAs_Request_List((String)msg.obj);
                    break;
            }
        }
    };


    /** A/S 내역이 신청중인것만 셋팅함*/
    private void setAs_Request_List(String _str){
        try{

           /** 서버로 부터 받은 데이터. */


        }catch(Exception e){
            KjyLog.e(TAG, e);
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        KjyLog.i(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        getAs_Request_List();
    }


    /** A/S 신청중인 것만 가지고 오기*/
    private void getAs_Request_List(){
        HashMap<String, String> _params = new HashMap<>();
        _params.put("action","aaaaaaa");
        _params.put("user_id","bbbbb");
        _params.put("user_phone","ccccc");
        new HttpClass_bak(this, HttpClass_bak.ACTION_01, mHandler, _params).start();
    }


}
