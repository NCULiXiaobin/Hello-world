package com.ncu.ui;

import com.ncu.dao.TeacherDao;
import com.ncu.pojo.Teacher;
import com.ncu.service.PageShow;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Vector;

public class SearchTeacherPanel extends JPanel implements ActionListener{
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
    int size;
    private static int nowpage1;
    int page1;
    private static int i = 0;
    TeacherDao teacherDao;
    PageShow pageShow;
    ArrayList<Teacher> list;
    Vector<Vector> a =null;
    Vector<String> name;
    public SearchTeacherPanel(){
        nowpage1 =1;
        teacherDao = new TeacherDao();
        pageShow = new PageShow();
        list = new ArrayList<>();
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
        list = teacherDao.findAllTeacher();
        label = new JLabel("共有"+list.size()+"条记录");
        size = list.size();
       if (size%19!=0){
           page1 = size/19+1;
       }else {
           page1 = size/19;
       }
        this.add(label);
        label.setBounds(20,70,100,20);
        a = pageShow.selectPage(1,list);
        name = new Vector<>();
        name.add("工号");    //= {"工号","密码","姓名","性别","年龄","职称","联系电话","紧急联系电话","邮箱","附加信息"};
        name.add("密码");
        name.add("姓名");
        name.add("性别");
        name.add("年龄");
        name.add("专业");
        name.add("联系电话");
        name.add("紧急联系电话");
        name.add("邮箱");
        name.add("附加信息");
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
                            int i = teacherDao.deleteTeacherInfo(Integer.parseInt(String.valueOf(tableModel.getValueAt(focusedRowIndex,0))));
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
                a = pageShow.selectPage(nowpage1,list);
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
                    a = pageShow.selectPage(nowpage1,list);
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
                    a = pageShow.selectPage(nowpage1,list);
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
                a = pageShow.selectPage(nowpage1,list);
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
            if (comboBox.getSelectedItem().equals("工号")){
                int temppage = 0;
                try{
                    Vector<Vector> listtemp = this.teacherDao.findTeacherByAccount(Integer.parseInt(info.getText()));
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
                int temppage = 0;
                System.out.println("cacaca");
                Vector<Vector> listtemp = this.teacherDao.findTeacherByName(info.getText().trim());
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
            else if (comboBox.getSelectedItem().equals("专业")){
                int temppage = 0;
                System.out.println("cacaca");
                Vector<Vector> listtemp = this.teacherDao.findTeacherByJob(info.getText());
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
