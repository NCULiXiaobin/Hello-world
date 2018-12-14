package com.example.lixiaobin.studentapp.Configer;
import android.os.Environment;
import android.os.Message;
import com.example.lixiaobin.studentapp.Obj.StaticCla;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class ThreadTransform implements Runnable {
    String studentAccount = null;
    public ThreadTransform(String studentAccount) {
        this.studentAccount = studentAccount;
    }

    @Override
    public void run() {
        System.out.println("进入线程");
        JSONObject jsonObject = null;
        HttpURLConnection connection = null;
        try {
            URL url = null;
            try {
                String address = "http://"+ ServletConfig.IP+":"+ServletConfig.PORT+"/imgSign.json";
                HashMap<String,String> params = new HashMap<>();
                params.put("studentAccount",studentAccount);
                String uri = MakeUri.getURLWithParams(address,params);
                url = new URL(uri);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                connection = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                // 设置请求方式
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setConnectTimeout(10 * 1000);
                connection.setRequestProperty("Content-Type", "multipart/form-data");
                // 上传一张图片
                FileInputStream file = new FileInputStream(Environment.getExternalStorageDirectory().getPath() + "/CRcode.png");
                connection.connect();
                OutputStream os = connection.getOutputStream();
                byte[] bytes = new byte[1024];
                int count = 0;
                while (true) {
                    count = file.read(bytes);
                    if (count != -1) {
                        os.write(bytes);
                    } else {
                        break;
                    }
                }
                os.flush();
                os.close();
            } catch (Exception e) {

            }
            try {
                // 获取返回数据
                if (connection.getResponseCode() == 200) {
                    InputStream in_stream = connection.getInputStream();
                    InputStreamReader input_reader = new InputStreamReader(in_stream);
                    BufferedReader read =new BufferedReader(input_reader);
                    String line = read.readLine();
                    jsonObject = new JSONObject(line);
                    String message = jsonObject.getString("message");
                    Message msg =new Message();
                    msg.what = 1;
                    msg.getData().putString("msg", message);
                    StaticCla.handler12.sendMessage(msg);
                }
            } catch (Exception e) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
