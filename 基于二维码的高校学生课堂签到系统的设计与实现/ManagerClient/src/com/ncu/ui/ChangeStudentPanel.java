package com.ncu.ui;

import com.ncu.dao.ClassInfoDao;
import com.ncu.dao.MajorDao;
import com.ncu.dao.StudentDao;
import com.ncu.dao.TeacherDao;
import com.ncu.pojo.ClassInfo;
import com.ncu.pojo.Major;
import com.ncu.pojo.Student;
import com.ncu.pojo.Teacher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Vector;

public class ChangeStudentPanel extends JPanel implements ActionListener {
    JComboBox comboBox;
    JTextField info;
    JButton search;
    StudentDao studentDao;
    MajorDao majorDao;
    ClassInfoDao classInfoDao;
    InputStudentMesPanel changeinfo;
    JTextField password1;
    JLabel password;
    JTextField id;
    JButton change;
    JLabel major,className;
    JComboBox major1,classname1;
    ArrayList<Major> list1;
    ArrayList<ClassInfo> list2;
    public  ChangeStudentPanel(){
        studentDao = new StudentDao();
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        majorDao = new MajorDao();
        classInfoDao = new ClassInfoDao();
        this.setLayout(null);
        comboBox = new JComboBox();
        comboBox.addItem("学号");
        comboBox.addItem("名字");
        comboBox.addItem("班级");
        this.add(comboBox);
        comboBox.setBounds(50,20,100,30);
        comboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (comboBox.getSelectedItem().equals("班级")){
                    info.setVisible(false);
                    major.setVisible(true);
                    major1.setVisible(true);
                    className.setVisible(true);
                    classname1.setVisible(true);
                }
            }
        });
        info = new JTextField(10);
        this.add(info);
        info.setBounds(180,20,400,30);
        search = new JButton("查找");
        search.addActionListener(this::actionPerformed);
        this.add(search);
        search.setBounds(600,20,100,30);
        major = new JLabel("专业");
        this.add(major);
        major.setBounds(180,20,30,30);
        major1 = new JComboBox();
        list1 = majorDao.FindAllMajor();
        for (int i = 0;i<list1.size();i++){
            major1.addItem(list1.get(i).getMajor_name());
        }
        this.add(major1);
        major1.setBounds(230,20,200,30);
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
        className.setBounds(450,20,30,30);
        classname1 = new JComboBox();
        classname1.removeAllItems();
        list2 = classInfoDao.findClassByMajor(String.valueOf(major1.getSelectedItem()));
        for (int i = 0;i<list2.size();i++){
            classname1.addItem(list2.get(i).getClass_name());
        }
        major.setVisible(false);
        major1.setVisible(false);
        className.setVisible(false);
        classname1.setVisible(false);
        this.add(classname1);
        classname1.setBounds(500,20,80,30);
        changeinfo = new InputStudentMesPanel();
        this.add(changeinfo);
        changeinfo.setBounds(60,80,430,500);
        password = new JLabel("密码");
        this.add(password);
        password.setVisible(false);
        password.setBounds(60,590,30,30);
        password1 = new JTextField();
        this.add(password1);
        password1.setVisible(false);
        password1.setBounds(150,590,160,30);
        changeinfo.setVisible(false);
        change = new JButton("修改");
        this.add(change);
        change.setBounds(320,590,60,30);
        change.setVisible(false);
        change.addActionListener(this::actionPerformed);
        id = new JTextField();
        this.add(id);
        id.setBounds(400,590,150,30);
        id.setVisible(false);
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawLine(0,60,800,60);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() ==search){
            if (comboBox.getSelectedItem().equals("学号")){
                int temppage = 0;
                try{
                    ArrayList<Student> listtemp = this.studentDao.findStudentByAccountImp(info.getText());
                    if (listtemp.size()==0) {
                        JOptionPane.showMessageDialog(null, "没有查到该信息", "提示", JOptionPane.WARNING_MESSAGE);
                    }
                    else {
                        changeinfo.setVisible(true);
                        password.setVisible(true);
                        password1.setVisible(true);
                        change.setVisible(true);
                        id.setText(String.valueOf(listtemp.get(0).getStudent_id()));
                        changeinfo.account1.setText(String.valueOf(listtemp.get(0).getStudent_account()));
                        changeinfo.cardId1.setText(listtemp.get(0).getStudent_cardid());
                        password1.setText(String.valueOf(listtemp.get(0).getStudent_password()));
                        changeinfo.name1.setText(String.valueOf(listtemp.get(0).getStudent_name()));
                        changeinfo.sex1.setSelectedItem(listtemp.get(0).getStudent_sex());
                        changeinfo.joinyear1.setText(listtemp.get(0).getStudent_joinyear());
                        changeinfo.stuyear1.setSelectedItem(listtemp.get(0).getStudent_stuyear());
                        changeinfo.major1.setSelectedItem(listtemp.get(0).getStudent_major());
                        changeinfo.classname1.setSelectedItem(listtemp.get(0).getStudent_class());
                        changeinfo.phone1.setText(String.valueOf(listtemp.get(0).getStudent_phone()));
                        changeinfo.emile1.setText(String.valueOf(listtemp.get(0).getStudent_emile()));
                        changeinfo.address1.setText(listtemp.get(0).getStudent_adress());
                        changeinfo.extra1.setText(String.valueOf(listtemp.get(0).getStudent_extra()));
                    }
                }catch (NumberFormatException e1){
                    JOptionPane.showMessageDialog(null,"请输入数字","提示",JOptionPane.WARNING_MESSAGE);
                }
            }
            else if(comboBox.getSelectedItem().equals("名字")){
                int temppage = 0;
                ArrayList<Student> listtemp = this.studentDao.findStudentByNameImp(info.getText());
                if (listtemp.size()==0) {
                    JOptionPane.showMessageDialog(null, "没有查到该信息", "提示", JOptionPane.WARNING_MESSAGE);
                }
                else if (listtemp.size()>1){
                    JOptionPane.showMessageDialog(null,"查询出多条信息,请细化查询条件","提示",JOptionPane.WARNING_MESSAGE);
                }
                else {
                    changeinfo.setVisible(true);
                    password.setVisible(true);
                    password1.setVisible(true);
                    change.setVisible(true);
                    id.setText(String.valueOf(listtemp.get(0).getStudent_id()));
                    changeinfo.account1.setText(String.valueOf(listtemp.get(0).getStudent_account()));
                    changeinfo.cardId1.setText(listtemp.get(0).getStudent_cardid());
                    password1.setText(String.valueOf(listtemp.get(0).getStudent_password()));
                    changeinfo.name1.setText(String.valueOf(listtemp.get(0).getStudent_name()));
                    changeinfo.sex1.setSelectedItem(listtemp.get(0).getStudent_sex());
                    changeinfo.joinyear1.setText(listtemp.get(0).getStudent_joinyear());
                    changeinfo.stuyear1.setSelectedItem(listtemp.get(0).getStudent_stuyear());
                    changeinfo.major1.setSelectedItem(listtemp.get(0).getStudent_major());
                    changeinfo.classname1.setSelectedItem(listtemp.get(0).getStudent_class());
                    changeinfo.phone1.setText(String.valueOf(listtemp.get(0).getStudent_phone()));
                    changeinfo.emile1.setText(String.valueOf(listtemp.get(0).getStudent_emile()));
                    changeinfo.address1.setText(listtemp.get(0).getStudent_adress());
                    changeinfo.extra1.setText(String.valueOf(listtemp.get(0).getStudent_extra()));
                }
            }
            else if (comboBox.getSelectedItem().equals("班级")){
                int temppage = 0;
                ArrayList<Student> listtemp = this.studentDao.findStudentByClassImp(String.valueOf(major1.getSelectedItem()),String.valueOf(classname1.getSelectedItem()));
                if (listtemp.size()==0) {
                    JOptionPane.showMessageDialog(null, "没有查到该信息", "提示", JOptionPane.WARNING_MESSAGE);
                }
                else if (listtemp.size()>1){
                    JOptionPane.showMessageDialog(null,"查询出多条信息,清细化查询条件","提示",JOptionPane.WARNING_MESSAGE);
                }
                else {
                    changeinfo.setVisible(true);
                    password.setVisible(true);
                    password1.setVisible(true);
                    change.setVisible(true);
                    id.setText(String.valueOf(listtemp.get(0).getStudent_id()));
                    changeinfo.account1.setText(String.valueOf(listtemp.get(0).getStudent_account()));
                    changeinfo.cardId1.setText(listtemp.get(0).getStudent_cardid());
                    password1.setText(String.valueOf(listtemp.get(0).getStudent_password()));
                    changeinfo.name1.setText(String.valueOf(listtemp.get(0).getStudent_name()));
                    changeinfo.sex1.setSelectedItem(listtemp.get(0).getStudent_sex());
                    changeinfo.joinyear1.setText(listtemp.get(0).getStudent_joinyear());
                    changeinfo.stuyear1.setSelectedItem(listtemp.get(0).getStudent_stuyear());
                    changeinfo.major1.setSelectedItem(listtemp.get(0).getStudent_major());
                    changeinfo.classname1.setSelectedItem(listtemp.get(0).getStudent_class());
                    changeinfo.phone1.setText(String.valueOf(listtemp.get(0).getStudent_phone()));
                    changeinfo.emile1.setText(String.valueOf(listtemp.get(0).getStudent_emile()));
                    changeinfo.address1.setText(listtemp.get(0).getStudent_adress());
                    changeinfo.extra1.setText(String.valueOf(listtemp.get(0).getStudent_extra()));
                }
            }
        }
        if (e.getSource() == change){
            Student student = new Student();
            student.setStudent_id(Integer.parseInt(id.getText()));
            student.setStudent_account(changeinfo.account1.getText());
            student.setStudent_password(password1.getText());
            student.setStudent_cardid(changeinfo.cardId1.getText());
            student.setStudent_name(changeinfo.name1.getText());
            student.setStudent_sex(String.valueOf(changeinfo.sex1.getSelectedItem()));
            student.setStudent_joinyear(changeinfo.joinyear1.getText());
            student.setStudent_stuyear(Integer.parseInt(String.valueOf(changeinfo.stuyear1.getSelectedItem())));
            student.setStudent_major(String.valueOf(changeinfo.major1.getSelectedItem()));
            student.setStudent_class(String.valueOf(changeinfo.classname1.getSelectedItem()));
            student.setStudent_phone(changeinfo.phone1.getText());
            student.setStudent_emile(changeinfo.emile1.getText());
            student.setStudent_adress(changeinfo.address1.getText());
            student.setStudent_extra(changeinfo.extra1.getText());
            int n = studentDao.updateStudentInfo(student);
            if (n==1){
                JOptionPane.showMessageDialog(null,"修改成功","提示",JOptionPane.INFORMATION_MESSAGE);
                changeinfo.setVisible(false);
                password.setVisible(false);
                password1.setVisible(false);
                change.setVisible(false);
            }
            System.out.println(n);
        }
    }
}
