package com.ncu.ui;

import com.ncu.dao.ManagerDao;
import com.ncu.dao.UserDao;
import com.ncu.pojo.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by LiXiaobin on 2017/12/19.
 */
public class Login extends JFrame implements ActionListener{
    JTextField account;
    JPasswordField password;
    JButton log;
    ImageIcon imageIcon;
    JPanel top;
    JLabel label;
    JRadioButton client,manager;
    ButtonGroup buttonGroup;
    Box box1,box2;
    JLabel register;
    JLabel changePass;
    UserDao userDao;
    ManagerDao managerDao;
    public Login(){
        userDao = new UserDao();
        managerDao = new ManagerDao();
        this.setTitle("QQ");
            this.setLayout(null);
        this.setBounds(700,400,512,400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        top = new JPanel();
        imageIcon = new ImageIcon("E:\\IdeaTest\\QQ\\src\\main\\logImg.png");
        label = new JLabel(imageIcon);
        label.setBounds(0,0,512,111);
        top.add(label);
        add(top);
        top.setBounds(0,0,512,111);

        buttonGroup = new ButtonGroup();
        client = new JRadioButton("普通用户");
        manager = new JRadioButton("管理员");
        client.setSelected(true);
        buttonGroup.add(client);
        buttonGroup.add(manager);
        box1 = Box.createHorizontalBox();
        box1.add(client);
        box1.add(Box.createHorizontalStrut(10));
        box1.add(manager);
        add(box1);
        box1.setBounds(170,120,300,50);

        box2 = Box.createVerticalBox();
        box2.add(new JLabel("账号"));
        box2.add(Box.createVerticalStrut(40));
        box2.add(new JLabel("密码"));
        add(box2);
        box2.setBounds(80,190,30,100);

        account = new JTextField(10);
        password = new JPasswordField(10);
        add(account);
        account.setBounds(130,190,200,30);
        add(password);
        password.setBounds(130,240,200,30);

        register = new JLabel("注册账号?");
        register.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(Cursor.getDefaultCursor());
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                Desktop desktop = Desktop.getDesktop();
                try {
                    desktop.browse(new URI("http://localhost:8080/"));
                } catch (IOException e1) {
                    e1.printStackTrace();
                } catch (URISyntaxException e1) {
                    e1.printStackTrace();
                }
            }
        });
        add(register);
        register.setBounds(360,190,100,30);

        changePass = new JLabel("修改密码?");
        changePass.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(Cursor.getDefaultCursor());
            }
        });
        add(changePass);
        changePass.setBounds(360,240,100,30);

        log = new JButton("登陆");
        log.addActionListener(this);
        add(log);
        log.setBounds(220,300,60,30);

        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == log){
            try {
                if (account.getText().length()==0&&account.getText().trim().equals("")
                        &&String.valueOf(password.getPassword()).length()==0&&String.valueOf(password.getPassword()).trim().equals("")){
                    JOptionPane.showMessageDialog(null,"账号或密码不能为空","登陆错误",JOptionPane.WARNING_MESSAGE);
                }
                else if (client.isSelected()){
                    int i = userDao.FindCounts(Integer.parseInt(account.getText()),String.valueOf(password.getPassword()));
                    if (i==0){
                        JOptionPane.showMessageDialog(null,"账号或密码错误","登录错误",JOptionPane.WARNING_MESSAGE);
                    }else {
                        User user = userDao.FindUserMes(Integer.parseInt(account.getText()),String.valueOf(password.getPassword()));
                        new ClientMainUI(user);
                        userDao.UpdateLog(user.getUserId(),"在线");
                        dispose();
                    }
                }
                else if (manager.isSelected()){
                    int i = managerDao.FindMaCounts(Integer.parseInt(account.getText()),String.valueOf(password.getPassword()));
                    if (i==0){
                        JOptionPane.showMessageDialog(null,"没有该管理员","登录错误",JOptionPane.WARNING_MESSAGE);
                    }
                    else {
                        dispose();
                    }
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
}
