package org.ncu.service;


import org.ncu.dao.ClassCurseDao;
import org.ncu.pojo.ClassCurse;
import org.ncu.pojo.Teacher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@Transactional
public class ClassCurseService {
    @Autowired
    private ClassCurseDao classCurseDao;

    public ArrayList<ClassCurse> findCurseInfo(String major,String classname){
        return classCurseDao.findCurseInfo(major,classname);
    };

    public int queryClassCurseCount(ClassCurse classCurse){
        return classCurseDao.queryClassCurseCount(classCurse);
    }

}