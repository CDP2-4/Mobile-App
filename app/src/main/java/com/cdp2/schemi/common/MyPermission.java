package com.cdp2.schemi.common;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


public class MyPermission {
    public void checkPermission(Context _ac, Activity _activity) {
        if (ContextCompat.checkSelfPermission(_ac, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(_activity, new String[]{android.Manifest.permission.CAMERA}, 50);
        }
        if (ContextCompat.checkSelfPermission(_ac, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(_activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 50);
        }
        if (ContextCompat.checkSelfPermission(_ac, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(_activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 50);
        }
    }
}
