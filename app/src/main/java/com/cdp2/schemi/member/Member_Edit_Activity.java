package com.cdp2.schemi.member;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.cdp2.schemi.MainActivity;
import com.cdp2.schemi.R;
import com.cdp2.schemi.common.I_VALUE;
import com.cdp2.schemi.common.KjyLog;

public class Member_Edit_Activity extends AppCompatActivity implements View.OnClickListener{
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

        mTv_edit.setOnClickListener( this );
    }

    @Override
    public void onClick(View v) {
        if(v == mTv_edit){
            checkPassword();
        }
    }

    private void checkPassword() {
        String _pwdStr = mEt_pwd.getText().toString();
        String _pwdStr_re = mEt_pwd_re.getText().toString();

        if(_pwdStr.equals(_pwdStr_re)) {
            showConfirm_Custom();
        } else {
            Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    private void showConfirm_Custom(){
        Dialog _dialog = new Dialog(this);
        _dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        _dialog.setContentView(R.layout.popup_layout);
        _dialog.setCancelable(false);

//        TextView mTv_popup_message = findViewById(R.id.popup_tv_message);
//        mTv_popup_message.setText("수정하시겠습니까?");

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
                /** 정보 업데이트 */


                _dialog.dismiss();
                finish();

                Intent t = new Intent(Member_Edit_Activity.this, MainActivity.class);
                startActivity(t);
                finish();
            }
        });

        _dialog.show();

    }
}
