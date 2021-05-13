package com.cdp2.schemi.product;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.cdp2.schemi.MainActivity;
import com.cdp2.schemi.R;
import com.cdp2.schemi.common.I_VALUE;
import com.cdp2.schemi.common.QR_Photo_Activity;
import com.cdp2.schemi.member.Login_Activity;
import com.cdp2.schemi.member.Member_Edit_Activity;

public class Receive_Activity extends AppCompatActivity implements View.OnClickListener {
    String TAG = "Receive_Activity";
    int request_Code_Qr = 1;
    int request_Code_Label = 2;
    boolean _isShootQr = false;
    boolean _isShootLabel = false;

    TextView mTv_qr_photo;
    TextView mTv_label_photo;
    TextView mTv_submit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receive_layout);
        
        /** 권한 허용 */
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Receive_Activity.this, new String[]{android.Manifest.permission.CAMERA}, 50);
        }

        mTv_qr_photo=findViewById(R.id.receive_tv_qr_photo);
        mTv_label_photo=findViewById(R.id.receive_tv_label_photo);
        mTv_submit=findViewById(R.id.receive_tv_submit);

        mTv_qr_photo.setOnClickListener(this);
        mTv_label_photo.setOnClickListener(this);
        mTv_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == mTv_qr_photo) {
            Intent t = new Intent(Receive_Activity.this, QR_Photo_Activity.class);
            startActivityForResult(t, request_Code_Qr);
        }

        if(v == mTv_label_photo) {
            Intent t = new Intent(Receive_Activity.this, QR_Photo_Activity.class);
            startActivityForResult(t, request_Code_Label);
        }

        if(v == mTv_submit) {
            /** QR 촬영과 라벨 촬영이 모두 완료된 후에만 팝업 출력 */
            if(!_isShootQr || !_isShootLabel) {
                Toast.makeText(this, "QR 촬영과 라벨 촬영을 모두 완료해주세요.", Toast.LENGTH_SHORT).show();
            } else {
//                Toast.makeText(this, "Ok!", Toast.LENGTH_SHORT).show();
                showSubmit_Custom();
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == request_Code_Qr) {
            /** QR 코드 촬영이 완료된 경우 */
            if (resultCode == RESULT_OK) {
                mTv_qr_photo.setText(data.getData().toString());
                _isShootQr = true;
            } else {
                Toast.makeText(Receive_Activity.this, "failed", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == request_Code_Label) {
            /** 라벨 촬영이 완료된 경우 */
            if (resultCode == RESULT_OK) {
                mTv_label_photo.setText(data.getData().toString());
                _isShootLabel = true;
            } else {
                Toast.makeText(Receive_Activity.this, "failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showSubmit_Custom(){
        Dialog _dialog = new Dialog(this);
        _dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        _dialog.setContentView(R.layout.popup_layout);

        _dialog.setCancelable(false);

        TextView _tvCancel = _dialog.findViewById(R.id.popup_tv_cancel);
        TextView _tvOk = _dialog.findViewById(R.id.popup_tv_okay);

        _tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _dialog.dismiss();
            }
        });

        _tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _dialog.dismiss();

                /** 서버로 데이터 전송 */
                finish();
            }
        });


        _dialog.show();

    }
}
