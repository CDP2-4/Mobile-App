package com.cdp2.schemi.product;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.cdp2.schemi.MainActivity;
import com.cdp2.schemi.R;
import com.cdp2.schemi.common.I_VALUE;
import com.cdp2.schemi.common.OjyLog;
import com.cdp2.schemi.common.QR_Photo_Activity;
import com.cdp2.schemi.member.Login_Activity;
import com.cdp2.schemi.member.Member_Edit_Activity;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.AlbumLoader;

import java.io.File;

public class Receive_Activity extends AppCompatActivity implements View.OnClickListener {
    String TAG = "Receive_Activity";
    int request_Code_Qr = 1;
    int request_Code_Label = 2;
    boolean _isShootQr = false;
    boolean _isShootLabel = false;

    TextView mTv_qr_photo;
    TextView mTv_label_photo;
    TextView mTv_submit;
    ImageView mIv_label_photo_image;

    File file;

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

        mTv_qr_photo=findViewById(R.id.receive_tv_qr_photo);
        mTv_label_photo=findViewById(R.id.receive_tv_label_photo);
        mTv_submit=findViewById(R.id.receive_tv_submit);
        mIv_label_photo_image=findViewById(R.id.receive_iv_label_photo_image);

        mTv_qr_photo.setOnClickListener(this);
        mTv_label_photo.setOnClickListener(this);
        mTv_submit.setOnClickListener(this);
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
}
