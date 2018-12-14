package org.ncu.action;


import net.sf.json.JSONArray;
import org.ncu.pojo.Student;
import org.ncu.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@Controller
public class ClassDetailedInfoAction {
    @Autowired
    StudentService studentService;

    private ArrayList<Student> student;

    @RequestMapping(value = "/detailedInf.html")
    public ModelAndView showDetailedClassInfo(HttpServletRequest request,ClassDetailedInfo classDetailedInfo){
        int i = 0;
        String temp_major = classDetailedInfo.getDetailedMajor();
        String temp_class = classDetailedInfo.getDetailedName();
        student = studentService.findStudentByClass(temp_major,temp_class);
        int t_count = studentService.countStudentByClass(temp_major,temp_class);
        request.setAttribute("count",t_count);
        request.setAttribute("major",temp_major);
        request.setAttribute("className",temp_class);
        if (t_count%10==0){
            i = t_count/10;
        } else{
            i = t_count/10+1;
        }
        request.setAttribute("page",i);
        return new ModelAndView("classDetailedInfo.jsp","student",student);
    }

    @RequestMapping(value = "/next.html")
    public void nextPage(HttpServletResponse response,ClassDetailedInfo classDetailedInfo){
        //System.out.println(classDetailedInfo.getNowPage());
        int i = classDetailedInfo.getNowPage()-1;
        response.setCharacterEncoding("UTF-8");
        ArrayList<Student> students = new ArrayList<Student>();
        if ((i+1)*10>student.size()){
            for (int j = i*10;j<student.size();j++ ){
                students.add(student.get(j));
            }
        }else {
            for (int j = i*10;j<i*10+10;j++ ){
                students.add(student.get(j));
            }
        }
        PrintWriter out= null;
        try {
            JSONArray jsonArray = JSONArray.fromObject(students);
            out = response.getWriter();
            out.write(jsonArray.toString());
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
