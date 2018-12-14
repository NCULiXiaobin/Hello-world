package com.ncu.web;

import com.ncu.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by LiXiaobin on 2017/12/20.
 */
public class RegisterServlet extends HttpServlet {
    String message;
    UserDao userDao = new UserDao();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-Type", "text/html;charset=UTF-8");
        int account = Integer.parseInt(request.getParameter("account"));
        String password = request.getParameter("password");
        String name = request.getParameter("user_name");
        String sex = request.getParameter("user_sex");
        String ip = request.getLocalAddr();
        int i = userDao.FindCounts(account);
        if (i!=0){
             message = "已存在该用户，注册失败";
        }
        else {
            userDao.InsertUser(account,password,name,sex,ip);
            message = "注册成功";
        }
        response.getWriter().append(message);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
