package com.ncu.ui;

import com.ncu.dao.*;
import com.ncu.pojo.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class ChangeClassCursePanel extends JPanel implements ActionListener{
    JComboBox comboBox;
    MajorDao majorDao;
    ClassInfoDao classInfoDao;
    TeacherDao teacherDao;
    ClassCurseDao classCurseDao;
    JComboBox classcomboBox;
    JTable cursetable;
    TableModel cursetableModel;
    JButton searchClassCurse;
    JButton saveChangeClassCurse;
    ChangeCursePanel changeCursePanel;
    String curse[][] = new String[10][7];
    String day[] = {"周一","周二","周三","周四","周五","周六","周日"};
    public ChangeClassCursePanel(){
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
        saveChangeClassCurse = new JButton("保存");
        this.add(saveChangeClassCurse);
        saveChangeClassCurse.addActionListener(this::actionPerformed);
        saveChangeClassCurse.setBounds(510,20,80,30);
        saveChangeClassCurse.setEnabled(false);
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
            saveChangeClassCurse.setEnabled(true);
            ArrayList<Integer> row = new ArrayList<>();
            ArrayList<Integer> col = new ArrayList<>();
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
            cursetable.setEnabled(true);
            cursetable.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    int row = cursetable.rowAtPoint(e.getPoint());
                    int col = cursetable.columnAtPoint(e.getPoint());
                    if(e.getClickCount() == 2){
                        changeCursePanel = new ChangeCursePanel(String.valueOf(comboBox.getSelectedItem()));
                        changeCursePanel.sureChange.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                ArrayList<Teacher> list = teacherDao.findTeacherByNameImp(String.valueOf(changeCursePanel.teachercomboBox.getSelectedItem()));
                                String string =changeCursePanel.cursecomboBox.getSelectedItem()+"--"+ list.get(0).getTeacher_account()+"--"+changeCursePanel.teachercomboBox.getSelectedItem();
                                cursetable.setValueAt(string,row,col);
                                changeCursePanel.dispose();
                                saveChangeClassCurse.setEnabled(true);
                            }
                        });
                    }
                    if (e.getButton()==MouseEvent.BUTTON3){
                        int focusedRowIndex = cursetable.rowAtPoint(e.getPoint());
                        int focusedColIndex = cursetable.columnAtPoint(e.getPoint());
                        //将表格所选项设为当前右键点击的行signdb
                        //弹出菜单
                        JPopupMenu popupMenu = new JPopupMenu();
                        JMenuItem delItem = new JMenuItem("删除");
                        delItem.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                cursetable.setValueAt(null,focusedRowIndex,focusedColIndex);
                            }
                        });
                        popupMenu.add(delItem);
                        popupMenu.show(cursetable, e.getX(), e.getY());
                    }
                }
            });
            cursetable.setRowHeight(50);
            cursetable.setEnabled(false);
            JScrollPane scrollPane = new JScrollPane(cursetable);
            this.add(scrollPane);
            scrollPane.setBounds(20,85,750,525);
        }
        if (e.getSource()==saveChangeClassCurse){
            ArrayList<ClassCurse> list = new ArrayList<>();
            for (int i=0;i<10;i++){
                for (int j = 0;j<7;j++){
                    if (cursetable.getValueAt(i,j)!=null){
                        String curseinfo = (String) cursetable.getValueAt(i,j);
                        System.out.println(curseinfo);
                        StringTokenizer str = new StringTokenizer(curseinfo,"-");
                        ArrayList<String> info = new ArrayList<>();
                        while (str.hasMoreTokens()){
                            info.add(str.nextToken());
                        }
                        ClassCurse classCurse = new ClassCurse();
                        classCurse.setClass_curse_name(info.get(0));
                        classCurse.setClass_curse_day(String.valueOf(j+1));
                        classCurse.setClass_curse_index(String.valueOf(i+1));
                        classCurse.setClass_curse_teacher(info.get(1));
                        classCurse.setClass_curse_teacher_name(info.get(2));
                        classCurse.setClass_curse_major(String.valueOf(comboBox.getSelectedItem()));
                        classCurse.setClass_curse_classname(String.valueOf(classcomboBox.getSelectedItem()));
                        list.add(classCurse);
                    }
                }
            }
            classCurseDao.deleteClassCurse(String.valueOf(comboBox.getSelectedItem()),String.valueOf(classcomboBox.getSelectedItem()));
            int count = classCurseDao.insertClassCurse(list);
            if (count!=0){
                JOptionPane.showMessageDialog(null,"修改成功","提示",JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawLine(0,60,800,60);
    }
    class ChangeCursePanel extends JFrame{
        JComboBox cursecomboBox;
        JComboBox teachercomboBox;
        CurseDao curseDao;
        TeacherDao teacherDao;
        JButton sureChange;
        public ChangeCursePanel(String major){
            curseDao = new CurseDao();
            teacherDao = new TeacherDao();
            Dimension screenSize =Toolkit.getDefaultToolkit().getScreenSize();
            this.setLayout(null);
            this.setSize(240,200);
            this.setLocation(screenSize.width/2-240/2,screenSize.height/2-200/2);
            cursecomboBox = new JComboBox();
            this.add(cursecomboBox);
            cursecomboBox.addItem("选择课程");
            cursecomboBox.setBounds(30,20,180,30);
            teachercomboBox = new JComboBox();
            teachercomboBox.addItem("选择教师");
            this.add(teachercomboBox);
            teachercomboBox.setBounds(30,70,180,30);
            ArrayList<Curse> curselist = curseDao.selectCurse(major);
            ArrayList<Teacher> teacherlist = teacherDao.findTeacherByJobImp(String.valueOf(major));
            for (int i = 0;i<curselist.size();i++){
                cursecomboBox.addItem(curselist.get(i).getCurse_name());
            }
            for (int i = 0;i<teacherlist.size();i++){
                teachercomboBox.addItem(teacherlist.get(i).getTeacher_name());
            }
            sureChange = new JButton("确定");
            this.add(sureChange);
            sureChange.setBounds(80,120,80,30);
            this.setVisible(true);
            this.setResizable(false);
            this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        }
    }
}
