package com.cdp2.schemi.common;

import android.util.Log;

public class KjyLog {
    static String Tag = "KJY_";
    static boolean isDebug = true;

    public static void i(String _tag, String _msg ){
        if(isDebug) {
            Log.i(Tag + _tag, _msg);
        }
    }

    public static void e(String _tag, Exception e){
        if(isDebug) {
            Log.i(Tag + _tag, e.getMessage());
        }
    }
}