package org.ncu.service;

import org.ncu.dao.CurseDao;
import org.ncu.pojo.Curse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@Transactional
public class CurseService {
    @Autowired
    private CurseDao curseDao;

    public ArrayList<Curse> findCurseByMajor(String major){return curseDao.findCurseByMajor(major);}

    public int findCurseByCurseName(Curse curse){return curseDao.findCurseByCurseName(curse);}
}
