package com.cdp2.schemi.member;

import android.content.SharedPreferences;

import com.cdp2.schemi.common.KjyLog;
import com.cdp2.schemi.common.OjyLog;

import org.json.JSONObject;

import java.io.Serializable;

public class Member_Value implements Serializable {
    String TAG = "Member_Value";

    int _id = 0;
    String mUser_id = "";
    String mUser_pwd = "";
    String mUser_Name = "";

    Member_Value(){}


    public Member_Value(JSONObject _obj){
        try{
            _id= _obj.getInt("_id");
            mUser_id= _obj.getString("mUser_id");
            mUser_pwd= _obj.getString("mUser_pwd");
            mUser_Name= _obj.getString("mUser_Name");

        }catch(Exception e){
            KjyLog.e(TAG, e);
        }
    }

    public Member_Value( SharedPreferences sharedPref ){
        try{
            _id = Integer.valueOf( sharedPref.getString("_id", ""));
            mUser_id = sharedPref.getString("mUser_id", "");
            mUser_pwd = sharedPref.getString("mUser_pwd", "");
            mUser_Name = sharedPref.getString("mUser_Name", "");

        }catch(Exception e){
            OjyLog.e(TAG,e);
        }
    }




    @Override
    public String toString() {
        return
                "_id=" + _id +
                ", mUser_id=" + mUser_id+
                ", mUser_pwd=" + mUser_pwd  +
                ", mUser_Name=" + mUser_Name;
    }
}