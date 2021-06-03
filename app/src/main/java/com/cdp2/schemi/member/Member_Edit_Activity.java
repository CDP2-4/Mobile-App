package com.cdp2.schemi.member;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
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
import com.cdp2.schemi.common.HttpClass;
import com.cdp2.schemi.common.I_VALUE;
import com.cdp2.schemi.common.KjyLog;
import com.cdp2.schemi.common.MyCommon;
import com.cdp2.schemi.common.OjyLog;

import org.json.JSONObject;

import java.util.HashMap;

public class Member_Edit_Activity extends AppCompatActivity implements View.OnClickListener{
    //기능은 구현 O 지금 PHP파일에 _isEditValue 이게 없어진 상황이라 DB 수정이 안됨 PHP파일에 추가 필요


    String TAG = "Member_Edit_Activity";

    TextView mTv_notice;
    EditText mEt_id;
    EditText mEt_name;
    EditText mEt_tel;
    EditText mEt_pwd;
    EditText mEt_pwd_re;
    TextView mTv_edit;

    /** 서버와 통신한 후 이 핸들러로 옴 */
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int _sel = msg.what;

            switch(_sel){
                case HttpClass.ACTION_01:
                    editValue((String)msg.obj);
                    break;
            }
        }
    };

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


        JSONObject _obj = new JSONObject();

        Member_Value _user = new Member_Value();
        _user = MyCommon.get_UserInfo(this);

        mEt_name.setText(_user.mUser_Name);
        mEt_tel.setText(_user.mUser_tel);
        mEt_id.setText(_user.mUser_id);

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

        //비밀번호 공란인채로 변경시 NULL이 DB에 저장됨
        //빈칸 NULL이면 변경 불가
        if(TextUtils.isEmpty(_pwdStr)||TextUtils.isEmpty(_pwdStr_re))
        {
            Toast.makeText(this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show();
        }
        else{
            //둘다 값이 있고
            if(_pwdStr.equals(_pwdStr_re)) {
                //그게 같으면 변경 O
                showConfirm_Custom();
            } else {
                Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void showConfirm_Custom(){
        Dialog _dialog = new Dialog(this);
        _dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        _dialog.setContentView(R.layout.popup_layout);
        _dialog.setCancelable(false);

        TextView message;
        message = (TextView) _dialog.findViewById(R.id.popup_tv_message);
        message.setText("수정하시겠습니까?");



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

                JSONObject _obj = new JSONObject();

                Member_Value _user = new Member_Value();
                Member_Value old_user = new Member_Value();
                old_user = MyCommon.get_UserInfo(getApplicationContext());

                //old_user에서 새로운 user로 바꿔주는 함수 호출
                _user = MakeUser(old_user,_user);

                //바꿔진 user를 hash함
                HashMap<String, String> _params = new HashMap();
                _params.put("action", "_isEditValue");
                _params.put("user_id", _user.mUser_id);
                _params.put("user_pwd", _user.mUser_pwd);
                _params.put("user_name", _user.mUser_Name);
                _params.put("user_tel", _user.mUser_tel);
                OjyLog.i(TAG, "hash 완료");
                new HttpClass(getApplicationContext(), HttpClass.ACTION_01, mHandler, _params).start();


                _dialog.dismiss();




            }
        });

        _dialog.show();

    }
    private void showCheck_Custom(){
        OjyLog.i(TAG, "확인 커스텀창 들어옴");
        Dialog _dialog = new Dialog(this);
        _dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        _dialog.setContentView(R.layout.popup_layout_confirm);
        _dialog.setCancelable(false);

        TextView message;
        message = (TextView) _dialog.findViewById(R.id.popup_confirm_tv_message);
        message.setText("수정되었습니다.");


        TextView _tvOk = _dialog.findViewById(R.id.popup_confirm_tv_okay);


        _tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                _dialog.dismiss();
                Intent t = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(t);
                finish();

            }
        });

        _dialog.show();

    }
    private Member_Value MakeUser(Member_Value old_user,Member_Value _user){

        OjyLog.i(TAG, "현재 정보  old_user name : " + old_user.mUser_Name+" old_user tel"+old_user.mUser_tel+" old_user pwd"+old_user.mUser_pwd);

        mEt_id=findViewById(R.id.edit_et_id);
        mEt_name=findViewById(R.id.edit_et_name);
        mEt_tel=findViewById(R.id.edit_et_tel);
        mEt_pwd=findViewById(R.id.edit_et_pwd);

        _user.mUser_Name=mEt_name.getText().toString();
        _user.mUser_pwd=mEt_pwd.getText().toString();
        _user.mUser_tel=mEt_tel.getText().toString();
        _user._id = old_user._id;
        _user.mUser_id = old_user.mUser_id;


        OjyLog.i(TAG, "저장될 정보  _user name : " + _user.mUser_Name+"_user tel"+_user.mUser_tel+"_user pwd"+_user.mUser_pwd);

        MyCommon.save_UserInfo(getApplicationContext(),_user);

        OjyLog.i(TAG, "MakeUser : _user create complete");
        return _user;

    }

    private void editValue(String _str) {
        KjyLog.i(TAG, "editValue() / _str : " + _str);

        try {
            JSONObject _obj = new JSONObject(_str);

            int _res = _obj.getInt("res");
            KjyLog.i(TAG, "res: " + _res);

            if (_res == 0) {

                //확인 완료 다이어로그 함수로
                //Toast.makeText(this, "수정 성공", Toast.LENGTH_SHORT).show();
                showCheck_Custom();

                Intent t = new Intent(this, MainActivity.class);
                startActivity(t);
                finish();

            } else {
                /*res 0 아니면 서버 수정 안된거임*/
                Toast.makeText(this, "수정 실패", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            KjyLog.e(TAG, e);
        }
    }
}