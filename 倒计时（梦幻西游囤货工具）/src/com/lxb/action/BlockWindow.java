package com.lxb.action;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by LiXiaobin on 2017/11/23.
 */
public class BlockWindow extends JFrame implements Runnable,ActionListener{
    JTextField hour,minute,second;
    JButton be,re;
    JButton add1,sub1,add2,sub2,add3,sub3;
    Font font = new Font("宋体", Font.PLAIN,35);
    BlockWindow(){
        init();
        this.setVisible(true);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setEnabled(true);
        this.setResizable(false);
    }
    public void init(){
        this.setTitle("梦幻囤货工具");
        this.setBounds(700,400,500,300);
        this.setLayout(null);
        hour = new JTextField(2);
        hour.setBounds(80,50,80,100);
        hour.setFont(font);
        hour.setText("00");
        hour.setHorizontalAlignment(SwingConstants.CENTER);
        add1 = new JButton("+");
        add1.setBounds(80,30,80,20);
        add1.addActionListener(this);
        sub1 = new JButton("-");
        sub1.setBounds(80,150,80,20);
        sub1.addActionListener(this);
        this.add(hour);
        this.add(add1);
        this.add(sub1);
        minute = new JTextField(2);
        minute.setBounds(210,50,80,100);
        minute.setFont(font);
        minute.setText("00");
        minute.setHorizontalAlignment(SwingConstants.CENTER);
        add2 = new JButton("+");
        add2.setBounds(210,30,80,20);
        add2.addActionListener(this);
        sub2 = new JButton("-");
        sub2.setBounds(210,150,80,20);
        sub2.addActionListener(this);
        this.add(minute);
        this.add(add2);
        this.add(sub2);
        second = new JTextField(2);
        second.setBounds(340,50,80,100);
        second.setFont(font);
        second.setText("00");
        second.setHorizontalAlignment(SwingConstants.CENTER);
        add3 = new JButton("+");
        add3.setBounds(340,30,80,20);
        add3.addActionListener(this);
        sub3 = new JButton("-");
        sub3.setBounds(340,150,80,20);
        sub3.addActionListener(this);
        this.add(second);
        this.add(add3);
        this.add(sub3);
        be = new JButton("开始");
        be.setBounds(100,200,100,50);
        this.add(be);
        be.addActionListener(this);
        re = new JButton("重置");
        re.setBounds(300,200,100,50);
        this.add(re);
        re.addActionListener(this);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == re){
            hour.setText("00");
            minute.setText("00");
            second.setText("00");
        }
        if (e.getSource() == be){
            new Thread(this).start();
            re.setEnabled(false);
            be.setEnabled(false);
        }
        if (e.getSource() == add1){
            int a = Integer.parseInt(hour.getText());
            a++;
            if(a<10&&a>=0){
                hour.setText("0"+a);
            }
            if (a>=10&&a<=24){
                hour.setText(String.valueOf(a));
            }
            if(a>24){
                hour.setText("00");
            }
        }
        if (e.getSource() == add2){
            int b = Integer.parseInt(minute.getText());
            b++;
            if(b<10&&b>=0){
                minute.setText("0"+b);
            }
            if(b>=10&&b<=59){
                minute.setText(String.valueOf(b));
            }
            if (b>59){
                minute.setText("00");
            }
        }
        if (e.getSource() == add3){
            int c = Integer.parseInt(second.getText());
            c++;
            if(c<10&&c>=0){
                second.setText("0"+c);
            }
            if(c>=10&&c<= 59){
                second.setText(String.valueOf(c));
            }
            if (c > 59){
                second.setText("00");
            }
        }
        if (e.getSource() == sub1){
            int a  = Integer.parseInt(hour.getText());
            a--;
            if(a<10&&a>=0){
                hour.setText("0"+a);
            }
            if (a>=10&&a<=24){
                hour.setText(String.valueOf(a));
            }
            if(a<0){
                hour.setText("24");
            }
        }
        if (e.getSource() == sub2){
            int a  = Integer.parseInt(minute.getText());
            a--;
            if(a<10&&a>=0){
                minute.setText("0"+a);
            }
            if (a>=10&&a<=59){
                minute.setText(String.valueOf(a));
            }
            if(a<0){
                minute.setText("59");
            }
        }
        if (e.getSource() == sub3){
            int a  = Integer.parseInt(second.getText());
            a--;
            if(a<10&&a>=0){
                second.setText("0"+a);
            }
            if (a>=10&&a<=59){
                second.setText(String.valueOf(a));
            }
            if(a<0){
                second.setText("59");
            }
        }
    }
    @Override
    public void run() {
        int time = Integer.parseInt(hour.getText())*60*60+Integer.parseInt(minute.getText())*60+Integer.parseInt(second.getText());
        boolean aa = true;
        while (aa){
            time--;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int hh = time / 60 / 60 % 60;
            int mm = time / 60 % 60;
            int ss = time % 60;
            if (hh>=0&&hh<100){
                hour.setText("0"+hh);
            }
            if (hh>=10&&hh<24){
                hour.setText(String.valueOf(hh));
            }
            if(hh>24){
                hour.setText("00");
            }

            if (mm>=0&&mm<100){
                minute.setText("0"+mm);
            }
            if (mm>=10&&mm<=59){
                minute.setText(String.valueOf(mm));
            }
            if (ss>=0&&ss<100){
                second.setText("0"+ss);
            }
            if (ss>=10&&ss<=59){
                second.setText(String.valueOf(ss));
            }
            if(time<0){
                aa = false;
            }
        }
        JOptionPane.showMessageDialog(this,"时间到","提示信息",JOptionPane.INFORMATION_MESSAGE);
        re.setEnabled(true);
        be.setEnabled(true);
    }
}
