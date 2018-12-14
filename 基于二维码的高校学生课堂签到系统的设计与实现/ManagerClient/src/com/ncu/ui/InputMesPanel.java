package com.ncu.ui;

import com.ncu.dao.MajorDao;
import com.ncu.pojo.Major;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class InputMesPanel extends JPanel {
    JLabel account,name,sex,age,major,phone,phoneex,emile,extra;
    JTextField account1,name1,age1,phone1,phoneex1,emile1;
    JComboBox major1;
    MajorDao majorDao;
    ButtonGroup group;
    ArrayList<Major> list1;
    JRadioButton radio1,radio2;
    JTextArea extra1;
    public InputMesPanel(){
        list1 = new ArrayList<>();
        majorDao = new MajorDao();
        this.setLayout(null);
        account = new JLabel("工号");
        this.add(account);
        account.setBounds(0,5,30,30);
        account1 = new JTextField(15);
        this.add(account1);
        account1.setBounds(40,5,160,30);
        name = new JLabel("名字");
        this.add(name);
        name.setBounds(210,5,30,30);
        name1 = new JTextField(15);
        this.add(name1);
        name1.setBounds(250,5,160,30);
        sex = new JLabel("性别");
        this.add(sex);
        sex.setBounds(0,50,30,30);
        group = new ButtonGroup();
        radio1 = new JRadioButton("男");
        radio2 = new JRadioButton("女");
        group.add(radio1);
        radio1.setBounds(40,50,50,30);
        group.add(radio2);
        radio2.setBounds(100,50,50,30);
        this.add(radio1);
        this.add(radio2);
        age = new JLabel("年龄");
        this.add(age);
        age.setBounds(210,50,30,30);
        age1 = new JTextField(5);
        this.add(age1);
        age1.setBounds(250,50,80,30);
        major = new JLabel("专业");
        this.add(major);
        major.setBounds(0,95,30,30);
        major1 = new JComboBox();
        major1 = new JComboBox();
        list1 = majorDao.FindAllMajor();
        for (int i = 0;i<list1.size();i++){
            major1.addItem(list1.get(i).getMajor_name());
        }
        this.add(major1);
        major1.setBounds(90,95,80,30);
        phone = new JLabel("联系方式");
        this.add(phone);
        phone.setBounds(0,140,60,30);
        phone1 = new JTextField(15);
        this.add(phone1);
        phone1.setBounds(90,140,240,30);
        phoneex = new JLabel("紧急联系方式");
        this.add(phoneex);
        phoneex.setBounds(0,185,80,30);
        phoneex1 = new JTextField(15);
        this.add(phoneex1);
        phoneex1.setBounds(90,185,240,30);
        emile = new JLabel("邮箱");
        this.add(emile);
        emile.setBounds(0,230,30,30);
        emile1 = new JTextField(15);
        this.add(emile1);
        emile1.setBounds(90,230,160,30);
        extra = new JLabel("其他描述");
        this.add(extra);
        extra.setBounds(0,275,80,30);
        extra1 = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(extra1);
        this.add(scrollPane);
        scrollPane.setBounds(90,275,300,200);
    }
}
