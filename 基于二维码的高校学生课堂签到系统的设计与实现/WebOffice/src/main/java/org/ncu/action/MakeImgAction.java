package org.ncu.action;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.ncu.pojo.ClassCurse;
import org.ncu.pojo.Sign;
import org.ncu.pojo.TempImgSign;
import org.ncu.service.ClassCurseService;
import org.ncu.service.SignService;
import org.ncu.service.StudentService;
import org.ncu.service.TempImgSignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

@Controller
public class MakeImgAction {

    @Autowired
    private TempImgSignService tempImgSignService;
    @Autowired
    private ClassCurseService classCurseService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private SignService signService;

    private String content;
    private int alltime1;
    private BitMatrix bitMatrix = null;
    private String key1;
    private ImgSignThread imgSignThread;
    private String imgSignMajor;
    private String imgSignClassName;
    private Timestamp t_time;
    private  int teacher_account;

    @RequestMapping(value = "/makeImgKey.html")
    public void makeImgKey( HttpServletRequest request,HttpServletResponse response,MakeKeyInfo makeKeyInfo){
        String teacher = makeKeyInfo.getMakeUseAccount();
        String major = makeKeyInfo.getMakeUseMajor();
        imgSignMajor = major;
        String classname = makeKeyInfo.getSelectClass();
        imgSignClassName =classname;
        teacher_account = Integer.parseInt(makeKeyInfo.getMakeUseAccount());
        String curse = makeKeyInfo.getMakeUseCurse();
        String week = makeKeyInfo.getWeek();
        String indexCurse = String.valueOf(makeKeyInfo.getIndexCurse());
        key1 = major+classname+curse+teacher+week+indexCurse;
        ClassCurse classCurse = new ClassCurse();
        classCurse.setClass_curse_major(major);
        classCurse.setClass_curse_classname(classname);
        classCurse.setClass_curse_teacher(teacher);
        classCurse.setClass_curse_name(curse);
        classCurse.setClass_curse_day(week);
        classCurse.setClass_curse_index(indexCurse);
        int t_count = classCurseService.queryClassCurseCount(classCurse);
        alltime1 = 30;
        if (t_count!=0){
            if (request.getParameter("makeImgSignTime")!=""&&request.getParameter("makeImgSignTime")!=null){
                alltime1 = Integer.parseInt(request.getParameter("makeImgSignTime"));
                System.out.println(alltime1);
            }
            JSONObject json = new JSONObject();
            json.put("teacher", teacher);
            json.put("major",major);
            json.put("className", classname);
            json.put("curse",curse);
            json.put("week",week);
            json.put("indexCurse",indexCurse);
            content = json.toString();// 内容
            Cookie imgFlag = new Cookie("imgFlag","true");
            imgFlag.setMaxAge(alltime1*60);
            imgFlag.setPath("/");
            response.addCookie(imgFlag);
            try {
                String cookiekey = URLEncoder.encode(key1,"utf-8");
                Cookie imgInfo = new Cookie("imgInfo",cookiekey);
                imgInfo.setMaxAge(alltime1*60);
                imgInfo.setPath("/");
                response.addCookie(imgInfo);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            TempImgSign tempImgSign = new TempImgSign();
            tempImgSign.setTemp_img_index(content);
            tempImgSign.setTemp_img_key(key1);
            tempImgSignService.insertTempImgSign(tempImgSign);
            t_time = tempImgSignService.findImgIndexByKey(key1).getTemp_img_start_time();
            try {
                response.sendRedirect("makeimgkey.jsp");
            } catch (IOException e) {
                e.printStackTrace();
            }
            imgSignThread = new ImgSignThread(alltime1*60);
            imgSignThread.start();
        }else {
            try {
                response.getWriter().print("<script>alert('Make Sure Your Mess Current!');" +
                        "window.onload=function jump(){ window.location.href=\"makeimgkey.jsp\";}</script>");
                //response.sendRedirect("makenumkey.jsp");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @RequestMapping(value = "/code.html")
    public void showImgKey(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String cval = null;
        Cookie cs[] = request.getCookies();
        for(Cookie c:cs){
            if(c.getName().equals("imgInfo")){
                cval = c.getValue();
                cval = URLDecoder.decode(cval,"utf-8");
                System.out.println("name = " + cval);
            }
        }
        TempImgSign tempImgSign = tempImgSignService.findImgIndexByKey(cval);
        System.out.println(tempImgSign.getTemp_img_index());
        int width = 200; // 图像宽度
        int height = 200; // 图像高度
        Map<EncodeHintType, Object> hints = new HashMap<EncodeHintType, Object>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        // 生成矩阵
        try {
            bitMatrix = new MultiFormatWriter().encode(tempImgSign.getTemp_img_index(),
                    BarcodeFormat.QR_CODE, width, height, hints);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        String format = "png";// 图像类型
        OutputStream out = null;
        out = response.getOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, format, out);
        out.flush();
        out.close();
        System.out.println("输出成功.");
    }

    @RequestMapping(value = "/returnImgSuccess.html")
    public void ReturnSuccessPage(HttpServletResponse response){
        Cookie imgFlag = new Cookie("imgFlag","");
        imgFlag.setMaxAge(0);
        imgFlag.setPath("/");
        response.addCookie(imgFlag);
        Cookie imgInfo = new Cookie("imgInfo",content);
        imgInfo.setMaxAge(0);
        imgInfo.setPath("/");
        response.addCookie(imgInfo);
        imgSignThread.alltime=-1;
        try {
            response.sendRedirect("success.jsp");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/serchImgSignInfo.html")
    public void serchNumSignInfo(HttpServletResponse response){
        int studentCount = studentService.countStudentByClass(imgSignMajor,imgSignClassName);
        HashMap<String,String> hashMap = new HashMap<String, String>();
        hashMap.put("imgSignMajor",imgSignMajor);
        hashMap.put("imgSignClassName",imgSignClassName);
        hashMap.put("imgSignInfoCount", String.valueOf(studentCount));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(t_time));
        System.out.println(signService.selectMesByTime(String.valueOf(teacher_account),sdf.format(t_time)));
        hashMap.put("numSignInfoCount", String.valueOf(studentCount));
        String now_people = tempImgSignService.findImgIndexByKey(key1).getTemp_img_sign_people();
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

    class ImgSignThread extends Thread{
        int alltime;
        public ImgSignThread(int alltime){
            this.alltime = alltime;
        }
        @Override
        public void run() {
            while (alltime>=0) {
                alltime--;
                try {
                    //System.out.println(alltime);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            org.json.JSONObject jsonObject = new org.json.JSONObject(content);
            Sign sign = new Sign();
            sign.setSign_teacher(jsonObject.getString("teacher"));
            sign.setSign_major(jsonObject.getString("major"));
            sign.setSign_class(jsonObject.getString("className"));
            sign.setSign_time(tempImgSignService.findImgIndexByKey(key1).getTemp_img_start_time());
            sign.setSign_people(tempImgSignService.findImgIndexByKey(key1).getTemp_img_sign_people());
            signService.insertSignMes(sign);
            tempImgSignService.deleteTempImgSign(key1);
            System.out.println("线程结束");
        }
    }
}
