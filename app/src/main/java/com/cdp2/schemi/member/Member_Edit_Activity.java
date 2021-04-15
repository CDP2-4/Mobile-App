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
    TextView mTv_id;
    EditText mEt_name;
    TextView mTv_name;
    EditText mEt_tel;
    TextView mTv_tel;
    EditText mEt_pwd;
    TextView mTv_pwd;
    EditText mEt_pwd_re;
    TextView mTv_pwd_re;
    TextView mTv_edit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_edit_layout);
        setTitle("회원정보 수정");

        mTv_notice=findViewById(R.id.edit_tv_notice);
        mTv_id=findViewById(R.id.edit_tv_id);
        mEt_id=findViewById(R.id.edit_et_id);
        mTv_name=findViewById(R.id.edit_tv_name);
        mEt_name=findViewById(R.id.edit_et_name);
        mTv_tel=findViewById(R.id.edit_tv_tel);
        mEt_tel=findViewById(R.id.edit_et_tel);
        mTv_pwd=findViewById(R.id.edit_tv_pwd);
        mEt_pwd=findViewById(R.id.edit_et_pwd);
        mTv_pwd_re=findViewById(R.id.edit_tv_pwd_re);
        mEt_pwd_re=findViewById(R.id.edit_et_pwd_re);
        mTv_edit=findViewById(R.id.edit_tv_edit);
    }
}
