package com.cdp2.schemi.common;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.cdp2.schemi.R;
import com.google.zxing.Result;

public class QR_Photo_Activity extends AppCompatActivity {
    String TAG  = "QR_Photo_Activity";
    private CodeScanner mCodeScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr_photo_layout);
        CodeScannerView scannerView = findViewById(R.id.scanner_view);

        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        /** 원래 Activity로 값 넘겨주기 */
                        Intent qr_Str = new Intent();
                        qr_Str.setData(Uri.parse(result.getText()));
                        OjyLog.i(TAG, "result.getText() : "+result.getText());
                        setResult(RESULT_OK, qr_Str);
                        finish();
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }
}

// https://github.com/yuriy-budiyev/code-scanner