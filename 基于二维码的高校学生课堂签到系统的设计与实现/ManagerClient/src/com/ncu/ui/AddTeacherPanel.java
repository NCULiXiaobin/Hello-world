package com.ncu.ui;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.ncu.dao.TeacherDao;
import com.ncu.pojo.Teacher;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddTeacherPanel extends JPanel implements ActionListener {

    InputMesPanel addinfo;
    JTextArea showTips;
    JButton save;
    TeacherDao teacherDao;
    public AddTeacherPanel(){
        teacherDao = new TeacherDao();
        this.setLayout(null);
        Border border= BorderFactory.createLineBorder(Color.BLACK);
        addinfo = new InputMesPanel();
        this.add(addinfo);
        addinfo.radio1.addActionListener(this::actionPerformed);
        addinfo.radio2.addActionListener(this::actionPerformed);
        addinfo.setBounds(50,60,430,530);
        showTips = new JTextArea("请注意每一项输入的格式！！！！密码默认为：123456");
        Font font = new Font("宋体",Font.BOLD,25);
        showTips.setFont(font);
        showTips.setLineWrap(true);
        showTips.setEnabled(false);
        JScrollPane scrollPane = new JScrollPane(showTips);
        this.add(scrollPane);
        scrollPane.setBounds(540,60,200,260);
        save = new JButton("保存");
        this.add(save);
        save.setBounds(550,455,100,75);
        save.setFont(new Font("宋体",Font.BOLD,20));
        save.addActionListener(this::actionPerformed);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == save){
            Teacher teacher = new Teacher();
            if (addinfo.name1.getText().equals("")){
                JOptionPane.showMessageDialog(null,"姓名未填写","提示",JOptionPane.WARNING_MESSAGE);
            }else {
                try{
                    teacher.setTeacher_account(Integer.parseInt(addinfo.account1.getText()));
                    teacher.setTeacher_name(addinfo.name1.getText());
                    if (addinfo.radio2.isSelected()){
                        teacher.setTeacher_sex("女");
                    }
                    else{
                        teacher.setTeacher_sex("男");
                    }
                    teacher.setTeacher_age(Integer.parseInt(addinfo.age1.getText()));
                    teacher.setTeacher_job(String.valueOf(addinfo.major1.getSelectedItem()));
                    teacher.setTeacher_phone(addinfo.phone1.getText());
                    teacher.setTeacher_phoneex(addinfo.phoneex1.getText());
                    teacher.setTeacher_emile(addinfo.emile1.getText());
                    teacher.setTeacher_extra(addinfo.extra1.getText());
                    int i = teacherDao.insertTeacherInfo(teacher);
                    if (i==1){
                        JOptionPane.showMessageDialog(null,"插入成功","提示",JOptionPane.INFORMATION_MESSAGE);
                        addinfo.account1.setText(null);
                        addinfo.name1.setText(null);
                        addinfo.radio1.setSelected(false);
                        addinfo.radio2.setSelected(false);
                        addinfo.age1.setText(null);
                        addinfo.major1.setSelectedIndex(0);
                        addinfo.phone1.setText(null);
                        addinfo.phoneex1.setText(null);
                        addinfo.emile1.setText(null);
                        addinfo.extra1.setText(null);
                    }else {
                        JOptionPane.showMessageDialog(null,"插入失败，工号重复！！","警告",JOptionPane.WARNING_MESSAGE);
                    }
                }catch (NumberFormatException e1){
                    JOptionPane.showMessageDialog(null,"输入格式有有误","提示",JOptionPane.WARNING_MESSAGE);
                }
            }
        }
    }
}
