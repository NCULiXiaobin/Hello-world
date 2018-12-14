package org.ncu.service;

import org.ncu.dao.TempNumSignDao;
import org.ncu.pojo.TempNumSign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class TempNumSignService {
    @Autowired
    private TempNumSignDao tempNumSignDao;

    public void insertTempNumSign(String temp_index,String temp_num_index){tempNumSignDao.insertTempNumSign(temp_index,temp_num_index);}
    public TempNumSign findByTempIndex(String temp_index){return tempNumSignDao.findByTempIndex(temp_index);}
    public void deleteTempNumSign(String temp_index){tempNumSignDao.deleteTempNumSign(temp_index);}
}
