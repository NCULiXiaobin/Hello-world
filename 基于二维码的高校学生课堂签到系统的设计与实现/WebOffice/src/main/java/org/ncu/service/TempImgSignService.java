package org.ncu.service;

import net.sf.json.JSON;
import org.ncu.dao.TempImgSignDao;
import org.ncu.pojo.TempImgSign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class TempImgSignService {
    @Autowired
    private TempImgSignDao tempImgSignDao;

    public void insertTempImgSign(TempImgSign tempImgSign){tempImgSignDao.insertTempImgSign(tempImgSign);}

    public void deleteTempImgSign(String key1){tempImgSignDao.deleteTempImgSign(key1); }

    public TempImgSign findImgIndexByKey(String temp_img_key){return tempImgSignDao.findImgIndexByKey(temp_img_key);}
}
