package org.ncu.service;

import org.ncu.dao.TeacherDao;
import org.ncu.pojo.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class TeacherService {
    private TeacherDao teacherDao;

    @Autowired
    public void setTeacherDao(TeacherDao teacherDao) {
        this.teacherDao = teacherDao;
    }

    public int getTeacherCount(int account) {
        return teacherDao.getTeacherCount(account);
    }

    public int makeSurePassword(int account,String password){return teacherDao.makeSurePassword(account,password);}

    public Teacher getTeacherMes(int account, String password) {
        return teacherDao.getTeacherMes(account, password);
    }

    public int updateMyInfo(Teacher teacher) {
        return teacherDao.updateMyInfo(teacher);
    }

    public Teacher findTeacherByAccount(int teacherAccount){return teacherDao.findTeacherByAccount(teacherAccount);}

}