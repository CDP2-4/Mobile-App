package com.cdp2.schemi.product;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.cdp2.schemi.R;
import com.cdp2.schemi.common.HttpClass;
import com.cdp2.schemi.common.KjyLog;
import com.cdp2.schemi.common.MyCommon;
import com.cdp2.schemi.common.OjyLog;
import com.cdp2.schemi.common.QR_Photo_Activity;
import com.cdp2.schemi.member.Member_Value;
import com.cdp2.schemi.warehouse.Warehouse_Value;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.AlbumLoader;

import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.HashMap;

public class Receive_Activity extends AppCompatActivity implements View.OnClickListener {
    String TAG = "Receive_Activity";
    int request_Code_Qr = 1;
    int request_Code_Label = 2;
    boolean _isShootQr = false;
    boolean _isShootLabel = false;

    TextView mTv_warehouse_name;
    TextView mTv_qr_photo;
    TextView mTv_label_photo;
    TextView mTv_submit;
    ImageView mIv_label_photo_image;

    File file;


    /** 서버와 통신한 후 이 핸들러로 옴 */
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int _sel = msg.what;

            switch(_sel){
                case HttpClass.ACTION_01:
                    requestWarehouse((String)msg.obj);
                    break;
            }
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.receive_layout);


        Album.initialize(AlbumConfig.newBuilder(this)
                .setAlbumLoader(new MediaLoader()).build());
        
        /** 권한 허용 */
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Receive_Activity.this, new String[]{android.Manifest.permission.CAMERA}, 50);
        }
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Receive_Activity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 50);
        }
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Receive_Activity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 50);
        }


        File sdcard = Environment.getExternalStorageDirectory();
        file = new File(sdcard, "capture.jpg");

        mTv_warehouse_name = findViewById(R.id.receive_tv_warehouse_name);
        mTv_qr_photo=findViewById(R.id.receive_tv_qr_photo);
        mTv_label_photo=findViewById(R.id.receive_tv_label_photo);
        mTv_submit=findViewById(R.id.receive_tv_submit);
        mIv_label_photo_image=findViewById(R.id.receive_iv_label_photo_image);

        mTv_qr_photo.setOnClickListener(this);
        mTv_label_photo.setOnClickListener(this);
        mTv_submit.setOnClickListener(this);

        loadWarehouse();
    }

    @Override
    public void onClick(View v) {
        if(v == mTv_qr_photo) {
            Intent t = new Intent(Receive_Activity.this, QR_Photo_Activity.class);
            startActivityForResult(t, request_Code_Qr);
        }

        if(v == mTv_label_photo) {
//            Intent t = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            Uri uri = FileProvider.getUriForFile(getBaseContext(), "com.cdp2.schemi.fileprovider", file);
//            t.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//            startActivityForResult(t, request_Code_Label);


            Album.camera(this) // Camera function.
                    .image() // Take Picture.
                    .onResult(new Action<String>() {
                        @Override
                        public void onAction(@NonNull String result) {
                            OjyLog.i(TAG, "onResult() / result : "+result);


                            Glide.with(Receive_Activity.this)
                                    .load(result)
                                    .thumbnail(0.1f)
                                    .into(mIv_label_photo_image);



                            ArrayList<String> _fileList = new ArrayList<>();
                            _fileList.add( result );
                            new HttpClass(Receive_Activity.this, HttpClass.ACTION_01, null, _fileList, null).start();



                        }
                    })
                    .onCancel(new Action<String>() {
                        @Override
                        public void onAction(@NonNull String result) {
                            OjyLog.i(TAG, "onCancel() / result : "+result);
                        }
                    })
                    .start();
        }

        if(v == mTv_submit) {
            /** QR 촬영과 라벨 촬영이 모두 완료된 후에만 팝업 출력 */
            if(!_isShootQr || !_isShootLabel) {
                Toast.makeText(this, "QR 촬영과 라벨 촬영을 모두 완료해주세요.", Toast.LENGTH_SHORT).show();
            } else {
//                Toast.makeText(this, "Ok!", Toast.LENGTH_SHORT).show();
                showSubmit_Custom();
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == request_Code_Qr) {
            /** QR 코드 촬영이 완료된 경우 */
            if (resultCode == RESULT_OK) {
                mTv_qr_photo.setText(data.getData().toString());
                _isShootQr = true;
            } else {
                Toast.makeText(Receive_Activity.this, "failed", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == request_Code_Label) {
            OjyLog.i(TAG, "resultCode :"+resultCode);
            OjyLog.i(TAG, "data.getDataStrin :"+data.getDataString() );

            /** 라벨 촬영이 완료된 경우 */
            if (resultCode == RESULT_OK) {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(), options);
                mIv_label_photo_image.setImageBitmap(bitmap);
            } else {
                Toast.makeText(Receive_Activity.this, "failed", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showSubmit_Custom(){
        Dialog _dialog = new Dialog(this);
        _dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        _dialog.setContentView(R.layout.popup_layout);

        _dialog.setCancelable(false);

        TextView _tvCancel = _dialog.findViewById(R.id.popup_tv_cancel);
        TextView _tvOk = _dialog.findViewById(R.id.popup_tv_okay);

        _tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _dialog.dismiss();
            }
        });

        _tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _dialog.dismiss();

                /** 서버로 데이터 전송 */
                finish();
            }
        });


        _dialog.show();

    }





    public class MediaLoader implements AlbumLoader {

        @Override
        public void load(ImageView imageView, AlbumFile albumFile) {
            load(imageView, albumFile.getPath());
        }

        @Override
        public void load(ImageView imageView, String url) {
            Glide.with(Receive_Activity.this)
                    .load(url)
                    .error(R.drawable.app_logo)
                    .placeholder(R.drawable.app_logo)
                    .thumbnail(0.1f)
                    .into(imageView);
        }
    }


    private void loadWarehouse() {
        Member_Value _user = MyCommon.get_UserInfo(this);
        String mUser_id = _user.mUser_id;

        HashMap<String, String> _params = new HashMap();
        _params.put("action", "_isLoadWarehouse");
        _params.put("user_id", mUser_id);
        new HttpClass(this, HttpClass.ACTION_01, mHandler, _params).start();
    }

    private void requestWarehouse(String _str) {
        KjyLog.i(TAG, "requestWarehouse() / _str : "+_str);
        String mWarehouse_name = "";

        try {
            JSONObject _obj = new JSONObject(_str);

            int _res = _obj.getInt("res");
            KjyLog.i(TAG, "res: " + _res);

            if(_res == 0){
                /** 성공적으로 불러왔으므로 창고 정보 출력*/
                Warehouse_Value _warehouse = new Warehouse_Value(_obj.getJSONObject("info"));
                MyCommon.save_CurrentWarehouse(this, _warehouse);

                mWarehouse_name = MyCommon.get_CurrentWarehouse(this).mWarehouse_name;
                KjyLog.i("TAG", mWarehouse_name);

                mTv_warehouse_name.setText(mWarehouse_name);
            }else{
                /** 불러오기 실패 */
                Toast.makeText(this, "창고 정보를 불러오지 못했습니다.", Toast.LENGTH_SHORT).show();
            }
        }catch(Exception e){
            KjyLog.e(TAG, e);
        }

    }
}


