package com.cdp2.schemi.product;

import android.content.SharedPreferences;

import com.cdp2.schemi.common.KjyLog;
import com.cdp2.schemi.common.OjyLog;

import org.json.JSONObject;

import java.io.Serializable;

public class Product_Value implements Serializable {
    String TAG = "Product_Value";

    int _id = 0;
    public String mProduct_QR = "";
    public String mWarehouse_no = "";
    public String mWarehousing_time = "";
    public String mUser_id = "";
    public String mWarehousing_label = "";
    public int mState = 0;
    public String mCompany_code = "";

    public Product_Value(){}


    public Product_Value(JSONObject _obj){
        KjyLog.i(TAG, _obj.toString());
        try{
            _id= _obj.getInt("no");
            mProduct_QR= _obj.getString("product_QR");
            mWarehouse_no= _obj.getString("warehouse_no");
            mWarehousing_time= _obj.getString("warehousing_time");
            mUser_id= _obj.getString("user_id");
            mWarehousing_label= _obj.getString("warehousing_label");
            mState= _obj.getInt("state");
            mCompany_code= _obj.getString("company_code");
        }catch(Exception e){
            KjyLog.e(TAG, e);
        }
    }

    public Product_Value( SharedPreferences sharedPref ){
        try{
            _id = Integer.valueOf( sharedPref.getString("_id", ""));
            mProduct_QR = sharedPref.getString("mProduct_QR", "");
            mWarehouse_no = sharedPref.getString("mWarehouse_no", "");
            mWarehousing_time = sharedPref.getString("mWarehousing_time", "");
            mUser_id = sharedPref.getString("mUser_id", "");
            mWarehousing_label = sharedPref.getString("mWarehousing_label", "");
            mState= Integer.valueOf( sharedPref.getString("mState", ""));
            mCompany_code = sharedPref.getString("mCompany_code", "");

        }catch(Exception e){
            OjyLog.e(TAG,e);
        }
    }




    @Override
    public String toString() {
        return
                "_id=" + _id +
                        ", mProduct_QR=" + mProduct_QR+
                        ", mWarehouse_no=" + mWarehouse_no +
                        ", mWarehousing_time=" + mWarehousing_time +
                        ", mUser_id=" + mUser_id +
                        ", mWarehousing_label=" + mWarehousing_label +
                        ", mState=" + mState +
                        ", mCompany_code=" + mCompany_code;
    }
}