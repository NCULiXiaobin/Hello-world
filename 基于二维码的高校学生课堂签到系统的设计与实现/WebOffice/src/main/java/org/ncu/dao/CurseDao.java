package org.ncu.dao;

import org.ncu.pojo.Curse;

import java.util.ArrayList;

public interface CurseDao {

    public ArrayList<Curse> findCurseByMajor(String major);

    public int findCurseByCurseName(Curse curse);
}
