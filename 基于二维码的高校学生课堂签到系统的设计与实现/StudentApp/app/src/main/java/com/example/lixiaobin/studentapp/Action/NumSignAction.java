package com.example.lixiaobin.studentapp.Action;

import android.util.Log;
import com.example.lixiaobin.studentapp.Configer.MakeUri;
import com.example.lixiaobin.studentapp.Configer.ServletConfig;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class NumSignAction {
    String message;

    public String NumSign(String number,String studentAccount){
        HttpURLConnection connection = null;
        JSONObject jsonObject = null;
        String address = "http://"+ ServletConfig.IP+":"+ServletConfig.PORT+"/numSign.json";
        HashMap<String,String> params = new HashMap<>();
        params.put("numberSign",number);
        params.put("studentAccount",studentAccount);
        try {
            String uri = MakeUri.getURLWithParams(address,params);
            Log.i("NumSignAction",uri);
            URL url = new URL(uri);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.connect();
            int stat = connection.getResponseCode();
            InputStream is = connection.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            if (stat==200){
                String WebMessage = br.readLine();
                jsonObject = new JSONObject(WebMessage);
                message = jsonObject.getString("message");
            }
            br.close();
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return message;
    }
}
