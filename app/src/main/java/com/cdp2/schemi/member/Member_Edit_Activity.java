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

public class Member_Edit_Activity extends AppCompatActivity{
    String TAG = "Member_Edit_Activity";

    TextView mTv_notice;
    EditText mEt_id;
    EditText mEt_name;
    EditText mEt_tel;
    EditText mEt_pwd;
    EditText mEt_pwd_re;
    TextView mTv_edit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_edit_layout);
        setTitle("회원정보 수정");

        mTv_notice=findViewById(R.id.edit_tv_notice);
        mEt_id=findViewById(R.id.edit_et_id);
        mEt_name=findViewById(R.id.edit_et_name);
        mEt_tel=findViewById(R.id.edit_et_tel);
        mEt_pwd=findViewById(R.id.edit_et_pwd);
        mEt_pwd_re=findViewById(R.id.edit_et_pwd_re);
        mTv_edit=findViewById(R.id.edit_tv_edit);
    }
}
