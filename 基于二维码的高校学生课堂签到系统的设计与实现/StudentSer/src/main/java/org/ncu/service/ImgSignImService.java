package org.ncu.service;

import org.ncu.dao.ImgSignDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImgSignImService {

    @Autowired
    private ImgSignDao imgSignDao;

    public int findCountImgIndexByKey(String temp_img_key){return  imgSignDao.findCountImgIndexByKey(temp_img_key);}
    public String findImgSignedPeo(String temp_img_key){return imgSignDao.findImgSignedPeo(temp_img_key);}
    public void ImgSignIn(String temp_img_sign_people,String temp_img_key){imgSignDao.ImgSignIn(temp_img_sign_people,temp_img_key);}
}
