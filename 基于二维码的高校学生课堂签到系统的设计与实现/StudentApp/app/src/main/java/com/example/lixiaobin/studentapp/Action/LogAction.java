package com.example.lixiaobin.studentapp.Action;

import android.net.Uri;
import android.util.Log;
import com.example.lixiaobin.studentapp.Configer.MakeUri;
import com.example.lixiaobin.studentapp.Configer.ServletConfig;
import com.example.lixiaobin.studentapp.Obj.Student;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class LogAction {
    public Student StudentLogIn(String account,String password){
        Student student = null;
        HttpURLConnection connection = null;
        JSONObject jsonObject = null;
        String address = "http://"+ServletConfig.IP+":"+ServletConfig.PORT+"/log.json";
        HashMap<String,String> params = new HashMap<>();
        params.put("studentAccount",account);
        params.put("studentPass",password);
        try {
            String makeurl = MakeUri.getURLWithParams(address,params);
            Log.i("LogActivity",makeurl);
            URL url = new URL(makeurl);
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
            if (stat == 200) {
                String userjson = br.readLine();
                if (userjson!=null){
                    jsonObject = new JSONObject(userjson);
                    int status = jsonObject.getInt("status");
                    if (status==1){
                        JSONObject e = jsonObject.getJSONObject("student");
                        student = new Student();
                        student.setStudent_id(e.getString("student_id"));
                        student.setStudent_account(e.getInt("student_account"));
                        student.setStudent_password(e.getString("student_password"));
                        student.setStudent_cardid(e.getString("student_cardid"));
                        student.setStudent_name(e.getString("student_name"));
                        student.setStudent_sex(e.getString("student_sex"));
                        student.setStudent_joinyear(e.getString("student_joinyear"));
                        student.setStudent_stuyear(e.getInt("student_stuyear"));
                        student.setStudent_major(e.getString("student_major"));
                        student.setStudent_class(e.getString("student_class"));
                        student.setStudent_phone(e.getString("student_phone"));
                        student.setStudent_emile(e.getString("student_emile"));
                        student.setStudent_adress(e.getString("student_adress"));
                        student.setStudent_extra(e.getString("student_extra"));
                    }
                }
            }
            br.close();
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return student;
    }
}
