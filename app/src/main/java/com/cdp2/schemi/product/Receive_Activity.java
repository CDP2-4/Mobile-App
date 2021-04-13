package com.cdp2.schemi.product;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cdp2.schemi.R;

public class Receive_Activity extends AppCompatActivity {
    String TAG = "Receive_Activity";

    TextView mTv_curr_warehouse;
    TextView mTv_warehouse_name;
    TextView mTv_qr_photo;
    TextView mTv_label_photo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receive_layout);

        mTv_curr_warehouse=findViewById(R.id.receive_tv_curr_warehouse);
        mTv_warehouse_name=findViewById(R.id.receive_tv_warehouse_name);
        mTv_qr_photo=findViewById(R.id.receive_tv_qr_photo);
        mTv_label_photo=findViewById(R.id.receive_tv_label_photo);
    }
}
