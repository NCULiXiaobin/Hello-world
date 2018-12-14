package org.ncu.action;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.ncu.pojo.Sign;
import org.ncu.service.ClassInfoService;
import org.ncu.service.SignService;
import org.ncu.service.StudentService;
import org.ncu.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@Controller
public class LookEnd {
    @Autowired
    private SignService signService;
    @Autowired
    StudentService studentService;
    @Autowired
    TeacherService teacherService;
    @RequestMapping(value = "/lookEnd.html")
    public void lookEnd(HttpServletRequest request, HttpServletResponse response){
        String teacherAccount= request.getParameter("teacherAccount");
        ArrayList<Sign> list = signService.selectSignMes(teacherAccount);
        ArrayList<List> t_list = new ArrayList<List>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        for (int i = 0;i<list.size();i++){
            ArrayList<String> list1 = new ArrayList<String>();
            list1.add(list.get(i).getSign_teacher());
            list1.add(list.get(i).getSign_major());
            list1.add(list.get(i).getSign_class());
            Timestamp timestamp = list.get(i).getSign_time();
            String t_time = sdf.format(timestamp);
            System.out.println(timestamp);
            list1.add(t_time);
            int count1 = studentService.countStudentByClass(list.get(i).getSign_major(),list.get(i).getSign_class());
            list1.add(String.valueOf(count1));
            String last_people = list.get(i).getSign_people();
//            StringTokenizer pe = new StringTokenizer(last_people,"+");
//            int nowNum = pe.countTokens();
            list1.add("1");
            String teacherName = teacherService.findTeacherByAccount(Integer.parseInt(list.get(i).getSign_teacher())).getTeacher_name();
            list1.add(teacherName);
            list1.add(last_people);
            t_list.add(list1);
        }
        JSONArray jsonArray = JSONArray.fromObject(t_list);
        System.out.println(jsonArray.toString());
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write(jsonArray.toString());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
