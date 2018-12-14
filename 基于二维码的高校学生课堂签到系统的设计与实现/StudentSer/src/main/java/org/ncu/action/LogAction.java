package org.ncu.action;

import net.sf.json.JSONObject;
import org.ncu.pojo.Student;
import org.ncu.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class LogAction {
    @Autowired
    StudentService studentService;

    @RequestMapping(value = "/log.json")
    public void StudentLog(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("application/json");
        PrintWriter out = null;
        JSONObject json = new JSONObject();
        Student student = null;
        String account = request.getParameter("studentAccount");
        String pass = request.getParameter("studentPass");
        try {
            out = response.getWriter();
            if (0!=studentService.countStudentByAccount(Integer.parseInt(account))){
                if (0!=studentService.makeSureStudentPass(Integer.parseInt(account),pass)){
                    student = new Student();
                    student = studentService.findStudentByAccAndPass(Integer.parseInt(account),pass);
                    json.put("student",student);
                    json.put("status",1);
                    out.write(json.toString());

                }else {
                    json.put("status",2);
                    out.write(json.toString());
                }
            }else {
                json.put("status",3);
                out.write(json.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            out.flush();
            out.close();
        }
    }
}
