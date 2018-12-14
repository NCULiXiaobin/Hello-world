package com.ncu.ui;

import com.ncu.dao.TeacherDao;
import com.ncu.pojo.Teacher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

public class ChangeTeacherPanel extends JPanel implements ActionListener {
    JComboBox comboBox;
    JTextField info;
    JButton search;
    TeacherDao teacherDao;
    InputMesPanel changeinfo;
    JTextField password1;
    JLabel password;
    JTextField id;
    JButton change;
    public  ChangeTeacherPanel(){
        teacherDao = new TeacherDao();
        this.setLayout(null);
        comboBox = new JComboBox();
        comboBox.addItem("工号");
        comboBox.addItem("名字");
        comboBox.addItem("专业");
        this.add(comboBox);
        comboBox.setBounds(50,20,100,30);
        info = new JTextField(10);
        this.add(info);
        info.setBounds(180,20,400,30);
        search = new JButton("查找");
        search.addActionListener(this::actionPerformed);
        this.add(search);
        search.setBounds(600,20,100,30);
        changeinfo = new InputMesPanel();
        this.add(changeinfo);
        changeinfo.setBounds(60,80,430,500);
        password = new JLabel("密码");
        this.add(password);
        password.setVisible(false);
        password.setBounds(60,580,30,30);
        password1 = new JTextField();
        this.add(password1);
        password1.setVisible(false);
        password1.setBounds(150,580,160,30);
        changeinfo.setVisible(false);
        change = new JButton("修改");
        this.add(change);
        change.setBounds(320,580,60,30);
        change.setVisible(false);
        change.addActionListener(this::actionPerformed);
        id = new JTextField();
        this.add(id);
        id.setBounds(400,580,150,30);
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
            if (comboBox.getSelectedItem().equals("工号")){
                int temppage = 0;
                try{
                    ArrayList<Teacher> listtemp = this.teacherDao.findTeacherByAccountImp(Integer.parseInt(info.getText()));
                    if (listtemp.size()==0) {
                        JOptionPane.showMessageDialog(null, "没有查到该信息", "提示", JOptionPane.WARNING_MESSAGE);
                    }
                    else {
                        changeinfo.setVisible(true);
                        password.setVisible(true);
                        password1.setVisible(true);
                        change.setVisible(true);
                        id.setText(String.valueOf(listtemp.get(0).getTeacher_id()));
                        changeinfo.account1.setText(String.valueOf(listtemp.get(0).getTeacher_account()));
                        password1.setText(String.valueOf(listtemp.get(0).getTeacher_password()));
                        changeinfo.name1.setText(String.valueOf(listtemp.get(0).getTeacher_name()));
                        if (changeinfo.radio1.getText().equals(String.valueOf(listtemp.get(0).getTeacher_sex()))){
                            changeinfo.radio1.setSelected(true);
                        }else {
                            changeinfo.radio2.setSelected(true);
                        }
                        changeinfo.age1.setText(String.valueOf(listtemp.get(0).getTeacher_age()));
                        changeinfo.major1.setSelectedItem(String.valueOf(listtemp.get(0).getTeacher_job()));
                        changeinfo.phone1.setText(String.valueOf(listtemp.get(0).getTeacher_phone()));
                        changeinfo.phoneex1.setText(String.valueOf(listtemp.get(0).getTeacher_phoneex()));
                        changeinfo.emile1.setText(String.valueOf(listtemp.get(0).getTeacher_emile()));
                        changeinfo.extra1.setText(String.valueOf(listtemp.get(0).getTeacher_extra()));
                    }
                }catch (NumberFormatException e1){
                    JOptionPane.showMessageDialog(null,"请输入数字","提示",JOptionPane.WARNING_MESSAGE);
                }
            }
            else if(comboBox.getSelectedItem().equals("名字")){
                int temppage = 0;
                ArrayList<Teacher> listtemp = this.teacherDao.findTeacherByNameImp(info.getText());
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
                    id.setText(String.valueOf(listtemp.get(0).getTeacher_id()));
                    changeinfo.account1.setText(String.valueOf(listtemp.get(0).getTeacher_account()));
                    password1.setText(String.valueOf(listtemp.get(0).getTeacher_password()));
                    changeinfo.name1.setText(String.valueOf(listtemp.get(0).getTeacher_name()));
                    if (changeinfo.radio1.getText().equals(String.valueOf(listtemp.get(0).getTeacher_sex()))) {
                        changeinfo.radio1.setSelected(true);
                    } else {
                        changeinfo.radio2.setSelected(true);
                    }
                    changeinfo.age1.setText(String.valueOf(listtemp.get(0).getTeacher_age()));
                    changeinfo.major1.setSelectedItem(String.valueOf(listtemp.get(0).getTeacher_job()));
                    changeinfo.phone1.setText(String.valueOf(listtemp.get(0).getTeacher_phone()));
                    changeinfo.phoneex1.setText(String.valueOf(listtemp.get(0).getTeacher_phoneex()));
                    changeinfo.emile1.setText(String.valueOf(listtemp.get(0).getTeacher_emile()));
                    changeinfo.extra1.setText(String.valueOf(listtemp.get(0).getTeacher_extra()));
                }
            }
            else if (comboBox.getSelectedItem().equals("专业")){
                int temppage = 0;
                ArrayList<Teacher> listtemp = this.teacherDao.findTeacherByJobImp(info.getText());
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
                    id.setText(String.valueOf(listtemp.get(0).getTeacher_id()));
                    changeinfo.account1.setText(String.valueOf(listtemp.get(0).getTeacher_account()));
                    password1.setText(String.valueOf(listtemp.get(0).getTeacher_password()));
                    changeinfo.name1.setText(String.valueOf(listtemp.get(0).getTeacher_name()));
                    if (changeinfo.radio1.getText().equals(String.valueOf(listtemp.get(0).getTeacher_sex()))) {
                        changeinfo.radio1.setSelected(true);
                    } else {
                        changeinfo.radio2.setSelected(true);
                    }
                    changeinfo.age1.setText(String.valueOf(listtemp.get(0).getTeacher_age()));
                    changeinfo.major1.setSelectedItem(String.valueOf(listtemp.get(0).getTeacher_job()));
                    changeinfo.phone1.setText(String.valueOf(listtemp.get(0).getTeacher_phone()));
                    changeinfo.phoneex1.setText(String.valueOf(listtemp.get(0).getTeacher_phoneex()));
                    changeinfo.emile1.setText(String.valueOf(listtemp.get(0).getTeacher_emile()));
                    changeinfo.extra1.setText(String.valueOf(listtemp.get(0).getTeacher_extra()));
                }
            }
        }
        if (e.getSource() == change){
            Teacher teacher = new Teacher();
            teacher.setTeacher_id(Integer.parseInt(id.getText()));
            teacher.setTeacher_account(Integer.parseInt(changeinfo.account1.getText()));
            teacher.setTeacher_password(password1.getText());
            teacher.setTeacher_name(changeinfo.name1.getText());
            teacher.setTeacher_age(Integer.parseInt(changeinfo.age1.getText()));
            teacher.setTeacher_sex("男");
            teacher.setTeacher_job(String.valueOf(changeinfo.major1.getSelectedItem()));
            teacher.setTeacher_phone(changeinfo.phone1.getText());
            teacher.setTeacher_phoneex(changeinfo.phoneex1.getText());
            teacher.setTeacher_emile(changeinfo.emile1.getText());
            teacher.setTeacher_extra(changeinfo.extra1.getText());
            int n = teacherDao.updateTeacherInfo(teacher);
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
