package com.cdp2.schemi.member;

import com.cdp2.schemi.common.KjyLog;

import org.json.JSONObject;

import java.io.Serializable;

public class Member_Value implements Serializable {
    String TAG = "Member_Value";

    int _id = 0;
    String mUser_id = "";
    String mUser_pwd = "";
    String mUser_Name = "";

    Member_Value(){}

    Member_Value(JSONObject _obj){
        try{
            mUser_id= _obj.getString("mUser_id");
        }catch(Exception e){
            KjyLog.e(TAG, e);
        }
    }
}