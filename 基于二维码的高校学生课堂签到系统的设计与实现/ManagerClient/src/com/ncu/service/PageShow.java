package com.ncu.service;

import com.ncu.pojo.Teacher;

import java.util.ArrayList;
import java.util.Vector;

public class PageShow {
    public Vector<Vector> selectPage(int nowpage,ArrayList<Teacher> list){
        Vector<Vector> vectors = new Vector<>();
        int start = (nowpage-1)*19;
        if (nowpage*19<=list.size()){
            for (int i = 0;i<19;i++){
                Vector<String> vector = new Vector<>();
                vector.add(String.valueOf(list.get(start).getTeacher_account()));
                vector.add(String.valueOf(list.get(start).getTeacher_password()));
                vector.add(String.valueOf(list.get(start).getTeacher_name()));
                vector.add(String.valueOf(list.get(start).getTeacher_sex()));
                vector.add(String.valueOf(list.get(start).getTeacher_age()));
                vector.add(String.valueOf(list.get(start).getTeacher_job()));
                vector.add(String.valueOf(list.get(start).getTeacher_phone()));
                vector.add(String.valueOf(list.get(start).getTeacher_phoneex()));
                vector.add(String.valueOf(list.get(start).getTeacher_emile()));
                vector.add(String.valueOf(list.get(start).getTeacher_extra()));
                vectors.add(vector);
                start++;
            }
        }
        else if (nowpage*19>list.size()){
            for (int i = 0;i<list.size()-(nowpage-1)*19;i++){
                Vector<String> vector = new Vector<>();
                vector.add(String.valueOf(list.get(start).getTeacher_account()));
                vector.add(String.valueOf(list.get(start).getTeacher_password()));
                vector.add(String.valueOf(list.get(start).getTeacher_name()));
                vector.add(String.valueOf(list.get(start).getTeacher_sex()));
                vector.add(String.valueOf(list.get(start).getTeacher_age()));
                vector.add(String.valueOf(list.get(start).getTeacher_job()));
                vector.add(String.valueOf(list.get(start).getTeacher_phone()));
                vector.add(String.valueOf(list.get(start).getTeacher_phoneex()));
                vector.add(String.valueOf(list.get(start).getTeacher_emile()));
                vector.add(String.valueOf(list.get(start).getTeacher_extra()));
                vectors.add(vector);
                start++;
            }
        }
        return vectors;
    }
}
