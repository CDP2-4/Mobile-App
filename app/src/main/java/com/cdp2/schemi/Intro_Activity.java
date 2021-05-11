package com.cdp2.schemi;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cdp2.schemi.common.I_VALUE;
import com.cdp2.schemi.common.KjyLog;
import com.cdp2.schemi.member.Login_Activity;

public class Intro_Activity extends AppCompatActivity {
    String TAG = "Intro_Activity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.intro_layout);

        /** 3초후 메인화면 이동 */

        SharedPreferences sharedPref = getSharedPreferences(I_VALUE.SP_KEY_VALUE, Context.MODE_PRIVATE);
        String _isLogin = sharedPref.getString("_isLogin", "__empty__");
        KjyLog.i(TAG, "_isLogin : "+_isLogin);

        if(_isLogin.equals("_isLogin")){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent t = new Intent(Intro_Activity.this, MainActivity.class);
                    startActivity(t);
                    finish();

                }
            }, 3000);
        }else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent t = new Intent(Intro_Activity.this, Login_Activity.class);
                    startActivity(t);
                    finish();

                }
            }, 3000);
        }






    }
}
