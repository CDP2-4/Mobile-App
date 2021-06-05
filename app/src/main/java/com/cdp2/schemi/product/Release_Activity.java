package com.cdp2.schemi.product;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
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
import com.cdp2.schemi.common.MyPermission;
import com.cdp2.schemi.common.OjyLog;
import com.cdp2.schemi.common.QR_Photo_Activity;
import com.cdp2.schemi.member.Member_Value;
import com.cdp2.schemi.warehouse.In_Out_Activity;
import com.cdp2.schemi.warehouse.In_Out_Value;

import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;


public class Release_Activity extends AppCompatActivity {
    String TAG = "Release_Activity";
    int request_Code_Qr = 1;
    String mQr_Text = "";

    /** 서버와 통신한 후 이 핸들러로 옴 */
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int _sel = msg.what;

            switch(_sel){
                case HttpClass.ACTION_01:
                    checkInout((String)msg.obj);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.release_layout);

            /** 권한 허용 */
            MyPermission _permission = new MyPermission();
            _permission.checkPermission(getApplicationContext(), Release_Activity.this);

            Intent t = new Intent(Release_Activity.this, QR_Photo_Activity.class);
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
                Toast.makeText(Release_Activity.this, "failed", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void showSubmit_Custom(){
        Dialog _dialog = new Dialog(this);
        _dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        _dialog.setContentView(R.layout.popup_layout);
        _dialog.setCancelable(false);

        TextView message;
        message = (TextView) _dialog.findViewById(R.id.popup_tv_message);
        message.setText("출고하시겠습니까?");

        TextView _tvCancel = _dialog.findViewById(R.id.popup_tv_cancel);
        TextView _tvOk = _dialog.findViewById(R.id.popup_tv_okay);

        _tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //취소
                _dialog.dismiss();
            }
        });

        _tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _dialog.dismiss();

                /** 서버로 데이터 전송 */
                sendInoutData();

                //finish();
            }
        });


        _dialog.show();

    }

    private void sendInoutData() {
        Member_Value _user = MyCommon.get_UserInfo(this);
        String mUser_id = _user.mUser_id;
        Date mTime = new Date();

        HashMap<String, String> _params = new HashMap();
        _params.put("action", "_isRelease");
        _params.put("user_id", mUser_id);
        _params.put("product_QR", mQr_Text);
        new HttpClass(this, HttpClass.ACTION_01, mHandler, _params).start();

    }

    private void showCheck_Custom(){
        OjyLog.i(TAG, "확인 커스텀창 들어옴");
        Dialog _dialog = new Dialog(this);
        _dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        _dialog.setContentView(R.layout.popup_layout_confirm);
        _dialog.setCancelable(false);

        TextView message;
        message = (TextView) _dialog.findViewById(R.id.popup_confirm_tv_message);
        message.setText("출고완료 되었습니다.");


        TextView _tvOk = _dialog.findViewById(R.id.popup_confirm_tv_okay);


        _tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                _dialog.dismiss();
                finish();

                Intent t = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(t);

            }
        });

        _dialog.show();

    }
    private void checkInout(String _str) {
        KjyLog.i(TAG, "checkInOut() / _str : "+_str);

        try {
            JSONObject _obj = new JSONObject(_str);

            int _res = _obj.getInt("res");
            KjyLog.i(TAG, "res: " + _res);

            if(_res == 0){
                /** 성공*/
                //In_Out_Value _release = new In_Out_Value(_obj.getJSONObject("info"));
                // MyCommon.save_InOutInfo(this, _release);
                showCheck_Custom();

                //KjyLog.i(TAG, "**** 통신 후 warehouse: "+ _release.mIn_time + " " +_release.mOut_time);

            }else{
                /** 불러오기 실패 */
                Toast.makeText(this, "입고 물품을 불러오지 못했습니다.", Toast.LENGTH_SHORT).show();
            }
        }catch(Exception e){
            KjyLog.e(TAG, e);
        }

    }
}