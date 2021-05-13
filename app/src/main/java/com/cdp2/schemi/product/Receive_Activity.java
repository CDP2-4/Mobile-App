package com.cdp2.schemi.product;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.cdp2.schemi.MainActivity;
import com.cdp2.schemi.R;
import com.cdp2.schemi.common.QR_Photo_Activity;
import com.cdp2.schemi.member.Member_Edit_Activity;

public class Receive_Activity extends AppCompatActivity implements View.OnClickListener {
    String TAG = "Receive_Activity";
    int request_Code = 1;

    TextView mTv_qr_photo;
    TextView mTv_label_photo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receive_layout);
        setTitle("입고");
        
        /** 권한 허용 */
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Receive_Activity.this, new String[]{android.Manifest.permission.CAMERA}, 50);
        }

        mTv_qr_photo=findViewById(R.id.receive_tv_qr_photo);
        mTv_label_photo=findViewById(R.id.receive_tv_label_photo);

        mTv_qr_photo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == mTv_qr_photo) {
            Intent t = new Intent(Receive_Activity.this, QR_Photo_Activity.class);
            startActivityForResult(t, request_Code);
        }

//        if(v == mTv_label_photo) {
//            Intent t = new Intent(Receive_Activity.this, Label_Photo_Activity.class);
//            startActivity(t);
//            finish();
//        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == request_Code) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(Receive_Activity.this, data.getData().toString(), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(Receive_Activity.this, "failed", Toast.LENGTH_SHORT).show();
        }
    }
}
