package com.ncu.ui;

import com.ncu.dao.CurseDao;
import com.ncu.dao.MajorDao;
import com.ncu.pojo.Major;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class CurseMainUi extends JFrame implements ActionListener{

    JComboBox comboBox;
    MajorDao majorDao;
    ArrayList<Major> list;
    JButton addCurse,bl,delCurse;
    JTextArea curseName;
    CurseDao curseDao;
    JLabel label;
    public  CurseMainUi(){
        majorDao = new MajorDao();
        curseDao = new CurseDao();
        Dimension screenSize =Toolkit.getDefaultToolkit().getScreenSize();
        this.setTitle("专业课程管理");
        this.setSize(600,350);
        this.setLocation(screenSize.width/2-600/2,screenSize.height/2-500/2);
        this.setLayout(null);
        list = majorDao.FindAllMajor();
        comboBox = new JComboBox();
        for (int i=0;i<list.size();i++){
            comboBox.addItem(list.get(i).getMajor_name());
        }
        this.add(comboBox);
        comboBox.setBounds(20,30,100,30);
        addCurse = new JButton("添加");
        this.add(addCurse);
        addCurse.setBounds(20,90,90,30);
        addCurse.addActionListener(this::actionPerformed);
        bl = new JButton("&");
        this.add(bl);
        bl.setBounds(20,150,90,30);
        bl.addActionListener(this::actionPerformed);
        delCurse = new JButton("删除");
        this.add(delCurse);
        delCurse.setBounds(20,200,90,30);
        delCurse.addActionListener(this::actionPerformed);
        curseName = new JTextArea();
        JScrollPane jScrollPane = new JScrollPane(curseName);
        this.add(jScrollPane);
        jScrollPane.setBounds(140,30,410,210);
        label = new JLabel("可批量添加课程，课程之间以&隔开即可！！");
        this.add(label);
        label.setBounds(140,260,300,30);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addCurse){
            String major = String.valueOf(comboBox.getSelectedItem());
            String str = curseName.getText().trim();
            int count = curseAddDel(major,str,"add");
            JOptionPane.showMessageDialog(null,"共有"+count+"条记录插入数据库","提示",JOptionPane.INFORMATION_MESSAGE);
            curseName.setText("");
        }
        if (e.getSource()==bl){
            curseName.append("&");
        }
        if (e.getSource()==delCurse){
            String major = String.valueOf(comboBox.getSelectedItem());
            String str = curseName.getText().trim();
            int count = curseAddDel(major,str,"del");
            JOptionPane.showMessageDialog(null,"共有"+count+"条记录删除","提示",JOptionPane.INFORMATION_MESSAGE);
            curseName.setText("");
        }
    }

    public int curseAddDel(String major,String str,String dao){
        int count=0;
        ArrayList<String> list = new ArrayList<>();
        StringTokenizer cursename = new StringTokenizer(str,"&");
        while (cursename.hasMoreTokens()){
            list.add(cursename.nextToken());
        }
        for (int i = 0;i<list.size();i++){
            System.out.println(list.get(i));
        }
        if (dao.equals("add")){
             count = curseDao.insertCurse(list,major);
        }
        if (dao.equals("del")){
            count = curseDao.deleteCurse(list,major);
        }
        return count;
    }
}
