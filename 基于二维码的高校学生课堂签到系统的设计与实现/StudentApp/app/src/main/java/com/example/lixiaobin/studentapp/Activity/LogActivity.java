package com.example.lixiaobin.studentapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.lixiaobin.studentapp.Action.LogAction;
import com.example.lixiaobin.studentapp.Obj.AppInfo;
import com.example.lixiaobin.studentapp.Obj.Student;
import com.example.lixiaobin.studentapp.R;

import java.lang.ref.WeakReference;

public class LogActivity extends AppCompatActivity{
    private Button logButton;
    private EditText accountText;
    private EditText passwordText;
    private String account;
    private String password;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        logButton = (Button) findViewById(R.id.loginButton);
        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                accountText = (EditText)findViewById(R.id.account);
                passwordText = (EditText) findViewById(R.id.password);
                account = accountText.getText().toString();
                password = passwordText.getText().toString();
                Log.v("LogActivity",account);
                Log.v("LogActivity",password);
                final Handler myhandler = new Handler(){
                    public void handleMessage(Message msg) {
                        //isNetError = msg.getData().getBoolean("isNetError");
                        if(msg.what==0x123)
                        {
                            Intent intent = new Intent(LogActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                };
                new Thread(){
                    @Override
                    public void run() {
                        LogAction logAction = new LogAction();
                        Student student = new Student();
                        student = logAction.StudentLogIn(account,password);
                        if (student==null){
                            Log.i("LogActivity","登陆失败");
                        }else {
                            Message message = new Message();
                            message.what=0x123;
                            myhandler.sendMessage(message);
                            AppInfo appInfo = (AppInfo)getApplicationContext();
                            appInfo.setStudent_id(student.getStudent_id());
                            appInfo.setStudent_account(student.getStudent_account());
                            appInfo.setStudent_password(student.getStudent_password());
                            appInfo.setStudent_cardid(student.getStudent_cardid());
                            appInfo.setStudent_name(student.getStudent_name());
                            appInfo.setStudent_sex(student.getStudent_sex());
                            appInfo.setStudent_joinyear(student.getStudent_joinyear());
                            appInfo.setStudent_stuyear(student.getStudent_stuyear());
                            appInfo.setStudent_major(student.getStudent_major());
                            appInfo.setStudent_class(student.getStudent_class());
                            appInfo.setStudent_phone(student.getStudent_phone());
                            appInfo.setStudent_emile(student.getStudent_emile());
                            appInfo.setStudent_adress(student.getStudent_adress());
                            appInfo.setStudent_extra(student.getStudent_extra());
                        }
                    }
                }.start();
            }
        });
    }
}
