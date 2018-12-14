package com.ncu.ui;

import com.ncu.dao.ClassInfoDao;
import com.ncu.dao.MajorDao;
import com.ncu.pojo.ClassInfo;
import com.ncu.pojo.Major;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ClassInfoUi extends JFrame implements ActionListener{
    JLabel label,label1,label2;
    JComboBox comboBox;
    JTextField textField,textField1,textField2;
    JScrollPane scrollPane;
    JButton search,add1,delete,sureadd1,suredelete;
    JTextArea area;
    MajorDao majorDao;
    ArrayList<Major> list;
    ClassInfoDao classInfoDao;
    public ClassInfoUi(){
        majorDao = new MajorDao();
        classInfoDao = new ClassInfoDao();
        list = new ArrayList<>();
        Dimension screenSize =Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(600,500);
        this.setLocation(screenSize.width/2-600/2,screenSize.height/2-500/2);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(false);
        label = new JLabel("选择专业");
        this.add(label);
        label.setBounds(80,20,100,30);
        list = majorDao.FindAllMajor();
        comboBox = new JComboBox();
        for (int i = 0;i<list.size();i++){
            comboBox.addItem(list.get(i).getMajor_name());
        }
        this.add(comboBox);
        comboBox.setBounds(190,20,200,30);
        search = new JButton("查询");
        search.addActionListener(this::actionPerformed);
        this.add(search);
        search.setBounds(430,20,80,30);
        area = new JTextArea();
        scrollPane = new JScrollPane(area);
        this.add(scrollPane);
        scrollPane.setBounds(40,70,500,200);
        label1 = new JLabel("添加班级");
        this.add(label1);
        label1.setBounds(40,320,80,30);
        textField1 = new JTextField();
        textField1.setEnabled(false);
        this.add(textField1);
        textField1.setBounds(140,320,230,30);
        add1 = new JButton("添加");
        this.add(add1);
        add1.setBounds(390,320,80,30);
        add1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField1.setEnabled(true);
                sureadd1.setEnabled(true);
            }
        });
        sureadd1 = new JButton("确定");
        sureadd1.addActionListener(this::actionPerformed);
        this.add(sureadd1);
        sureadd1.setBounds(490,320,80,30);
        sureadd1.setEnabled(false);
        label2 = new JLabel("删除班级");
        this.add(label2);
        label2.setBounds(40,360,80,30);
        textField2 = new JTextField();
        this.add(textField2);
        textField2.setBounds(140,360,230,30);
        textField2.setEnabled(false);
        delete = new JButton("删除");
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textField2.setEnabled(true);
                suredelete.setEnabled(true);
            }
        });
        this.add(delete);
        delete.setBounds(390,360,80,30);
        suredelete = new JButton("确定");
        suredelete.addActionListener(this::actionPerformed);
        suredelete.setEnabled(false);
        this.add(suredelete);
        suredelete.setBounds(490,360,80,30);
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == search){
            area.setText(null);
            ArrayList<ClassInfo> list = classInfoDao.findClassByMajor(String.valueOf(comboBox.getSelectedItem()));
            if (list.size()>0){
                for (int i = 0;i<list.size();i++){
                    int count = classInfoDao.findClassPeopleCount(list.get(i).getMajor_name(),list.get(i).getClass_name());
                    area.append(list.get(i).getMajor_name()+"     "+list.get(i).getClass_name()+"班" +"       "+"人数：" +count+"\n");
                }
            }
        }
        if (e.getSource() == sureadd1){
            ClassInfo classInfo = new ClassInfo();
            classInfo.setMajor_name(String.valueOf(comboBox.getSelectedItem()));
            classInfo.setClass_name(textField1.getText());
            int i = classInfoDao.insertClassInfo(classInfo);
            if (i==1){
                JOptionPane.showMessageDialog(null,"插入成功","提示",JOptionPane.INFORMATION_MESSAGE);
                textField1.setText(null);
                textField1.setEnabled(false);
                sureadd1.setEnabled(false);
            }
        }
        if (e.getSource() == suredelete){
            int i = classInfoDao.deleteClassInfo(textField2.getText());
            if (i==1){
                JOptionPane.showMessageDialog(null,"删除成功","提示",JOptionPane.INFORMATION_MESSAGE);
                textField2.setText(null);
                textField2.setEnabled(false);
                suredelete.setEnabled(false);
            }
        }
    }
}
