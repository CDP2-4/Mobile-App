package com.cdp2.schemi.warehouse;

import android.content.SharedPreferences;

import com.cdp2.schemi.common.KjyLog;
import com.cdp2.schemi.common.OjyLog;

import org.json.JSONObject;

import java.io.Serializable;

public class In_Out_Value implements Serializable {
    String TAG = "In_Out_Value";

    public static int _id = 0;
    public static String mUser_id = "";
    public static String mUser_Name = "";
    public static int mWarehouse_no= 0;
    public static String mWarehouse_name = "";
    public static String mIn_time = "";
    public static String mOut_time = "";

    public In_Out_Value(){}


    public In_Out_Value(JSONObject _obj){
        KjyLog.i(TAG, _obj.toString());
        try{
            _id= _obj.getInt("no");
            mUser_id= _obj.getString("user_id");
            mUser_Name= _obj.getString("user_name");
            mWarehouse_no= _obj.getInt("warehouse_no");
            mWarehouse_name= _obj.getString("warehouse_name");
            mIn_time= _obj.getString("in_time");
            mOut_time= _obj.getString("out_time");
        }catch(Exception e){
            KjyLog.e(TAG, e);
        }
    }

    public In_Out_Value(SharedPreferences sharedPref ){
        try{
            _id = Integer.valueOf( sharedPref.getString("_id", ""));
            mUser_id = sharedPref.getString("mUser_id", "");
            mUser_Name = sharedPref.getString("mUser_Name", "");
            mWarehouse_no = Integer.valueOf( sharedPref.getString("mWarehouse_no", ""));
            mWarehouse_name = sharedPref.getString("mWarehouse_name", "");
            mIn_time = sharedPref.getString("mIn_time", "");
            mOut_time = sharedPref.getString("mOut_time", "");

        }catch(Exception e){
            KjyLog.e(TAG,e);
        }
    }


    public static void initialize() {
        _id= 0;
        mUser_id= "";
        mUser_Name= "";
        mWarehouse_no= 0;
        mWarehouse_name= "";
        mIn_time= "";
        mOut_time= "";
    }



    @Override
    public String toString() {
        return
                "_id=" + _id +
                        ", mUser_id=" + mUser_id+
                        ", mUser_Name=" + mUser_Name+
                        ", mWarehouse_no=" + mWarehouse_no+
                        ", mWarehouse_name=" + mWarehouse_name+
                        ", mIn_time=" + mIn_time  +
                        ", mOut_time=" + mOut_time;
    }
}