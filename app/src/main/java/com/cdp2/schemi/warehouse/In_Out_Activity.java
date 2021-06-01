package com.cdp2.schemi.warehouse;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cdp2.schemi.MainActivity;
import com.cdp2.schemi.R;
import com.cdp2.schemi.common.KjyLog;
import com.cdp2.schemi.common.MyPermission;
import com.cdp2.schemi.common.OjyLog;
import com.cdp2.schemi.common.QR_Photo_Activity;
import com.cdp2.schemi.product.Receive_Activity;


public class In_Out_Activity extends AppCompatActivity {
    String TAG = "In_Out_Activity";
    int request_Code_Qr = 1;
    String mQr_Text = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inout_layout);

        /** 권한 허용 */
        MyPermission _permission = new MyPermission();
        _permission.checkPermission(getApplicationContext(), In_Out_Activity.this);

        Intent t = new Intent(In_Out_Activity.this, QR_Photo_Activity.class);
        startActivityForResult(t, request_Code_Qr);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == request_Code_Qr) {
            /** QR 코드 촬영이 완료된 경우 */
            if (resultCode == RESULT_OK) {
                KjyLog.i(TAG, "RESULT_OK");
                mQr_Text = data.getData().toString();
                showSubmit_Custom();
            } else {
                Toast.makeText(In_Out_Activity.this, "failed", Toast.LENGTH_SHORT).show();
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
                Intent t = new Intent(In_Out_Activity.this, MainActivity.class);
                startActivity(t);
                finish();
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