package com.ncu;


import java.io.IOException;
import java.util.List;

/**
 * Created by LiXiaobin on 2017/11/8.
 */
public class SearchServlet extends javax.servlet.http.HttpServlet {
    ConnectMsql connectMsql = new ConnectMsql();
    String sql;
    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String keyword = request.getParameter("keyword");
        System.out.println(keyword);
        sql = "select * from sakila.info where searchKey="+'"'+keyword+'"';
        //System.out.println(sql);
        List list = connectMsql.Search(sql);
//        for (int i = 0;i < list.size();i++){
//            System.out.println(list.get(i));
//        }
        response.getWriter().append(list.toString());
    }
}
