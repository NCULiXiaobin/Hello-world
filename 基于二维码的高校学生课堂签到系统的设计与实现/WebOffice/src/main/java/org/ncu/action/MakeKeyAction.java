package org.ncu.action;

import net.sf.json.JSONArray;
import org.json.JSONObject;
import org.ncu.pojo.ClassCurse;
import org.ncu.pojo.Curse;
import org.ncu.pojo.Sign;
import org.ncu.pojo.TempNumSign;
import org.ncu.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class MakeKeyAction {
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private MajorService majorService;
    @Autowired
    private ClassInfoService classInfoService;
    @Autowired
    private TempNumSignService tempNumSignService;
    @Autowired
    CurseService curseService;
    @Autowired
    ClassCurseService classCurseService;
    @Autowired
    StudentService studentService;
    @Autowired
    SignService signService;

    private  int numKeybord;
    private int alltime;
    private NumSignThread numSignThread;

    private String numSignMajor;
    private String numSignClassName;
    private String numSignCurse;
    private Timestamp t_time;
    private int teacher_account;
    @RequestMapping(value = "/makeNumKey.html")
    public void makeNumKey(MakeKeyInfo makeKeyInfo,HttpServletRequest request,HttpServletResponse response){
        response.setCharacterEncoding("utf-8");
        numSignMajor = makeKeyInfo.getMakeUseMajor();
        numSignClassName = makeKeyInfo.getSelectClass();
        numSignCurse = makeKeyInfo.getMakeUseCurse();
        Curse t_curse = new Curse();
        t_curse.setCurse_major(numSignMajor);
        t_curse.setCurse_name(numSignCurse);
        teacher_account = Integer.parseInt(makeKeyInfo.getMakeUseAccount());
        int major = majorService.findMajorByName(makeKeyInfo.getMakeUseMajor()).getMajor_id();
        int teacher = teacherService.findTeacherByAccount(Integer.parseInt(makeKeyInfo.getMakeUseAccount())).getTeacher_id();
        int classinfo = classInfoService.findClassByName(makeKeyInfo.getMakeUseMajor(),makeKeyInfo.getSelectClass()).getClass_id();
        int curse = curseService.findCurseByCurseName(t_curse);
        int week = Integer.parseInt(makeKeyInfo.getWeek());
        int indexCurse = makeKeyInfo.getIndexCurse();
        ClassCurse classCurse = new ClassCurse();
        classCurse.setClass_curse_major(makeKeyInfo.getMakeUseMajor());
        classCurse.setClass_curse_classname(makeKeyInfo.getSelectClass());
        classCurse.setClass_curse_teacher(makeKeyInfo.getMakeUseAccount());
        classCurse.setClass_curse_name(makeKeyInfo.getMakeUseCurse());
        classCurse.setClass_curse_day(makeKeyInfo.getWeek());
        classCurse.setClass_curse_index(String.valueOf(makeKeyInfo.getIndexCurse()));
        int t_count = classCurseService.queryClassCurseCount(classCurse);
        alltime = 30;
        if (t_count!=0){
            if (request.getParameter("makeNumSignTime")!=""){
                alltime = Integer.parseInt(request.getParameter("makeNumSignTime"));
            }
            StringBuilder str=new StringBuilder();//定义变长字符串
            Random random=new Random();
            //随机生成数字，并添加到字符串
            for(int i=0;i<2;i++){
                str.append(random.nextInt(10));
            }
            //将字符串转换为数字并输出
            int num=Integer.parseInt(str.toString());
            numKeybord = teacher*10000000+major*1000000+classinfo*100000+curse*10000+week*1000+indexCurse*100+num;
            net.sf.json.JSONObject jsonObject = new net.sf.json.JSONObject();
            jsonObject.put("teacher",makeKeyInfo.getMakeUseAccount());
            jsonObject.put("major",makeKeyInfo.getMakeUseMajor());
            jsonObject.put("class",makeKeyInfo.getSelectClass());
            String numSignInfo = jsonObject.toString();
            tempNumSignService.insertTempNumSign(String.valueOf(numKeybord),numSignInfo);
            t_time = tempNumSignService.findByTempIndex(String.valueOf(numKeybord)).getTemp_start_time();
            request.setAttribute("className",makeKeyInfo.getSelectClass());
            Cookie keyboo = new Cookie("keyboo",String.valueOf(numKeybord));
            keyboo.setMaxAge(60*alltime);
            keyboo.setPath("/");
            response.addCookie(keyboo);
            try {
                response.sendRedirect("makenumkey.jsp");
            } catch (IOException e) {
                e.printStackTrace();
            }
            numSignThread = new NumSignThread(alltime*60,String.valueOf(numKeybord));
            numSignThread.start();
        }
        else {
            try {
                response.getWriter().print("<script>alert('Make Sure Your Mess Current!');" +
                        "window.onload=function jump(){ window.location.href=\"makenumkey.jsp\";}</script>");
                //response.sendRedirect("makenumkey.jsp");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @RequestMapping(value = "/returnSuccess.html")
    public void ReturnSuccessPage(HttpServletRequest request,HttpServletResponse response){
        Cookie keyboo = new Cookie("keyboo",String.valueOf(numKeybord));
        keyboo.setMaxAge(0);
        keyboo.setPath("/");
        response.addCookie(keyboo);
        String kkb = request.getParameter("kkb");
        tempNumSignService.deleteTempNumSign(kkb);
        numSignThread.alltime=-1;
        try {
            response.sendRedirect("success.jsp");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/lookLeftTime.html")
    public void LookLeftTime(HttpServletRequest request,HttpServletResponse response){
        Timestamp time = new Timestamp(new Date().getTime());
        String kkb = request.getParameter("kkb");
        TempNumSign tempNumSign = tempNumSignService.findByTempIndex(kkb);
        long time2 = tempNumSign.getTemp_start_time().getTime();
        long s = alltime*60-(time.getTime()-time2)/1000;
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.write(String.valueOf(s));
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/serchNumSignInfo.html")
    public void serchNumSignInfo(HttpServletResponse response){
        System.out.println(teacher_account);
        int studentCount = studentService.countStudentByClass(numSignMajor,numSignClassName);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(t_time));
        System.out.println(signService.selectMesByTime(String.valueOf(teacher_account),sdf.format(t_time)));
        HashMap<String,String> hashMap = new HashMap<String, String>();
        hashMap.put("numSignMajor",numSignMajor);
        hashMap.put("numSignClassName",numSignClassName);
        hashMap.put("numSignInfoCount", String.valueOf(studentCount));
        String now_people = tempNumSignService.findByTempIndex(String.valueOf(numKeybord)).getTemp_sign_people();
        System.out.println(now_people);
        StringTokenizer pe = new StringTokenizer(now_people,"+");
        int nowNum = pe.countTokens();
//        if (now_people!=null){
//            ArrayList<String> peopleList = new ArrayList<String>();
//            StringTokenizer pe = new StringTokenizer(now_people,"+");
//            int nowNum = pe.countTokens();
//            while (pe.hasMoreTokens()){
//                peopleList.add(pe.nextToken());
//            }
//            hashMap.put("NumSignNowCount",String.valueOf(nowNum));
//            hashMap.put("numSignLeftStudent",now_people);
//        }
        hashMap.put("NumSignNowCount",String.valueOf(nowNum));
        hashMap.put("numSignLeftStudent",now_people);
        JSONArray jsonArray = JSONArray.fromObject(hashMap);
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

    class NumSignThread extends Thread{
            int alltime;
            String keybord;
            public NumSignThread(int alltime,String keybord){
                this.alltime = alltime;
                this.keybord = keybord;
            }
            @Override
            public void run() {
                while (alltime>=0){
                    alltime--;
                    try {
                        //System.out.println(alltime);
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                TempNumSign tempNumSign = tempNumSignService.findByTempIndex(keybord);
                JSONObject jsonObject = new JSONObject(tempNumSign.getTemp_num_index());
                Sign sign = new Sign();
                sign.setSign_teacher(jsonObject.getString("teacher"));
                sign.setSign_major(jsonObject.getString("major"));
                sign.setSign_class(jsonObject.getString("class"));
                sign.setSign_time(tempNumSign.getTemp_start_time());
                sign.setSign_people(tempNumSign.getTemp_sign_people());
                signService.insertSignMes(sign);
                tempNumSignService.deleteTempNumSign(keybord);
            }
    }
}
