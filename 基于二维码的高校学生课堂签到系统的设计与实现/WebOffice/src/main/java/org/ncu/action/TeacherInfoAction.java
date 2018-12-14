package org.ncu.action;

import org.ncu.pojo.ClassInfo;
import org.ncu.pojo.Major;
import org.ncu.pojo.Teacher;
import org.ncu.service.ClassInfoService;
import org.ncu.service.MajorService;
import org.ncu.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Controller
public class TeacherInfoAction {
    @Autowired
    TeacherService teacherService;
    @Autowired
    ClassInfoService classInfoService;

    @RequestMapping(value = "/MyMes.html")
    public ModelAndView FindMyInfo(HttpServletRequest request){
        return new ModelAndView("myinfo.jsp");
    }

    @RequestMapping(value = "/ChangeMyMes.html")
    public ModelAndView UpdateMyInfo(HttpServletRequest request,TeacherInfo teacherInfo){
        Teacher teacher = new Teacher();
        teacher.setTeacher_id(teacherInfo.getTeacherId());
        teacher.setTeacher_name(teacherInfo.getTeacherName());
        teacher.setTeacher_account(teacherInfo.getTeacherAccount());
        teacher.setTeacher_password(teacherInfo.getTeacherPassword());
        teacher.setTeacher_age(teacherInfo.getTeacherAge());
        teacher.setTeacher_sex(teacherInfo.getTeacherSex());
        teacher.setTeacher_major(teacherInfo.getTeacherMajor());
        teacher.setTeacher_phone(teacherInfo.getTeacherPhone());
        teacher.setTeacher_phoneex(teacherInfo.getTeacherPhoneex());
        teacher.setTeacher_emile(teacherInfo.getTeacherEmile());
        teacher.setTeacher_extra(teacherInfo.getTeacherExtr());
        teacherService.updateMyInfo(teacher);
        request.getSession().setAttribute("teacher",teacher);
        ArrayList<ClassInfo> classInfos = classInfoService.findClassByMajor(teacher.getTeacher_major());
        request.getSession().setAttribute("myClassInfo",classInfos);
        return new ModelAndView("myinfo.jsp","message","修改成功");
    }
}
