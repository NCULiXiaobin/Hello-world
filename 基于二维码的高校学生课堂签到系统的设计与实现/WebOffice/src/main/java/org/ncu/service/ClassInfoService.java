package org.ncu.service;


import org.ncu.dao.ClassInfoDao;
import org.ncu.pojo.ClassInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Transactional
@Service
public class ClassInfoService {
    private ClassInfoDao classInfoDao;

    @Autowired
    public void setClassInfoDao(ClassInfoDao classInfoDao) {
        this.classInfoDao = classInfoDao;
    }

    public ArrayList<ClassInfo> findClassByMajor(String major){return classInfoDao.findClassByMajor(major);}

    public ClassInfo findClassByName(String major,String className){
        return  classInfoDao.findClassByName(major,className);
    }
}
