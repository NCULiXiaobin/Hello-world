package service;

import org.ncu.pojo.Teacher;
import org.ncu.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.transaction.annotation.Transactional;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

@ContextConfiguration("classpath*:/applicationContext.xml")
@Rollback
@Transactional
public class TeacherServiceTest extends AbstractTransactionalTestNGSpringContextTests{
    private TeacherService teacherService;

    @Autowired
    public void setTeacherService(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @Test
    public void getTeacher(){
        int i = teacherService.getTeacherCount(8888);
        assertEquals(i,1);
        System.out.println(i);
    }

    @Test
    public void getTeacherMes(){
        Teacher teacher = teacherService.getTeacherMes(8888,"123456");
        assertEquals(teacher.getTeacher_name(),"胡勇");
        System.out.println(teacher.getTeacher_name());
    }
}
