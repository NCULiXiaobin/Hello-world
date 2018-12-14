package org.ncu.action;

import org.ncu.pojo.*;
import org.ncu.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private TeacherService teacherService;
    @Autowired
    ClassInfoService classInfoService;
    @Autowired
    MajorService majorService;
    @Autowired
    ClassCurseService classCurseService;
    @Autowired
    CurseService curseService;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Teacher loginUser = (Teacher) request.getSession().getAttribute("teacher");
        if(loginUser == null){
            String loginCookieUserName = "";
            String loginCookiePassword = "";
            Cookie[] cookies = request.getCookies();
            if(null!=cookies){
                for(Cookie cookie : cookies){
                    if("cookie1".equals(cookie.getName())){
                        loginCookieUserName = cookie.getValue();
                    }else if("cookie2".equals(cookie.getName())){
                        loginCookiePassword = cookie.getValue();
                    }
                }
                if(!loginCookieUserName.equals("") && !loginCookiePassword.equals("")){
                    Teacher teacher = teacherService.findTeacherByAccount(Integer.parseInt(loginCookieUserName));
                    ArrayList<ClassInfo> classInfos = classInfoService.findClassByMajor(teacher.getTeacher_major());
                    ArrayList<Major> major = majorService.findAllMajor();
                    ArrayList<Curse> curses = curseService.findCurseByMajor(teacher.getTeacher_major());
                    if(loginCookiePassword.equals(teacher.getTeacher_password())){
                        request.getSession().setAttribute("teacher", teacher);
                        request.getSession().setAttribute("myClassInfo",classInfos);
                        request.getSession().setAttribute("major",major);
                        request.getSession().setAttribute("curses",curses);
                    }
                }
            }else {
                response.sendRedirect("log.jsp");
            }
        }
        return true;
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
