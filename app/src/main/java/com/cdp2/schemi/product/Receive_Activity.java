package com.cdp2.schemi.product;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cdp2.schemi.MainActivity;
import com.cdp2.schemi.R;
import com.cdp2.schemi.common.QR_Photo_Activity;
import com.cdp2.schemi.member.Member_Edit_Activity;

public class Receive_Activity extends AppCompatActivity implements View.OnClickListener {
    String TAG = "Receive_Activity";

    TextView mTv_qr_photo;
    TextView mTv_label_photo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receive_layout);
        setTitle("입고");

        mTv_qr_photo=findViewById(R.id.receive_tv_qr_photo);
        mTv_label_photo=findViewById(R.id.receive_tv_label_photo);

        mTv_qr_photo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == mTv_qr_photo) {
            Intent t = new Intent(Receive_Activity.this, QR_Photo_Activity.class);
            startActivity(t);
        }

//        if(v == mTv_label_photo) {
//            Intent t = new Intent(Receive_Activity.this, Label_Photo_Activity.class);
//            startActivity(t);
//            finish();
//        }
    }
}
