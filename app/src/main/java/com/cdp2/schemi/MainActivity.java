package com.cdp2.schemi;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaMetadata;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cdp2.schemi.common.I_VALUE;
import com.cdp2.schemi.common.KjyLog;
import com.cdp2.schemi.common.QR_Photo_Activity;
import com.cdp2.schemi.member.Login_Activity;
import com.cdp2.schemi.member.Member_Edit_Activity;
import com.cdp2.schemi.product.Receive_Activity;

import java.lang.reflect.Member;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView mTv_member_modify;
    TextView mTv_logout;
    LinearLayout mLl_inout;
    LinearLayout mLl_receive;
    LinearLayout mLl_release;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTv_member_modify=findViewById(R.id.main_tv_member_modify);
        mTv_logout=findViewById(R.id.main_tv_logout);
        mLl_inout=findViewById(R.id.main_ll_inout);
        mLl_receive=findViewById(R.id.main_ll_receive);
        mLl_release=findViewById(R.id.main_ll_release);

        mTv_member_modify.setOnClickListener( this );
        mTv_logout.setOnClickListener( this );
        mLl_receive.setOnClickListener( this );
    }

    @Override
    public void onClick(View v) {
        if(v == mTv_logout) {
            showLogout_Custom();
        }

        if(v == mTv_member_modify) {
            Intent t = new Intent(MainActivity.this, Member_Edit_Activity.class);
            startActivity(t);
            finish();
        }

        if(v == mLl_receive) {
            Intent t = new Intent(MainActivity.this, Receive_Activity.class);
            startActivity(t);
            finish();
        }
    }

    private void showLogOut(){
        AlertDialog.Builder _alert = new AlertDialog.Builder(this);
        _alert.setTitle("로그아웃");
        _alert.setMessage("로그아웃 하시겠습니까?");
        _alert.setNegativeButton("아니오", null);
        _alert.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        _alert.show();
    }


    private void showLogout_Custom(){
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
                SharedPreferences sharedPref = getSharedPreferences(I_VALUE.SP_KEY_VALUE, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("_isLogin", "__empty__");
                editor.commit();

                _dialog.dismiss();
                finish();

                Intent t = new Intent(MainActivity.this, Login_Activity.class);
                startActivity(t);
                finish();
            }
        });


        _dialog.show();

    }
}