package org.ncu.service;

import org.ncu.dao.StudentDao;
import org.ncu.pojo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Transactional
@Service
public class StudentService {
    private StudentDao studentDao;

    @Autowired
    public void setStudentDao(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    public ArrayList<Student> findStudentByClass(String major,String className){
        return studentDao.findStudentByClass(major,className);
    }

    public int countStudentByClass(String major,String className){
        return studentDao.countStudentByClass(major,className);
    }

    public int countStudentByAccount(int account){
        return studentDao.countStudentByAccount(account);
    }

    public int makeSureStudentPass(int account,String password){
        return studentDao.makeSureStudentPass(account,password);
    }

    public Student findStudentByAccAndPass(int account,String password){
        return studentDao.findStudentByAccAndPass(account,password);
    }
}
