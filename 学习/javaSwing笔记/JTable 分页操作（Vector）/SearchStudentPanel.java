package com.ncu.ui;

import com.ncu.dao.ClassInfoDao;
import com.ncu.dao.MajorDao;
import com.ncu.dao.StudentDao;
import com.ncu.dao.TeacherDao;
import com.ncu.pojo.ClassInfo;
import com.ncu.pojo.Major;
import com.ncu.pojo.Student;
import com.ncu.pojo.Teacher;
import com.ncu.service.PageShow;
import com.ncu.service.StudentPageShow;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Vector;

public class SearchStudentPanel extends JPanel implements ActionListener {

    JComboBox comboBox;
    JTextField info;
    JButton search;
    JLabel label;
    JLabel previous,next,first,end;
    JLabel nowpage;
    DefaultTableModel tableModel;
    JTable table;
    JPopupMenu popupMenu;
    JMenuItem delItem;
    JLabel major,className;
    JComboBox major1,classname1;
    int size;
    private static int nowpage1;
    int page1;
    private static int i = 0;
    StudentDao studentDao;
    MajorDao majorDao;
    ClassInfoDao classInfoDao;
    StudentPageShow studentPageShow;
    ArrayList<Student> list;
    Vector<Vector> a =null;
    Vector<String> name;
    ArrayList<Major> list1;
    ArrayList<ClassInfo> list2;
    public SearchStudentPanel(){
        nowpage1 =1;
        studentDao = new StudentDao();
        majorDao = new MajorDao();
        classInfoDao = new ClassInfoDao();
        studentPageShow = new StudentPageShow();
        list = new ArrayList<>();
        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
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
                if (comboBox.getSelectedItem().equals("学号")){
                    info.setVisible(true);
                    major.setVisible(false);
                    major1.setVisible(false);
                    className.setVisible(false);
                    classname1.setVisible(false);
                }
                if (comboBox.getSelectedItem().equals("名字")){
                    info.setVisible(true);
                    major.setVisible(false);
                    major1.setVisible(false);
                    className.setVisible(false);
                    classname1.setVisible(false);
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
        list = studentDao.findAllStudent();
        label = new JLabel("共有"+list.size()+"条记录");
        size = list.size();
        if (size%19!=0){
            page1 = size/19+1;
        }else {
            page1 = size/19;
        }
        this.add(label);
        label.setBounds(20,70,100,20);
        a = studentPageShow.selectStudentPage(1,list);
        name = new Vector<>();
        name.add("学号");    //= {"工号","密码","姓名","性别","年龄","职称","联系电话","紧急联系电话","邮箱","附加信息"};
        name.add("密码");
        name.add("身份证号");
        name.add("姓名");
        name.add("性别");
        name.add("入学时间");
        name.add("在校时间（年）");
        name.add("所在专业");
        name.add("所在班级");
        name.add("联系方式");
        name.add("邮箱");
        name.add("家庭住址");
        name.add("其他描述");
        tableModel = new DefaultTableModel(a,name);
        table = new JTable(tableModel);
        table.setRowHeight(25);
        table.setSelectionBackground(Color.GRAY);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton()==MouseEvent.BUTTON3){
                    int focusedRowIndex = table.rowAtPoint(e.getPoint());
                    if (focusedRowIndex == -1) {
                        return;
                    }
                    //将表格所选项设为当前右键点击的行
                    table.setRowSelectionInterval(focusedRowIndex, focusedRowIndex);
                    //弹出菜单
                    popupMenu = new JPopupMenu();
                    delItem = new JMenuItem("删除");
                    delItem.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            int i = studentDao.deleteStudentInfo(Integer.parseInt(String.valueOf(tableModel.getValueAt(focusedRowIndex,0))));
                            if (i==1){
                                JOptionPane.showMessageDialog(null,"删除成功","提示",JOptionPane.INFORMATION_MESSAGE);
                                tableModel.removeRow(focusedRowIndex);
                                table.updateUI();
                            }
                        }
                    });
                    popupMenu.add(delItem);
                    popupMenu.show(table, e.getX(), e.getY());
                }
            }
        });
        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane);
        scrollPane.setBounds(20,100,750,500);
        first = new JLabel("首页");
        this.add(first);
        first.setBounds(200,605,30,30);
        first.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                nowpage1 = 1;
                a = studentPageShow.selectStudentPage(nowpage1,list);
                tableModel.setRowCount(0);
                tableModel.setDataVector(a,name);
                table.updateUI();
                nowpage.setText(nowpage1+"/"+page1);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(Cursor.getDefaultCursor());
            }
        });
        previous = new JLabel("上一页");
        this.add(previous);
        previous.setBounds(250,605,50,30);
        previous.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                nowpage1--;
                if (nowpage1<1){
                    JOptionPane.showMessageDialog(null,"已经是第一页了","提示",JOptionPane.WARNING_MESSAGE);
                    nowpage1++;
                    previous.setEnabled(false);
                    first.setEnabled(false);
                    nowpage.setText(nowpage1+"/"+page1);
                }else {
                    a = studentPageShow.selectStudentPage(nowpage1,list);
                    tableModel.setRowCount(0);
                    tableModel.setDataVector(a,name);
                    table.updateUI();
                    nowpage.setText(nowpage1+"/"+page1);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(Cursor.getDefaultCursor());
            }
        });
        nowpage = new JLabel(nowpage1+"/"+page1);
        this.add(nowpage);
        nowpage.setBounds(360,605,100,30);
        next = new JLabel("下一页");
        this.add(next);
        next.setBounds(450,605,50,30);
        next.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                nowpage1++;
                if (nowpage1>page1){
                    JOptionPane.showMessageDialog(null,"已经没有下一页了","提示",JOptionPane.WARNING_MESSAGE);
                    nowpage1--;
                    next.setEnabled(false);
                    end.setEnabled(false);
                    nowpage.setText(nowpage1+"/"+page1);
                }else {
                    a = studentPageShow.selectStudentPage(nowpage1,list);
                    tableModel.setRowCount(0);
                    tableModel.setDataVector(a,name);
                    nowpage.setText(nowpage1+"/"+page1);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(Cursor.getDefaultCursor());
            }
        });
        end = new JLabel("尾页");
        this.add(end);
        end.setBounds(500,605,30,30);
        end.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                nowpage1 = page1;
                a =studentPageShow.selectStudentPage(nowpage1,list);
                tableModel.setRowCount(0);
                tableModel.setDataVector(a,name);
                table.updateUI();
                nowpage.setText(nowpage1+"/"+page1);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(Cursor.getDefaultCursor());
            }
        });
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
                try{
                    Vector<Vector> listtemp = this.studentDao.findStudentByAccount(info.getText());
                    if (listtemp.size()==0){
                        JOptionPane.showMessageDialog(null,"没有查到该信息","提示", JOptionPane.WARNING_MESSAGE);
                        tableModel.setRowCount(0);
                        table.updateUI();
                        label.setText("共有"+listtemp.size()+"条记录");
                        first.setVisible(false);
                        previous.setVisible(false);
                        next.setVisible(false);
                        end.setVisible(false);
                        nowpage.setVisible(false);
                    }else {
                        tableModel.setRowCount(0);
                        tableModel.setDataVector(listtemp,name);
                        table.updateUI();
                        label.setText("共有"+listtemp.size()+"条记录");
                        first.setVisible(false);
                        previous.setVisible(false);
                        next.setVisible(false);
                        end.setVisible(false);
                        nowpage.setVisible(false);
                    }
                }catch (NumberFormatException e1){
                    JOptionPane.showMessageDialog(null,"请输入数字","提示",JOptionPane.WARNING_MESSAGE);
                }
            }
            else if(comboBox.getSelectedItem().equals("名字")){
                Vector<Vector> listtemp = this.studentDao.findStudentByName(info.getText().trim());
                if (listtemp.size()==0){
                    JOptionPane.showMessageDialog(null,"没有查到该信息","提示", JOptionPane.WARNING_MESSAGE);
                    tableModel.setRowCount(0);
                    table.updateUI();
                    label.setText("共有"+listtemp.size()+"条记录");
                    first.setVisible(false);
                    previous.setVisible(false);
                    next.setVisible(false);
                    end.setVisible(false);
                    nowpage.setVisible(false);
                }else {
                    tableModel.setRowCount(0);
                    tableModel.setDataVector(listtemp, name);
                    table.updateUI();
                    label.setText("共有" + listtemp.size() + "条记录");
                    first.setVisible(false);
                    previous.setVisible(false);
                    next.setVisible(false);
                    end.setVisible(false);
                    nowpage.setVisible(false);
                }
            }
            else if (comboBox.getSelectedItem().equals("班级")){
                Vector<Vector> listtemp = this.studentDao.findStudentByClass(String.valueOf(major1.getSelectedItem()),String.valueOf(classname1.getSelectedItem()));
                if (listtemp.size()==0){
                    JOptionPane.showMessageDialog(null,"没有查到该信息","提示", JOptionPane.WARNING_MESSAGE);
                    tableModel.setRowCount(0);
                    table.updateUI();
                    label.setText("共有"+listtemp.size()+"条记录");
                    first.setVisible(false);
                    previous.setVisible(false);
                    next.setVisible(false);
                    end.setVisible(false);
                    nowpage.setVisible(false);
                }else {
                    tableModel.setRowCount(0);
                    tableModel.setDataVector(listtemp, name);
                    table.updateUI();
                    label.setText("共有" + listtemp.size() + "条记录");
                    first.setVisible(false);
                    previous.setVisible(false);
                    next.setVisible(false);
                    end.setVisible(false);
                    nowpage.setVisible(false);
                }
            }
        }
    }
}
