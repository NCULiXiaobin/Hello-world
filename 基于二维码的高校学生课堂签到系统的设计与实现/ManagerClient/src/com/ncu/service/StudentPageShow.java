package com.ncu.service;

import com.ncu.pojo.Student;
import com.ncu.pojo.Teacher;

import java.util.ArrayList;
import java.util.Vector;

public class StudentPageShow {
    public Vector<Vector> selectStudentPage(int nowpage, ArrayList<Student> list){
        Vector<Vector> vectors = new Vector<>();
        int start = (nowpage-1)*19;
        if (nowpage*19<=list.size()){
            for (int i = 0;i<19;i++){
                Vector<String> vector = new Vector<>();
                vector.add(String.valueOf(list.get(start).getStudent_account()));
                vector.add(String.valueOf(list.get(start).getStudent_password()));
                vector.add(String.valueOf(list.get(start).getStudent_cardid()));
                vector.add(String.valueOf(list.get(start).getStudent_name()));
                vector.add(String.valueOf(list.get(start).getStudent_sex()));
                vector.add(String.valueOf(list.get(start).getStudent_joinyear()));
                vector.add(String.valueOf(list.get(start).getStudent_stuyear()));
                vector.add(list.get(start).getStudent_major());
                vector.add(String.valueOf(list.get(start).getStudent_class()));
                vector.add(String.valueOf(list.get(start).getStudent_phone()));
                vector.add(String.valueOf(list.get(start).getStudent_emile()));
                vector.add(list.get(start).getStudent_adress());
                vector.add(list.get(start).getStudent_extra());
                vectors.add(vector);
                start++;
            }
        }
        else if (nowpage*19>list.size()){
            for (int i = 0;i<list.size()-(nowpage-1)*19;i++){
                Vector<String> vector = new Vector<>();
                vector.add(String.valueOf(list.get(start).getStudent_account()));
                vector.add(String.valueOf(list.get(start).getStudent_password()));
                vector.add(String.valueOf(list.get(start).getStudent_cardid()));
                vector.add(String.valueOf(list.get(start).getStudent_name()));
                vector.add(String.valueOf(list.get(start).getStudent_sex()));
                vector.add(String.valueOf(list.get(start).getStudent_joinyear()));
                vector.add(String.valueOf(list.get(start).getStudent_stuyear()));
                vector.add(list.get(start).getStudent_major());
                vector.add(String.valueOf(list.get(start).getStudent_class()));
                vector.add(String.valueOf(list.get(start).getStudent_phone()));
                vector.add(String.valueOf(list.get(start).getStudent_emile()));
                vector.add(list.get(start).getStudent_adress());
                vector.add(list.get(start).getStudent_extra());
                vectors.add(vector);
                start++;
            }
        }
        return vectors;
    }
}
