package com.example.lixiaobin.studentapp.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.lixiaobin.studentapp.Action.NumSignAction;
import com.example.lixiaobin.studentapp.R;

public class NumSignActivity extends AppCompatActivity {
    private EditText inputNum;
    private Button numButtonSign;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numsign);
        inputNum = (EditText) findViewById(R.id.inputNum);
        numButtonSign = (Button) findViewById(R.id.numButtonSign);
        numButtonSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Handler handler = new Handler(){
                    public void handleMessage(Message msg) {
                        //isNetError = msg.getData().getBoolean("isNetError");
                        if(msg.what==0x123)
                        {
                            Log.i("NumSignActivity",msg.getData().getString("mes"));
                            if (msg.getData().getString("mes").equals("签到成功")){
                                new AlertDialog.Builder(NumSignActivity.this).
                                setTitle("提示消息").
                                setMessage("签到成功")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent = new Intent(NumSignActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                })
                                .show();
                            }
                            else {
                                new AlertDialog.Builder(NumSignActivity.this).
                                        setTitle("提示消息").
                                        setMessage(msg.getData().getString("mes")).
                                        setPositiveButton("确定", null)
                                        .show();
                            }
                        }
                    }
                };
                new Thread(){
                    @Override
                    public void run() {
                        NumSignAction numSignAction = new NumSignAction();
                        Intent intent=getIntent();
                        Bundle bundle=intent.getExtras();
                        Log.i("NumSignActivity",bundle.getString("studentAccount"));
                        String mes = numSignAction.NumSign(inputNum.getText().toString(),bundle.getString("studentAccount"));
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("mes",mes);
                        if (mes!=null){
                            Message message = new Message();
                            message.what=0x123;
                            message.setData(bundle1);
                            handler.sendMessage(message);
                        }
                    }
                }.start();
            }
        });
    }
}
