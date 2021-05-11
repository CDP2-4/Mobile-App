package com.cdp2.schemi.member;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.JetPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.cdp2.schemi.MainActivity;
import com.cdp2.schemi.R;
import com.cdp2.schemi.common.I_VALUE;
import com.cdp2.schemi.common.KjyLog;

public class Login_Activity extends AppCompatActivity implements View.OnClickListener {
    String TAG = "Login_Activity";

    EditText mEt_id;
    EditText mEt_pwd;
    TextView mTv_login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);


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

        if(_idStr.equals(_pwdStr)){
            SharedPreferences sharedPref = getSharedPreferences(I_VALUE.SP_KEY_VALUE, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString("_isLogin", "_isLogin");
            editor.commit();


            Intent t = new Intent(this, MainActivity.class);
            startActivity(t);
            finish();
        }else{
            Toast.makeText(this, "아이디가 없거나 아이디/비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();

        }

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
