package com.example.lixiaobin.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.*;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static android.view.KeyEvent.KEYCODE_BACK;

public class WebViewActivity extends AppCompatActivity {
    private WebView webview;
    private TextView pro_view;
    MyDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        webview = (WebView) findViewById(R.id.webView);
        pro_view = (TextView) findViewById(R.id.proview);
        //设置WebView属性，能够执行Javascript脚本
        webview.getSettings().setJavaScriptEnabled(true);
        //加载需要显示的网页
//        webview.loadUrl("http://ng19839177.iask.in:37043/index.html");
        webview.loadUrl("http://192.168.43.170:8090/index.html");
        //设置Web视图
        webview.setWebChromeClient(new WebChromeClient(){

            public boolean onJsAlert(WebView view, String url, String message,
                                     JsResult result) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("对话框")
                        .setMessage(message)
                        .setPositiveButton("确定", null);
                builder.setCancelable(false);
                AlertDialog dialog = builder.create();
                dialog.show();
                result.confirm();// 因为没有绑定事件，需要强行confirm,否则页面会变黑显示不了内容。
                return true;
                // return super.onJsAlert(view, url, message, result);
            }
            public boolean onJsConfirm(WebView view, String url, String message,
                                       final JsResult result) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("对话框")
                        .setMessage(message)
                        .setPositiveButton("确定",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int which) {
                                result.confirm();
                            }
                        })
                        .setNeutralButton("取消", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                result.cancel();
                            }
                        });
                builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        result.cancel();
                    }
                });
                // 屏蔽keycode等于84之类的按键，避免按键后导致对话框消息而页面无法再弹出对话框的问题
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
                // return super.onJsConfirm(view, url, message, result);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                if(!TextUtils.isEmpty(title)&&title.toLowerCase().contains("error")){
                    Intent intent = new Intent(WebViewActivity.this, ErrorActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        webview.setWebViewClient(new WebViewClient() {
            //覆盖shouldOverrideUrlLoading 方法
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                dialog.dismiss();
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                dialog = MyDialog.showDialog(WebViewActivity.this);
//                if (url.equals("http://ng19839177.iask.in:37043/index.html")||url.equals("http://ng19839177.iask.in:37043/login.html#")){
//                    dialog.show();
//                }
                if (url.equals("http://192.168.43.170:8090/index.html")||url.equals("http://192.168.43.170:8090/index.html#")){
                    dialog.show();
                }
            }

            //解决webview不能及时更新cookie
            @Override
            public void onPageFinished(WebView view, String url) {
                //Log.i("WebViewActivity","1212");
                dialog.dismiss();
                webview.setVisibility(View.VISIBLE);
                CookieSyncManager.getInstance().sync();
            }
            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                Intent intent = new Intent(WebViewActivity.this, ErrorActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KEYCODE_BACK) && webview.canGoBack()) {
//            if(webview.getUrl().equals("http://ng19839177.iask.in:37043/index.html#")||webview.getUrl().equals("http://ng19839177.iask.in:37043/log.jsp#")){
//                System.exit(0);
//            }
//            webview.loadUrl("http://ng19839177.iask.in:37043/index.html");
            if(webview.getUrl().equals("http://192.168.43.170:8090/index.html#")||webview.getUrl().equals("http://192.168.43.170:8090/log.jsp#")){
                System.exit(0);
            }
            webview.loadUrl("http://192.168.43.170:8090/index.html");
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
