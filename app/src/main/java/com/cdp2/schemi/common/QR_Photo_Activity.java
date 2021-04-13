package com.cdp2.schemi.common;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cdp2.schemi.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class QR_Photo_Activity extends AppCompatActivity {
    private IntentIntegrator mQrScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr_photo_layout);

        mQrScan = new IntentIntegrator(this);
        mQrScan.setOrientationLocked(false); // default가 세로 모드인데 휴대폰 방향에 따라 가로, 세로로 자동 변경됩니다.
        mQrScan.initiateScan();
        mQrScan.setPrompt("카메라 초점을 맞춰주세요.");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                // todo
            } else {
                Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                // todo
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}

// https://park-duck.tistory.com/110