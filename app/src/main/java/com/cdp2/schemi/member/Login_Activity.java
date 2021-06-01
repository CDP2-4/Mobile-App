package com.cdp2.schemi.member;

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


public class Login_Activity extends AppCompatActivity implements View.OnClickListener {
    String TAG = "Login_Activity";

    EditText mEt_id;
    EditText mEt_pwd;
    TextView mTv_login;

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int _sel = msg.what;

            switch(_sel){
                case HttpClass.ACTION_01:
                    checkLogin((String)msg.obj);
                    break;
            }
        }
    };


    private void checkLogin(String _str){
        OjyLog.i(TAG, "checkLogin() / _str : "+_str);


        try {
            JSONObject _obj = new JSONObject(_str);

            int _res = _obj.getInt("res");
            KjyLog.i(TAG, "res: " + _res);

            if(_res == 0){
                /** 아이디 비번 맞으니깐 메인으로.*/
                MyCommon.save_SharedPreferences(this, "_isLogin", "_isLogin");

                Member_Value _user = new Member_Value(_obj.getJSONObject("info"));
                MyCommon.save_UserInfo(this, _user);

                KjyLog.i(TAG, "MyCommon.save_UserInfo");
                Intent t = new Intent(this, MainActivity.class);
                startActivity(t);
                finish();

            }else{
                /** 아이디 혹은 비밀번호가 잘못됨.*/
                Toast.makeText(this, "아이디 혹은 비밀번호를 잘못입력하셨습니다.", Toast.LENGTH_SHORT).show();
            }
        }catch(Exception e){
            OjyLog.e(TAG, e);
        }


    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        String _isLogin = MyCommon.get_SharedPreferences(this, "_isLogin");

        if(_isLogin.equals("_isLogin")){
            Intent t = new Intent(this, MainActivity.class);
            startActivity(t);
            finish();

        }


        mEt_id=findViewById(R.id.login_et_id);
        mEt_pwd=findViewById(R.id.login_et_pw);
        mTv_login=findViewById(R.id.login_tv_login);


        mTv_login.setOnClickListener( this );

    }

    @Override
    public void onClick(View v) {
        if(v == mTv_login){
            /**  21.04.08 - 로그인 클릭시 */
            if(isEmpty()){

            }else{
                /** 서버에 아이디 비번 체크. */
                checkLogin();

            }

        }
    }




    private void checkLogin(){
        KjyLog.i(TAG, "checkLogin()");
        String _idStr = mEt_id.getText().toString();
        String _pwdStr = mEt_pwd.getText().toString();

        HashMap<String, String> _params = new HashMap();
        _params.put("action", "_isLoginCheck");
        _params.put("user_id", _idStr);
        _params.put("user_pwd", _pwdStr);
        new HttpClass(this, HttpClass.ACTION_01, mHandler, _params).start();

    }


    /** 빈값 체크
     * @return true:빈값있음, 로그인하면 안됨, false:모두 입력했음. */
    boolean isEmpty(){
        if( mEt_id.length() == 0) {
            Toast.makeText(this, "아이디를 입력하세요.", Toast.LENGTH_SHORT).show();
            return true;
        }

        if( mEt_id.length() == 0) {
            Toast.makeText(this, "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
}