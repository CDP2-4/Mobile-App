package com.cdp2.schemi.warehouse;

import android.content.SharedPreferences;

import com.cdp2.schemi.common.KjyLog;
import com.cdp2.schemi.common.OjyLog;

import org.json.JSONObject;

import java.io.Serializable;

public class Warehouse_Value implements Serializable {
    String TAG = "Warehouse_Value";

    int _id = 0;
    public String mWarehouse_name = "";
    public String mIn_time = "";
    public String mOut_time = "";

    public Warehouse_Value(){}


    public Warehouse_Value(JSONObject _obj){
        KjyLog.i(TAG, _obj.toString());
        try{
            _id= _obj.getInt("no");
            mWarehouse_name= _obj.getString("warehouse_name");
            mIn_time= _obj.getString("in_time");
            mOut_time= _obj.getString("out_time");
        }catch(Exception e){
            KjyLog.e(TAG, e);
        }
    }

    public Warehouse_Value( SharedPreferences sharedPref ){
        try{
            _id = Integer.valueOf( sharedPref.getString("_id", ""));
            mWarehouse_name = sharedPref.getString("mWarehouse_name", "");
            mIn_time = sharedPref.getString("mIn_time", "");
            mOut_time = sharedPref.getString("mOut_time", "");

        }catch(Exception e){
            KjyLog.e(TAG,e);
        }
    }




    @Override
    public String toString() {
        return
                "_id=" + _id +
                        ", mWarehouse_name=" + mWarehouse_name+
                        ", mIn_time=" + mIn_time  +
                        ", mOut_time=" + mOut_time;
    }
}