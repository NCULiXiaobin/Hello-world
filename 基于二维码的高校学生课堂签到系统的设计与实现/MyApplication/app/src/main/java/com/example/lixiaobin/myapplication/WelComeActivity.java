package com.example.lixiaobin.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class WelComeActivity extends AppCompatActivity implements Runnable{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        new Thread(this).start();
    }
    @Override
    public void run() {
        try {
            Thread.sleep(2000);
            Intent intent = new Intent(WelComeActivity.this, WebViewActivity.class);
            startActivity(intent);
            finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
