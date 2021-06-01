package com.cdp2.schemi.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.cdp2.schemi.member.Member_Value;
import com.cdp2.schemi.warehouse.In_Out_Value;

public class MyCommon {

    /** 1. 로그인할때 쓸거 : key:_isLogin, value:_isLogin*/
    public static void save_SharedPreferences(Context _ac, String _key, String _value){
        SharedPreferences sharedPref = _ac.getSharedPreferences(I_VALUE.SP_KEY_VALUE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(_key, _value);
        editor.commit();
    }


    public static String get_SharedPreferences(Context _ac, String _key){
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


        for(int i=0 ; i<_userARr01.length ; i++) {
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

    public static void save_InOutInfo(Context _ac, In_Out_Value _warehouse){
        SharedPreferences sharedPref = _ac.getSharedPreferences(I_VALUE.SP_INOUT_KEY_VALUE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        String _warehouseStr = _warehouse.toString();
        String _warehouseARr01[] = _warehouseStr.split(", ");


        for(int i=0 ; i<_warehouseARr01.length ; i++) {
            String _key = _warehouseARr01[i].split("=")[0];
            String _value = _warehouseARr01[i].split("=")[1];
            KjyLog.i("save_InOutInfo", _key + " " + _value);

            editor.putString(_key, _value);
            editor.commit();
        }
    }


    public static In_Out_Value get_InOutInfo(Context _ac){
        SharedPreferences sharedPref = _ac.getSharedPreferences(I_VALUE.SP_INOUT_KEY_VALUE, Context.MODE_PRIVATE);

        In_Out_Value _warehouse = new In_Out_Value(sharedPref);
        KjyLog.i("get_InOutInfo", _warehouse.mWarehouse_name);

        return _warehouse;
    }

}
