package org.ncu.action;

import net.sf.json.JSONArray;
import org.ncu.pojo.ClassCurse;
import org.ncu.service.ClassCurseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@Controller
public class ClassCurseAction {
    @Autowired
    private ClassCurseService classCurseService;

    @RequestMapping(value = "/lookClassCurse.html")
    public void lookClassCurse(HttpServletResponse response, HttpServletRequest request){
        String className = request.getParameter("activeTab");
        String major = request.getParameter("major");
        ArrayList<ClassCurse> classCurses = classCurseService.findCurseInfo(major,className);
//        System.out.println("查询完成");
//        for (int i=0;i<classCurses.size();i++){
//            System.out.println(classCurses.get(i).getClass_curse_name());
//        }
        JSONArray jsonArray = JSONArray.fromObject(classCurses);
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
