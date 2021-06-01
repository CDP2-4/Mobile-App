package com.cdp2.schemi.product;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cdp2.schemi.MainActivity;
import com.cdp2.schemi.R;
import com.cdp2.schemi.common.HttpClass;
import com.cdp2.schemi.common.KjyLog;
import com.cdp2.schemi.common.MyCommon;
import com.cdp2.schemi.common.OjyLog;

import org.json.JSONObject;

import java.util.HashMap;


public class Release_Activity extends AppCompatActivity {
    String TAG = "Release_Activity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.release_layout);

    }

}