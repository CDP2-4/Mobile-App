package com.cdp2.schemi.member;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.cdp2.schemi.R;

public class Login_Activity extends AppCompatActivity implements View.OnClickListener {
    String TAG = "Login_Activity";

    EditText mEt_id;
    EditText mEt_pwd;
    TextView mTv_login;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        ActionBar bar=getSupportActionBar();
        bar.hide();

        mEt_id=findViewById(R.id.login_et_id);
        mTv_login = findViewById(R.id.login_tv_login);


        mTv_login.setOnClickListener( this );

    }

    @Override
    public void onClick(View v) {
        if(v == mTv_login){
            /**  21.04.08 - 로그인 클릭시 */
            if(isEmpty()){


            }else{
                /** 서버에 아이디 비번 체크. */
            }
        }
    }

    /** 빈값 체크
     * @return true:빈값있음, 로그인하면 안됨, false:모두 입력했음. */
    boolean isEmpty(){
        if( mEt_id.length() == 0) {
            Toast.makeText(this, "아이디를 입력하세요.", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }
}
