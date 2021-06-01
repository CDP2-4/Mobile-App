package com.cdp2.schemi.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.cdp2.schemi.member.Member_Value;

public class MyCommon {


    /** 1. 로그인할때 쓸거 : key:_isLogin, value:_isLogin*/
    public static void save_SharedPreferences(Context _ac, String _key, String _value){
        SharedPreferences sharedPref = _ac.getSharedPreferences(I_VALUE.SP_KEY_VALUE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(_key, _value);
        editor.commit();
    }


    public static String get_save_SharedPreferences(Context _ac, String _key){
        String _value = "";

        SharedPreferences sharedPref = _ac.getSharedPreferences(I_VALUE.SP_KEY_VALUE, Context.MODE_PRIVATE);
        _value = sharedPref.getString(_key, "____empty____");

        return _value;
    }


    public static void save_UserInfo(Context _ac, Member_Value _user){


        SharedPreferences sharedPref = _ac.getSharedPreferences(I_VALUE.SP_MEMBER_KEY_VALUE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        String _userStr = _user.toString();
        String _userARr01[] = _userStr.split(", ");


        for(int i=0 ; i<_userARr01.length ; i++){
           String _key = _userARr01[i].split("=")[0];
            String _value = _userARr01[i].split("=")[1];

            editor.putString(_key, _value);
            editor.commit();
        }
    }


    public static Member_Value get_UserInfo(Context _ac){
        SharedPreferences sharedPref = _ac.getSharedPreferences(I_VALUE.SP_MEMBER_KEY_VALUE, Context.MODE_PRIVATE);


        Member_Value _user = new Member_Value(sharedPref);


        return _user;

    }


}
