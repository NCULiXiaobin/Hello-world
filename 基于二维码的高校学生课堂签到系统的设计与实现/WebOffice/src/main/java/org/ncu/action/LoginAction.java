package org.ncu.action;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.ncu.pojo.*;
import org.ncu.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@Controller
public class LoginAction {
    @Autowired
    TeacherService teacherService;
    @Autowired
    ClassInfoService classInfoService;
    @Autowired
    MajorService majorService;
    @Autowired
    CurseService curseService;
    @Autowired
    StudentService studentService;

    @RequestMapping(value = "/index.html")
    public String loginPage() {
        return "success.jsp";
    }

    @RequestMapping(value = "/login.html")
    public ModelAndView Login(HttpServletRequest request, HttpServletResponse response,LogMes logMes) {
        if (logMes.getPerson().equals("teacher")) {
            if (0 != teacherService.getTeacherCount(Integer.parseInt(logMes.getAccount()))) {
                if (0 != teacherService.makeSurePassword(Integer.parseInt(logMes.getAccount()), logMes.getPassword())) {
                    Teacher teacher = teacherService.getTeacherMes(Integer.parseInt(logMes.getAccount()), logMes.getPassword());
                    ArrayList<ClassInfo> classInfos = classInfoService.findClassByMajor(teacher.getTeacher_major());
                    ArrayList<Major> major = majorService.findAllMajor();
                    System.out.println(teacher.getTeacher_major());
                    ArrayList<Curse> curses = curseService.findCurseByMajor(teacher.getTeacher_major());
                    Cookie teacherAccountCookie = new Cookie("cookie1", String.valueOf(teacher.getTeacher_account()));
                    Cookie teacherPasswordCookie = new Cookie("cookie2", teacher.getTeacher_password());
                    teacherAccountCookie.setMaxAge(60*60);
                    teacherAccountCookie.setPath("/");
                    teacherPasswordCookie.setMaxAge(60*60);
                    teacherPasswordCookie.setPath("/");
                    request.getSession().setAttribute("teacher",teacher);
                    request.getSession().setAttribute("myClassInfo", classInfos);
                    request.getSession().setAttribute("major", major);
                    request.getSession().setAttribute("curses",curses);
                    response.addCookie(teacherAccountCookie);
                    response.addCookie(teacherPasswordCookie);
                    return new ModelAndView("success.jsp");
                } else {
                    return new ModelAndView("log.jsp", "error", "*用户名或密码错误");
                }
            } else {
                return new ModelAndView("log.jsp", "error", "*非教师用户登录");
            }
        } else if (logMes.getPerson().equals("student")) {
            return new ModelAndView("fail.jsp");
        } else {
            return new ModelAndView("fail.jsp");
        }
    }

    @RequestMapping(value = "/ChangeUser.html")
    public String ChangeUser() {
        return "log.jsp";
    }


    @RequestMapping(value = "/LogOut.html")
    public String LogOut(HttpSession session,HttpServletResponse response) {
        Cookie teacherAccountCookie = new Cookie("cookie1", "");
        Cookie teacherPasswordCookie = new Cookie("cookie2", "");
        Cookie keyboo = new Cookie("keyboo","");
        Cookie imgFlag = new Cookie("imgFlag","");
        teacherAccountCookie.setMaxAge(0);
        teacherAccountCookie.setPath("/");
        teacherPasswordCookie.setMaxAge(0);
        teacherPasswordCookie.setPath("/");
        keyboo.setMaxAge(0);
        keyboo.setPath("/");
        imgFlag.setMaxAge(0);
        imgFlag.setPath("/");
        response.addCookie(teacherAccountCookie);
        response.addCookie(teacherPasswordCookie);
        response.addCookie(keyboo);
        response.addCookie(imgFlag);
        session.invalidate();
        return "log.jsp";
    }

    @RequestMapping(value = "/forSuccess.html")
    public String ForSuccess(){
        return "success.jsp";
    }
}
