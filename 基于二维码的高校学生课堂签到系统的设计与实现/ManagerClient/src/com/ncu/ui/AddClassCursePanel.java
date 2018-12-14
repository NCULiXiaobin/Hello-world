package com.ncu.ui;

import com.ncu.dao.*;
import com.ncu.pojo.*;
import jdk.nashorn.internal.scripts.JO;
import sun.nio.cs.KOI8_R;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Vector;

public class AddClassCursePanel extends JPanel implements ActionListener{
    JComboBox comboBox;
    MajorDao majorDao;
    ClassInfoDao classInfoDao;
    CurseDao curseDao;
    TeacherDao teacherDao;
    ClassCurseDao classCurseDao;
    JComboBox classcomboBox;
    JComboBox cursecomboBox;
    JTable cursetable;
    TableModel cursetableModel;
    ArrayList<ClassCurse> classCurses;
    JComboBox teachercomboBox;
    int teacherAccount;
    JButton saveClassCurse;
    String curse[][] = new String[10][7];
    String day[] = {"周一","周二","周三","周四","周五","周六","周日"};
    public AddClassCursePanel(){
        classInfoDao = new ClassInfoDao();
        classCurseDao = new ClassCurseDao();
        majorDao = new MajorDao();
        curseDao = new CurseDao();
        teacherDao = new TeacherDao();
        classCurses = new ArrayList<>();
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
        cursecomboBox = new JComboBox();
        this.add(cursecomboBox);
        cursecomboBox.addItem("选择课程");
        cursecomboBox.setBounds(310,20,180,30);
        teachercomboBox = new JComboBox();
        teachercomboBox.addItem("选择教师");
        this.add(teachercomboBox);
        teachercomboBox.setBounds(510,20,100,30);
        saveClassCurse = new JButton("提交");
        this.add(saveClassCurse);
        saveClassCurse.setBounds(630,20,80,30);
        saveClassCurse.addActionListener(this::actionPerformed);
        comboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                ArrayList<ClassInfo> classlist = classInfoDao.findClassByMajor(String.valueOf(e.getItem()));
                classcomboBox.removeAllItems();
                classcomboBox.addItem("选择班级");
                ArrayList<Curse> curselist = curseDao.selectCurse(String.valueOf(e.getItem()));
                cursecomboBox.removeAllItems();
                cursecomboBox.addItem("选择课程");
                ArrayList<Teacher> teacherlist = teacherDao.findTeacherByJobImp(String.valueOf(e.getItem()));
                teachercomboBox.removeAllItems();
                teachercomboBox.addItem("选择教师");
                for (int i = 0;i<classlist.size();i++){
                    classcomboBox.addItem(classlist.get(i).getClass_name());
                }
                for (int i = 0;i<curselist.size();i++){
                    cursecomboBox.addItem(curselist.get(i).getCurse_name());
                }
                for (int i = 0;i<teacherlist.size();i++){
                    teachercomboBox.addItem(teacherlist.get(i).getTeacher_name());
                }
            }
        });
        teachercomboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                ArrayList<Teacher> temp_teacher_list = teacherDao.findTeacherByNameImp(String.valueOf(e.getItem()));
                int cc = temp_teacher_list.size();
                if (cc==1){
                    teacherAccount = temp_teacher_list.get(0).getTeacher_account();
                }
                if (cc>1){
                    String temp_teacherAccount = JOptionPane.showInputDialog(null,"有两个相同名字的教师，请输入教师工号","输入工号",JOptionPane.PLAIN_MESSAGE);
                    if (temp_teacherAccount!=null){
                        if (teacherDao.findTeacherCounts(Integer.parseInt(temp_teacherAccount))==0){
                            JOptionPane.showMessageDialog(null,"工号输入有误","警告",JOptionPane.WARNING_MESSAGE);
                            cc = 0;
                        } else {
                            teacherAccount = Integer.parseInt(temp_teacherAccount);
                        }
                    }
                }
            }
        });

        cursetableModel = new DefaultTableModel(curse,day);
        cursetable = new JTable(cursetableModel);
        cursetable.setRowHeight(50);
        cursetable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton()==MouseEvent.BUTTON3){
                    String insertcurse = String.valueOf(cursecomboBox.getSelectedItem());
                    int focusedRowIndex = cursetable.rowAtPoint(e.getPoint());
                    int focusedColIndex = cursetable.columnAtPoint(e.getPoint());
                    //将表格所选项设为当前右键点击的行signdb
                    //弹出菜单
                    JPopupMenu popupMenu = new JPopupMenu();
                    JMenuItem insItem = new JMenuItem("插入");
                    insItem.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if (comboBox.getSelectedIndex()!=0&&classcomboBox.getSelectedIndex()!=0&&cursecomboBox.getSelectedIndex()!=0){
                                ClassCurse classCurse = new ClassCurse();
                                classCurse.setClass_curse_major(String.valueOf(comboBox.getSelectedItem()));
                                classCurse.setClass_curse_classname(String.valueOf(classcomboBox.getSelectedItem()));
                                classCurse.setClass_curse_name(insertcurse);
                                classCurse.setClass_curse_day(String.valueOf(focusedColIndex+1));
                                classCurse.setClass_curse_index(String.valueOf(focusedRowIndex+1));
                                classCurse.setClass_curse_teacher(String.valueOf(teacherAccount));
                                classCurse.setClass_curse_teacher_name(String.valueOf(teachercomboBox.getSelectedItem()));
                                classCurses.add(classCurse);
                                cursetableModel.setValueAt(insertcurse,focusedRowIndex,focusedColIndex);
                                cursetable.updateUI();
                            }else {
                                JOptionPane.showMessageDialog(null,"专业、班级、科目为必填信息！！","警告",JOptionPane.WARNING_MESSAGE);
                            }
                        }
                    });
                    popupMenu.add(insItem);
                    popupMenu.show(cursetable, e.getX(), e.getY());
                }
            }
        });
        JScrollPane scrollPane = new JScrollPane(cursetable);
        this.add(scrollPane);
        scrollPane.setBounds(20,85,750,525);
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==saveClassCurse){
            System.out.println(classCurses.size());
            if (classCurses.size()!=0){
                int counts = classCurseDao.findClassCurseCount(classCurses.get(0).getClass_curse_major(),classCurses.get(0).getClass_curse_classname());
                if (counts>0){
                    for (int i = 0;i<7;i++){
                        for (int j = 0;j<10;j++){
                            cursetable.setValueAt("",j,i);
                        }
                    }
                    classCurses.clear();
                    cursetable.updateUI();
                    comboBox.setSelectedIndex(0);
                    JOptionPane.showMessageDialog(null,"该课程表已经存在","提示", JOptionPane.WARNING_MESSAGE);
                }else {
                    int count = classCurseDao.insertClassCurse(classCurses);
                    if (count!=0){
                        JOptionPane.showMessageDialog(null,"添加成功","提示", JOptionPane.INFORMATION_MESSAGE);
                        for (int i = 0;i<7;i++){
                            for (int j = 0;j<10;j++){
                                cursetable.setValueAt("",j,i);
                            }
                        }
                        classCurses.clear();
                        cursetable.updateUI();
                        comboBox.setSelectedIndex(0);
                    }
                }
            }else {
                JOptionPane.showMessageDialog(null,"添加失败","提示", JOptionPane.WARNING_MESSAGE);
                classCurses.clear();
                cursetable.updateUI();
                comboBox.setSelectedIndex(0);
            }
        }
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawLine(0,60,800,60);
    }

}
