package com.cdp2.schemi.warehouse;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cdp2.schemi.MainActivity;
import com.cdp2.schemi.R;
import com.cdp2.schemi.common.HttpClass;
import com.cdp2.schemi.common.I_VALUE;
import com.cdp2.schemi.common.KjyLog;
import com.cdp2.schemi.common.MyCommon;
import com.cdp2.schemi.common.MyPermission;
import com.cdp2.schemi.common.QR_Photo_Activity;
import com.cdp2.schemi.member.Login_Activity;
import com.cdp2.schemi.member.Member_Value;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;


public class In_Out_Activity extends AppCompatActivity {
    String TAG = "In_Out_Activity";
    int request_Code_Qr = 1;
    String mQr_Text = "";
    SimpleDateFormat _format = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");

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
        _dialog.setCancelable(false);TextView message;

        message = (TextView) _dialog.findViewById(R.id.popup_tv_message);
        message.setText("입출입 하시겠습니까?");

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
                /** 서버로 데이터 전송 */
                sendInoutData();
                _dialog.dismiss();
                finish();

                /** 입출입 정보 저장 */
                Toast.makeText(In_Out_Activity.this, "창고 입출입이 기록되었습니다.", Toast.LENGTH_SHORT).show();

                Intent t = new Intent(In_Out_Activity.this, MainActivity.class);
                startActivity(t);
                finish();
            }
        });

        _dialog.show();
    }



    private void sendInoutData() {
        Member_Value _user = MyCommon.get_UserInfo(this);
        String mUser_id = _user.mUser_id;
        String mUser_name = _user.mUser_Name;
        String mCompany_code = _user.mCompany_code;
        Date mTime = new Date();
        String _time = _format.format(mTime); // 현재 시각

        HashMap<String, String> _params = new HashMap();
        _params.put("action", "_isInOutCheck");
        _params.put("user_id", mUser_id);
        _params.put("user_name", mUser_name);
        _params.put("warehouse_no", mQr_Text);
        _params.put("in_time", _time);
        _params.put("company_code", mCompany_code);
        new HttpClass(this, HttpClass.ACTION_01, mHandler, _params).start();

    }



    private void checkInout(String _str) {
        KjyLog.i(TAG, "checkInOut() / _str : "+_str);

        try {
            JSONObject _obj = new JSONObject(_str);

            int _res = _obj.getInt("res");
            KjyLog.i(TAG, "res: " + _res);

            if(_res == 0){
                /** 성공적으로 불러왔으므로 창고 정보 출력*/
                In_Out_Value _warehouse = new In_Out_Value(_obj.getJSONObject("info"));
                MyCommon.save_InOutInfo(this, _warehouse);

                KjyLog.i(TAG, "**** 통신 후 warehouse: "+ _warehouse.mIn_time + " " +_warehouse.mOut_time);//코드 어디고??

            }else{
                /** 불러오기 실패 */
                Toast.makeText(this, "창고 정보를 불러오지 못했습니다.", Toast.LENGTH_SHORT).show();
            }
        }catch(Exception e){
            KjyLog.e(TAG, e);
        }

    }
}