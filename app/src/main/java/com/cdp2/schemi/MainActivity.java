package com.cdp2.schemi;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView mTv_member_modify;
    TextView mTv_logout;
    LinearLayout mLl_inout;
    LinearLayout mLl_receive;
    LinearLayout mLl_release;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //액션바 제거
        ActionBar bar=getSupportActionBar();
        bar.hide();

        mTv_member_modify=findViewById(R.id.main_tv_member_modify);
        mTv_logout=findViewById(R.id.main_tv_logout);
        mLl_inout=findViewById(R.id.main_ll_inout);
        mLl_receive=findViewById(R.id.main_ll_receive);
        mLl_release=findViewById(R.id.main_ll_release);
    }
}