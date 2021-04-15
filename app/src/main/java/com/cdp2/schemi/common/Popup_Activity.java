package com.cdp2.schemi.common;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.cdp2.schemi.R;

public class Popup_Activity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedinstancestate){
        super.onCreate(savedinstancestate);
        //상태바 제거 (전체화면 모드)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.popup_layout);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        //바깥레이어 클릭시 안닫히게

        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed(){
        //안드로이드 백버튼 막기
        return;
    }
}

//https://binshuuuu.tistory.com/57
//되는지 안되는지 모름~~