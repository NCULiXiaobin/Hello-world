package org.ncu.service;

import org.ncu.dao.SignDao;
import org.ncu.dao.TempImgSignDao;
import org.ncu.dao.TempNumSignDao;
import org.ncu.pojo.Sign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;

@Transactional
@Service
public class SignService {
    @Autowired
    private SignDao signDao;

    public void insertSignMes(Sign sign){signDao.insertSignMes(sign);}
    public ArrayList<Sign> selectSignMes(String teacherName){return signDao.selectSignMes(teacherName);}
    public Sign selectMesByTime(String teacherAccount,String timestamp){return signDao.selectMesByTime(teacherAccount,timestamp);}
}
