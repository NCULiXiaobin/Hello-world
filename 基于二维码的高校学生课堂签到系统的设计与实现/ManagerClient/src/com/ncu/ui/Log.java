package com.ncu.ui;

import com.ncu.dao.ManagerDao;
import com.ncu.pojo.Manager;
import jdk.nashorn.internal.scripts.JO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Log extends JFrame implements ActionListener{
    JTextField account;
    JTextField password;
    JButton log;
    JPanel top;
    JLabel label;
    ImageIcon imageIcon;
    Box box2;
    ManagerDao managerDao;
    public Log(String title){
        managerDao = new ManagerDao();
        this.setTitle(title);
        this.setLayout(null);
        top = new JPanel();
        imageIcon = new ImageIcon("logImg.png");
        label = new JLabel(imageIcon);
        label.setBounds(0,0,512,111);
        top.add(label);
        add(top);
        top.setBounds(0,0,512,111);

        box2 = Box.createVerticalBox();
        box2.add(new JLabel("账号"));
        box2.add(Box.createVerticalStrut(40));
        box2.add(new JLabel("密码"));
        add(box2);
        box2.setBounds(110,170,30,100);

        account = new JTextField(10);
        password = new JPasswordField(10);
        add(account);
        account.setBounds(180,170,240,34);
        add(password);
        password.setBounds(180,220,240,34);

        log = new JButton("登陆");
        add(log);
        log.setBounds(220,280,60,30);
        log.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == log){
            int i = managerDao.findManagerCount(account.getText().trim());
            if (i>0){
                Manager manager = managerDao.findManager(account.getText(),password.getText());
                if (manager.getManagerName() == null){
                    JOptionPane.showMessageDialog(this,"密码错误请重试","提示",JOptionPane.WARNING_MESSAGE);
                }else{
                    new MainUi(manager);
                    dispose();
                }
            }else{
                JOptionPane.showMessageDialog(this,"抱歉，您不是管理员","提示",JOptionPane.WARNING_MESSAGE);
            }
        }
    }
}
