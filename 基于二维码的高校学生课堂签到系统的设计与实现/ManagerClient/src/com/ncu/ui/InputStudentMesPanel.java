package com.ncu.ui;

import com.ncu.dao.ClassInfoDao;
import com.ncu.dao.MajorDao;
import com.ncu.pojo.ClassInfo;
import com.ncu.pojo.Major;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class InputStudentMesPanel extends JPanel {
    JLabel account,name,sex,cardid,joinyear,stuyear,major,className,phone,emile,address,extra;
    JTextField account1,cardId1,name1,joinyear1,phone1,emile1,address1;
    JComboBox sex1,stuyear1,major1,classname1;
    JTextArea extra1;
    ArrayList<Major> list1;
    MajorDao majorDao;
    ArrayList<ClassInfo> list2;
    ClassInfoDao classInfoDao;
    public InputStudentMesPanel(){
        majorDao = new MajorDao();
        classInfoDao = new ClassInfoDao();
        this.setLayout(null);
        account = new JLabel("学号");
        this.add(account);
        account.setBounds(0,5,30,30);
        account1 = new JTextField();
        this.add(account1);
        account1.setBounds(40,5,160,30);
        name = new JLabel("姓名");
        this.add(name);
        name.setBounds(210,5,30,30);
        name1 = new JTextField();
        this.add(name1);
        name1.setBounds(250,5,160,30);
        cardid = new JLabel("身份证号");
        this.add(cardid);
        cardid.setBounds(0,55,80,30);
        cardId1 = new JTextField();
        this.add(cardId1);
        cardId1.setBounds(70,55,230,30);
        JLabel mess1 = new JLabel("(*可由学生填写)");
        this.add(mess1);
        mess1.setBounds(310,55,100,30);
        sex = new JLabel("性别");
        this.add(sex);
        sex.setBounds(0,100,30,30);
        sex1 = new JComboBox();
        sex1.addItem("男");
        sex1.addItem("女");
        this.add(sex1);
        sex1.setBounds(40,100,60,30);
        joinyear = new JLabel("入学时间");
        this.add(joinyear);
        joinyear.setBounds(120,100,60,30);
        joinyear1 = new JTextField();
        this.add(joinyear1);
        joinyear1.setBounds(180,100,90,30);
        stuyear = new JLabel("在校时间");
        this.add(stuyear);
        stuyear.setBounds(280,100,60,30);
        stuyear1 = new JComboBox();
        stuyear1.addItem("1");
        stuyear1.addItem("2");
        stuyear1.addItem("3");
        stuyear1.addItem("4");
        stuyear1.addItem("5");
        this.add(stuyear1);
        stuyear1.setBounds(340,100,60,30);
        major = new JLabel("专业");
        this.add(major);
        major.setBounds(0,145,30,30);
        major1 = new JComboBox();
        list1 = majorDao.FindAllMajor();
        for (int i = 0;i<list1.size();i++){
            major1.addItem(list1.get(i).getMajor_name());
        }
        this.add(major1);
        major1.setBounds(40,145,200,30);
        major1.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                classname1.removeAllItems();
                list2 = classInfoDao.findClassByMajor(String.valueOf(major1.getSelectedItem()));
                for (int i = 0;i<list2.size();i++){
                    classname1.addItem(list2.get(i).getClass_name());
                }
            }
        });
        className = new JLabel("班级");
        this.add(className);
        className.setBounds(260,145,30,30);
        classname1 = new JComboBox();
        classname1.removeAllItems();
        list2 = classInfoDao.findClassByMajor(String.valueOf(major1.getSelectedItem()));
        for (int i = 0;i<list2.size();i++){
            classname1.addItem(list2.get(i).getClass_name());
        }
        this.add(classname1);
        classname1.setBounds(300,145,80,30);
        phone = new JLabel("联系方式");
        this.add(phone);
        phone.setBounds(0,190,60,30);
        phone1 = new JTextField();
        this.add(phone1);
        phone1.setBounds(70,190,240,30);
        emile = new JLabel("邮箱");
        this.add(emile);
        emile.setBounds(0,235,30,30);
        emile1 = new JTextField(15);
        this.add(emile1);
        emile1.setBounds(70,235,160,30);
        address = new JLabel("住址");
        this.add(address);
        address.setBounds(0,280,30,30);
        address1 = new JTextField();
        this.add(address1);
        address1.setBounds(70,280,300,30);

        extra = new JLabel("其他描述");
        this.add(extra);
        extra.setBounds(0,325,80,30);
        extra1 = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(extra1);
        this.add(scrollPane);
        scrollPane.setBounds(70,325,300,200);
        this.setVisible(true);
    }
}
