package org.ncu.action;

import net.sf.json.JSONObject;
import org.ncu.service.NumSignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;

@Controller
public class NumSignAction {
    @Autowired
    NumSignService numSignService;

    @RequestMapping(value = "numSign.json")
    public void tempNumSign(HttpServletRequest request, HttpServletResponse response){
        PrintWriter out = null;
        JSONObject jsonObject = new JSONObject();
        response.setCharacterEncoding("utf-8");
        String numberSign = request.getParameter("numberSign");
        String studentSignAccount  = request.getParameter("studentAccount");
        int n = numSignService.findTempNumSign(numberSign);
        int count = 0;
        try {
            out = response.getWriter();
            if (n==0){
                jsonObject.put("message","未找到签到信息");
                out.write(jsonObject.toString());
                out.flush();
                out.close();
            }else {
                if(numSignService.findNumSignedPeo(numberSign)!=null){
                    String signedPeople = numSignService.findNumSignedPeo(numberSign);
                    StringTokenizer stringTokenizer = new StringTokenizer(signedPeople,"+");
                    ArrayList<String> person = new ArrayList<String>();
                    while (stringTokenizer.hasMoreTokens()){
                        person.add(stringTokenizer.nextToken());
                    }
                    for (int i = 0;i<person.size();i++){
                        System.out.println(person.get(i));
                        if (studentSignAccount.equals(person.get(i))){
                            count++;
                        }
                    }
                }
                if (count!=0){
                    jsonObject.put("message","已签到！请勿重复签到");
                    out.write(jsonObject.toString());
                    out.flush();
                    out.close();
                }else {
                    numSignService.numSignIn(studentSignAccount,numberSign);
                    jsonObject.put("message","签到成功");
                    out.write(jsonObject.toString());
                    out.flush();
                    out.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
