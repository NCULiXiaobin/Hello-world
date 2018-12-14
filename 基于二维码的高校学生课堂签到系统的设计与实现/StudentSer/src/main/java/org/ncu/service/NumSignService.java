package org.ncu.service;

import org.ncu.dao.NumSignDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class NumSignService {
    @Autowired
    private NumSignDao numSignDao;

    public int findTempNumSign(String temp_index){return numSignDao.findTempNumSign(temp_index);}
    public void numSignIn(String temp_sign_people,String temp_index){numSignDao.numSignIn(temp_sign_people,temp_index);}
    public String findNumSignedPeo(String temp_index){return numSignDao.findNumSignedPeo(temp_index);}
}
