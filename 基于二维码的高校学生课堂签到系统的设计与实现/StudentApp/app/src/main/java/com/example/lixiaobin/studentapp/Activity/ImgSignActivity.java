package com.example.lixiaobin.studentapp.Activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.os.*;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.lixiaobin.studentapp.Configer.ThreadTransform;
import com.example.lixiaobin.studentapp.Obj.StaticCla;
import com.example.lixiaobin.studentapp.R;
import com.flurgle.camerakit.CameraKit;
import com.flurgle.camerakit.CameraListener;
import com.flurgle.camerakit.CameraView;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Permission;
import java.util.HashMap;
import java.util.Map;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.content.pm.PackageManager.*;
import static com.example.lixiaobin.studentapp.R.id.button_capture;

public class ImgSignActivity extends AppCompatActivity implements Runnable {
    private CameraView camera;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_sign);
        checkPermission();
        camera = (CameraView) findViewById(R.id.camera);
        Button btn = (Button) findViewById(button_capture);
        ActivityCompat.requestPermissions(ImgSignActivity.this, new String[]{Manifest.permission.CAMERA}, 1);


        Thread t = new Thread(this);
        t.start();
        camera.setFacing(CameraKit.Constants.FACING_BACK);
        camera.setCameraListener(new CameraListener() {
            @Override
            public void onPictureTaken(byte[] picture) {
                super.onPictureTaken(picture);

                // Create a bitmap
                Bitmap result = BitmapFactory.decodeByteArray(picture, 0, picture.length);
                saveImage(result);
                Intent intent=getIntent();
                Bundle bundle=intent.getExtras();
                ThreadTransform t=new ThreadTransform(bundle.getString("studentAccount"));
                Thread t1=new Thread(t);
                t1.start();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ImgSignActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        // camera.captureImage();
        Button btn1=(Button) findViewById(R.id.button_capture);
        btn1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                camera.captureImage();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                new AlertDialog.Builder(ImgSignActivity.this).
                        setTitle("提示消息").
                        setMessage("签到成功")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(ImgSignActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        camera.start();
    }

    @Override
    protected void onPause() {
        camera.stop();
        super.onPause();
    }

    private void saveImage(Bitmap bmp) {
        String fileName = "CRcode.png";
        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/" + fileName);
        try {
            //创建文件
            file.createNewFile();
            Log.v("LogActivity","创建成功");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileOutputStream fos = new FileOutputStream(file);
           /* val face=findViewById(R.id.faceview) as ImageView
            val left=face.left
            val right=face.right
            val top=face.top
            val bottom=face.bottom
            val bmp1= Bitmap.createBitmap(bmp,left,top,right-left,bottom-top)*/
            bmp.compress(Bitmap.CompressFormat.JPEG, 30, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
       // camera.captureImage();
    }
    private void checkPermission() {
        //检查权限（NEED_PERMISSION）是否被授权 PackageManager.PERMISSION_GRANTED表示同意授权
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //用户已经拒绝过一次，再次弹出权限申请对话框需要给用户一个解释
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission
                    .WRITE_EXTERNAL_STORAGE)) {
                Toast.makeText(this, "请开通相关权限，否则无法正常使用本应用！", Toast.LENGTH_SHORT).show();
            }
            //申请权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

        } else {
            Toast.makeText(this, "授权成功！", Toast.LENGTH_SHORT).show();

        }
    }

}
