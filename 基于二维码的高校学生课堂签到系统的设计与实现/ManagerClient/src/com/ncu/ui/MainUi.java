package com.ncu.ui;

import com.ncu.pojo.Manager;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainUi extends JFrame implements ActionListener {
    JMenuBar menuBar;
    JMenu menu,menu2;
    JMenuItem item,item1,item2,item3;
    JLabel label;
    ImageIcon imageIcon;
    JScrollPane left;
    JPanel right;
    JButton course,teacher,student,notice;
    Border lineborder;
    JPanel courseImp;
    JPanel teacherImp;
    JPanel studentImp;
    JButton searchCourse,changeCourse,addCourse;
    JButton searchTeacher,changeTeacher,addTeacher;
    JButton searchStudent,changeStudent,addStudent;
    int flag = 0;
    int flag1 = 0;
    int flag2 = 0;
    private static int Y = 0;
    public MainUi(Manager manager){
        Dimension screenSize =Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(1000,850);
        this.setLocation(screenSize.width/2-1000/2,screenSize.height/2-850/2);
        lineborder = BorderFactory.createLineBorder(Color.BLACK);
        this.setTitle("在线课堂管理界面");
        this.setResizable(false);

        menuBar = new JMenuBar();
        menu = new JMenu("登陆管理");
        item = new JMenuItem("班级信息管理");
        item.addActionListener(this::actionPerformed);
        item3 = new JMenuItem("专业课程管理");
        item3.addActionListener(this::actionPerformed);
        item1 = new JMenuItem("切换账号");
        item1.addActionListener(this::actionPerformed);
        item2 = new JMenuItem("退出登录");
        item2.addActionListener(this::actionPerformed);
        menu.add(item);
        menu.addSeparator();
        menu.add(item1);
        menu.addSeparator();;
        menu.add(item2);
        menuBar.add(menu);
        menu2 = new JMenu("信息管理");
        menu2.add(item);
        menu2.add(item3);
        menuBar.add(menu2);
        this.setJMenuBar(menuBar);

        this.setLayout(null);
        imageIcon = new ImageIcon("MainUiImg.png");
        label = new JLabel(imageIcon);
        this.add(label);
        label.setBounds(0,0,1000,150);

        left = new JScrollPane();
        left.setLayout(null);
        course = new JButton("课程信息管理");
        course.addActionListener(this::actionPerformed);
        left.add(course);
        course.setBounds(0,Y,200,50);
        teacher = new JButton("教师信息管理");
        teacher.addActionListener(this::actionPerformed);
        left.add(teacher);
        teacher.setBounds(0,Y+50,200,50);
        student = new JButton("学生信息管理");
        student.addActionListener(this::actionPerformed);
        left.add(student);
        student.setBounds(0,Y+100,200,50);
        notice = new JButton("查看当前公告");
        notice.addActionListener(this::actionPerformed);
        left.add(notice);
        notice.setBounds(0,Y+150,200,50);
        this.add(left);
        left.setBounds(0,150,200,700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        right = new JPanel();
        right.setLayout(null);
        right.setBorder(lineborder);
        this.add(right);
        right.setBounds(200,150,800,700);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == item){
            ClassInfoUi classInfo = new ClassInfoUi();
        }
        if (e.getSource() == item3){
            CurseMainUi curseMainUi = new CurseMainUi();
        }
        if (e.getSource() == course){
            if (flag == 0)
            {
                courseImp = new JPanel();
                this.courseImp.setVisible(true);
                courseImp.setLayout(null);
                searchCourse = new JButton("查看课程信息");
                searchCourse.addActionListener(this::actionPerformed);
                searchCourse.setBackground(Color.lightGray);
                courseImp.add(searchCourse);
                searchCourse.setBounds(0,0,200,40);
                changeCourse = new JButton("修改课程信息");
                changeCourse.addActionListener(this::actionPerformed);
                changeCourse.setBackground(Color.lightGray);
                courseImp.add(changeCourse);
                changeCourse.setBounds(0,40,200,40);
                addCourse = new JButton("新增课程信息");
                addCourse.addActionListener(this::actionPerformed);
                addCourse.setBackground(Color.lightGray);
                courseImp.add(addCourse);
                addCourse.setBounds(0,80,200,40);
                this.left.add(courseImp);
                courseImp.setBounds(0,50,200,180);
                teacher.setBounds(0,courseImp.getY()+180,200,50);
                student.setBounds(0,teacher.getY()+50,200,50);
                notice.setBounds(0,student.getY()+50,200,50);
                if (teacherImp!=null&&teacherImp.isVisible()&&studentImp!=null&&studentImp.isVisible()){
                    teacherImp.setBounds(0,teacher.getY()+50,200,180);
                    student.setBounds(0,teacherImp.getY()+180,200,50);
                    studentImp.setBounds(0,student.getY()+50,200,180);
                    notice.setBounds(0,studentImp.getY()+180,200,50);
                }
                else if (teacherImp!=null&&teacherImp.isVisible()){
                    teacherImp.setBounds(0,teacher.getY()+50,200,180);
                    student.setBounds(0,teacherImp.getY()+180,200,50);
                    notice.setBounds(0,student.getY()+50,200,50);
                }
                else if (studentImp!=null&&studentImp.isVisible()){
                    studentImp.setBounds(0,student.getY()+50,200,180);
                    notice.setBounds(0,studentImp.getY()+180,200,50);
                }
                flag = 1;
            }
            else if (flag ==1){
                this.courseImp.setVisible(false);
                teacher.setBounds(0,course.getY()+50,200,50);
                student.setBounds(0,teacher.getY()+50,200,50);
                notice.setBounds(0,student.getY()+50,200,50);
                if (teacherImp!=null&&teacherImp.isVisible()&&studentImp!=null&&studentImp.isVisible()){
                    teacherImp.setBounds(0,teacher.getY()+50,200,180);
                    student.setBounds(0,teacherImp.getY()+180,200,50);
                    studentImp.setBounds(0,student.getY()+50,200,180);
                    notice.setBounds(0,studentImp.getY()+180,200,50);
                }
                else if (teacherImp!=null&&teacherImp.isVisible()){
                    teacherImp.setBounds(0,teacher.getY()+50,200,180);
                    student.setBounds(0,teacherImp.getY()+180,200,50);
                    notice.setBounds(0,student.getY()+50,200,50);
                }
                else if (studentImp!=null&&studentImp.isVisible()){
                    studentImp.setBounds(0,student.getY()+50,200,180);
                    notice.setBounds(0,studentImp.getY()+180,200,50);
                }
                flag = 0;
            }
        }
        if (e.getSource() == teacher){
            if (flag1 ==0){
                teacherImp = new JPanel();
                this.teacherImp.setVisible(true);
                teacherImp.setLayout(null);
                searchTeacher = new JButton("查看教师信息");
                searchTeacher.addActionListener(this::actionPerformed);
                searchTeacher.setBackground(Color.lightGray);
                teacherImp.add(searchTeacher);
                searchTeacher.setBounds(0,0,200,40);
                changeTeacher = new JButton("修改教师信息");
                changeTeacher.addActionListener(this::actionPerformed);
                changeTeacher.setBackground(Color.lightGray);
                teacherImp.add(changeTeacher);
                changeTeacher.setBounds(0,40,200,40);
                addTeacher = new JButton("新增教师信息");
                addTeacher.addActionListener(this::actionPerformed);
                addTeacher.setBackground(Color.lightGray);
                teacherImp.add(addTeacher);
                addTeacher.setBounds(0,80,200,40);
                this.left.add(teacherImp);
                teacherImp.setBounds(0,teacher.getY()+50,200,180);
                student.setBounds(0,teacherImp.getY()+180,200,50);
                notice.setBounds(0,student.getY()+50,200,50);
                if (studentImp!=null&&studentImp.isVisible()){
                    studentImp.setBounds(0,student.getY()+50,200,180);
                    notice.setBounds(0,studentImp.getY()+180,200,50);
                }
                flag1 = 1;
            }
            else if (flag1 ==1){
                this.teacherImp.setVisible(false);
                student.setBounds(0,teacher.getY()+50,200,50);
                notice.setBounds(0,student.getY()+50,200,50);
                if (studentImp!=null&&studentImp.isVisible()){
                    studentImp.setBounds(0,student.getY()+50,200,180);
                    notice.setBounds(0,studentImp.getY()+180,200,50);
                }
                flag1 =0;
            }
        }
        if (e.getSource() == student){
            if (flag2 == 0){
                studentImp = new JPanel();
                this.studentImp.setVisible(true);
                studentImp.setLayout(null);
                searchStudent = new JButton("查看学生信息");
                searchStudent.addActionListener(this::actionPerformed);
                searchStudent.setBackground(Color.lightGray);
                studentImp.add(searchStudent);
                searchStudent.setBounds(0,0,200,40);
                changeStudent = new JButton("修改学生信息");
                changeStudent.addActionListener(this::actionPerformed);
                changeStudent.setBackground(Color.lightGray);
                studentImp.add(changeStudent);
                changeStudent.setBounds(0,40,200,40);
                addStudent = new JButton("新增学生信息");
                addStudent.addActionListener(this::actionPerformed);
                addStudent.setBackground(Color.lightGray);
                studentImp.add(addStudent);
                addStudent.setBounds(0,80,200,40);
                this.left.add(studentImp);
                studentImp.setBounds(0,student.getY()+50,200,180);
                notice.setBounds(0,studentImp.getY()+180,200,50);
                flag2 = 1;
            }
            else if (flag2 ==1){
                this.studentImp.setVisible(false);
                flag2 = 0;
                notice.setBounds(0,student.getY()+50,200,50);
            }
        }
        if (e.getSource()==addCourse){
            right.removeAll();
            AddClassCursePanel addClassCursePanel = new AddClassCursePanel();
            right.add(addClassCursePanel);
            addClassCursePanel.setBorder(lineborder);
            addClassCursePanel.setBounds(0,0,800,700);
        }
        if (e.getSource()==searchCourse){
            right.removeAll();
            SearchClassCursePanel searchClassCursePanel = new SearchClassCursePanel();
            right.add(searchClassCursePanel);
            searchClassCursePanel.setBorder(lineborder);
            searchClassCursePanel.setBounds(0,0,800,700);
        }
        if (e.getSource() ==changeCourse){
            right.removeAll();
            ChangeClassCursePanel changeClassCursePanel = new ChangeClassCursePanel();
            right.add(changeClassCursePanel);
            changeClassCursePanel.setBorder(lineborder);
            changeClassCursePanel.setBounds(0,0,800,700);
        }
        if (e.getSource() == searchTeacher){
            right.removeAll();
            SearchTeacherPanel searchTeacherPanel = new SearchTeacherPanel();
            right.add(searchTeacherPanel);
            searchTeacherPanel.setBorder(lineborder);
            searchTeacherPanel.setBounds(0,0,800,700);
        }
        if (e.getSource() == changeTeacher){
            right.removeAll();
            ChangeTeacherPanel changeTeacherPanel = new ChangeTeacherPanel();
            right.add(changeTeacherPanel);
            changeTeacherPanel.setBorder(lineborder);
            changeTeacherPanel.setBounds(0,0,800,700);
        }
        if (e.getSource() == addTeacher){
            right.removeAll();
            AddTeacherPanel addTeacherPanel = new AddTeacherPanel();
            right.add(addTeacherPanel);
            addTeacherPanel.setBorder(lineborder);
            addTeacherPanel.setBounds(0,0,800,700);
        }
        if (e.getSource() == searchStudent){
            right.removeAll();
            SearchStudentPanel searchStudentPanel = new SearchStudentPanel();
            right.add(searchStudentPanel);
            searchStudentPanel.setBorder(lineborder);
            searchStudentPanel.setBounds(0,0,800,700);
        }
        if (e.getSource() ==addStudent){
            right.removeAll();
            AddStudentPanel addStudentPanel = new AddStudentPanel();
            right.add(addStudentPanel);
            addStudentPanel.setBorder(lineborder);
            addStudentPanel.setBounds(0,0,800,700);
        }
        if (e.getSource() == changeStudent){
            right.removeAll();
            ChangeStudentPanel changeStudentPanel = new ChangeStudentPanel();
            right.add(changeStudentPanel);
            changeStudentPanel.setBorder(lineborder);
            changeStudentPanel.setBounds(0,0,800,700);
        }
    }

}
