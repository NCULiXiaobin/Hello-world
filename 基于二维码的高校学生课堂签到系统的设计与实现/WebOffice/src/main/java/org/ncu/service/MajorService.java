package org.ncu.service;

import org.ncu.dao.MajorDao;
import org.ncu.pojo.Major;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Transactional
@Service
public class MajorService {
    private MajorDao majorDao;

    @Autowired
    public void setMajorDao(MajorDao majorDao) {
        this.majorDao = majorDao;
    }

    public ArrayList<Major> findAllMajor(){return majorDao.findAllMajor();}

    public Major findMajorByName(String majorName){return majorDao.findMajorByName(majorName);}
}
