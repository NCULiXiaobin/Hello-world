package com.ncu.ui;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.ncu.dao.StudentDao;
import com.ncu.dao.TeacherDao;
import com.ncu.pojo.Student;
import com.ncu.pojo.Teacher;
import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddStudentPanel extends JPanel implements ActionListener {

    InputStudentMesPanel addinfo;
    JTextArea showTips;
    JButton save;
    StudentDao studentDao;
    public AddStudentPanel(){
        studentDao = new StudentDao();
        this.setLayout(null);
        Border border= BorderFactory.createLineBorder(Color.BLACK);
        addinfo = new InputStudentMesPanel();
        this.add(addinfo);
//        Border lineBorder = BorderFactory.createLineBorder(Color.BLACK);
//        addinfo.setBorder(lineBorder);
        addinfo.setBounds(30,50,500,530);
        showTips = new JTextArea("注意输入格式！！！！！默认密码：123123");
        Font font = new Font("宋体",Font.BOLD,25);
        showTips.setFont(font);
        showTips.setLineWrap(true);
        showTips.setEnabled(false);
        JScrollPane scrollPane = new JScrollPane(showTips);
        this.add(scrollPane);
        scrollPane.setBounds(540,50,200,260);
        save = new JButton("保存");
        this.add(save);
        save.setBounds(550,455,100,75);
        save.setFont(new Font("宋体",Font.BOLD,20));
        save.addActionListener(this::actionPerformed);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() ==save) {
            Student student = new Student();
            if (addinfo.name1.getText().equals("")){
                JOptionPane.showMessageDialog(null,"姓名未填写","提示",JOptionPane.WARNING_MESSAGE);
            }else {
                try{
                    student.setStudent_account(addinfo.account1.getText());
                    student.setStudent_name(addinfo.name1.getText());
                    student.setStudent_cardid(addinfo.cardId1.getText());
                    student.setStudent_sex(String.valueOf(addinfo.sex1.getSelectedItem()));
                    student.setStudent_joinyear(addinfo.joinyear1.getText());
                    student.setStudent_stuyear(Integer.parseInt(String.valueOf(addinfo.stuyear1.getSelectedItem())));
                    student.setStudent_major(String.valueOf(addinfo.major1.getSelectedItem()));
                    student.setStudent_class(String.valueOf(addinfo.classname1.getSelectedItem()));
                    student.setStudent_phone(addinfo.phone1.getText());
                    student.setStudent_emile(addinfo.emile1.getText());
                    student.setStudent_adress(addinfo.address1.getText());
                    student.setStudent_extra(addinfo.extra.getText());
                    int i = studentDao.insertStudentInfo(student);
                    if (i==1){
                        JOptionPane.showMessageDialog(null,"插入成功","提示",JOptionPane.INFORMATION_MESSAGE);
                        addinfo.account1.setText(null);
                        addinfo.name1.setText(null);
                        addinfo.cardId1.setText(null);
                        addinfo.sex1.setSelectedIndex(0);
                        addinfo.joinyear1.setText(null);
                        addinfo.stuyear1.setSelectedIndex(0);
                        addinfo.major1.setSelectedIndex(0);
                        addinfo.classname1.setSelectedIndex(0);
                        addinfo.phone1.setText(null);
                        addinfo.emile1.setText(null);
                        addinfo.address1.setText(null);
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
