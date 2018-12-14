package com.ncu.ui;

import com.ncu.dao.*;
import com.ncu.pojo.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class SearchClassCursePanel extends JPanel implements ActionListener{
    JComboBox comboBox;
    MajorDao majorDao;
    ClassInfoDao classInfoDao;
    TeacherDao teacherDao;
    ClassCurseDao classCurseDao;
    JComboBox classcomboBox;
    JTable cursetable;
    TableModel cursetableModel;
    JButton searchClassCurse;
    String curse[][] = new String[10][7];
    String day[] = {"周一","周二","周三","周四","周五","周六","周日"};
    public SearchClassCursePanel(){
        classInfoDao = new ClassInfoDao();
        classCurseDao = new ClassCurseDao();
        teacherDao = new TeacherDao();
        majorDao = new MajorDao();
        this.setLayout(null);
        comboBox = new JComboBox();
        comboBox.addItem("选择专业");
        ArrayList<Major> majorlist = majorDao.FindAllMajor();
        for (int i = 0;i<majorlist.size();i++){
            comboBox.addItem(majorlist.get(i).getMajor_name());
        }
        this.add(comboBox);
        comboBox.setBounds(20,20,150,30);
        classcomboBox = new JComboBox();
        classcomboBox.addItem("选择班级");
        this.add(classcomboBox);
        classcomboBox.setBounds(190,20,100,30);
        searchClassCurse = new JButton("查询");
        this.add(searchClassCurse);
        searchClassCurse.setBounds(630,20,80,30);
        searchClassCurse.addActionListener(this::actionPerformed);
        comboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                ArrayList<ClassInfo> classlist = classInfoDao.findClassByMajor(String.valueOf(e.getItem()));
                classcomboBox.removeAllItems();
                classcomboBox.addItem("选择班级");
                for (int i = 0;i<classlist.size();i++){
                    classcomboBox.addItem(classlist.get(i).getClass_name());
                }
            }
        });
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==searchClassCurse) {
            ArrayList<ClassCurse> classCurses = classCurseDao.findClassCurse(String.valueOf(comboBox.getSelectedItem()),String.valueOf(classcomboBox.getSelectedItem()));
            for (int i=0;i<10;i++){
                for(int j = 0;j<7;j++){
                    for (int n = 0;n<classCurses.size();n++){
                        curse[Integer.parseInt(classCurses.get(n).getClass_curse_index())-1]
                                [Integer.parseInt(classCurses.get(n).getClass_curse_day())-1] = classCurses.get(n).getClass_curse_name()
                                +"--"+classCurses.get(n).getClass_curse_teacher()+"--"+classCurses.get(n).getClass_curse_teacher_name();
                    }
                }
            }
            cursetableModel = new DefaultTableModel(curse,day);
            cursetable = new JTable(cursetableModel);
            cursetable.setRowHeight(50);
            JScrollPane scrollPane = new JScrollPane(cursetable);
            this.add(scrollPane);
            scrollPane.setBounds(20,85,750,525);
        }
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawLine(0,60,800,60);
    }
}
