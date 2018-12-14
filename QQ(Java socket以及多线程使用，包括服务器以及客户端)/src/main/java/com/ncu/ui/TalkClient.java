package com.ncu.ui;
import com.ncu.pojo.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.InetAddress;
import java.net.*;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * Created by LiXiaobin on 2017/12/8.
 */
public class TalkClient extends JFrame implements ActionListener {
    Socket mySocket;
    DataInputStream in = null;
    DataOutputStream out = null;
    Thread t;
    User user;
    JTextArea showArea;
    JTextArea onlineMes;
    JTextArea inputArea;
    JButton send;
    JButton close;
    String TalkStyle;
    public TalkClient(final User user,String TalkStyle) {
        this.user = user;
        this.TalkStyle = TalkStyle;
        this.setTitle(user.getUserName()+TalkStyle);
        this.setBounds(600, 200, 800, 750);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setLayout(new BorderLayout());
        JPanel north = new JPanel();
        north.setLayout(new BorderLayout());
        showArea = new JTextArea(30,50);
        showArea.setLineWrap(true);
        showArea.setWrapStyleWord(true);
        showArea.setEnabled(false);
        onlineMes = new JTextArea(30,15);
        onlineMes.setLineWrap(true);
        onlineMes.setWrapStyleWord(true);
        onlineMes.setEnabled(false);
        north.add(new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,new JScrollPane(showArea),new JScrollPane(onlineMes)));
        this.add(BorderLayout.NORTH,north);
        inputArea = new JTextArea();
        this.add(new JSplitPane(JSplitPane.VERTICAL_SPLIT,north,new JScrollPane(inputArea)));
        JPanel south = new JPanel();
        send = new JButton("发送");
        close = new JButton("关闭");
        south.add(send);
        south.add(close);
        this.add(BorderLayout.SOUTH,south);
        this.setVisible(true);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int n = JOptionPane.showConfirmDialog(null,"是否退出聊天室","提示",JOptionPane.YES_NO_OPTION);
                if(n == JOptionPane.YES_OPTION){
                    try {
                        out = new DataOutputStream(mySocket.getOutputStream());
                        out.writeUTF("用户 "+user.getUserName()+" 离开了聊天室");
                        dispose();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        send.addActionListener(this);
        try {
            mySocket = new Socket("192.168.7.28", 6666);
            DataOutputStream out = new DataOutputStream(mySocket.getOutputStream());
            out.writeUTF("用户 "+user.getUserName() + " 进入聊天室");
            ClientThread clientThread = new ClientThread(this);
            t = new Thread(clientThread);
            t.start();
            this.setVisible(true);
        } catch (IOException e) {}
        inputArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    try {
                        String date = String.format("%tY-%<tm-%<td(%<tA)  %<tT", new Date());
                        out = new DataOutputStream(mySocket.getOutputStream());
                        out.writeUTF("用户-- "+user.getUserName() + "   " + date +"\n" + inputArea.getText());
                        inputArea.setText("");
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String date = String.format("%tY-%<tm-%<td(%<tA)  %<tT", new Date());
            out = new DataOutputStream(mySocket.getOutputStream());
            out.writeUTF("用户-- "+user.getUserName() + "   " + date + "\n" + inputArea.getText());
            inputArea.setText("");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    class ClientThread implements Runnable{
        DataInputStream in;
        TalkClient talkClient;
        DataOutputStream out;

        public ClientThread(TalkClient talkClient) {
            this.talkClient = talkClient;
        }

        @Override
        public void run() {
            while (true){
                try {
                    out = new DataOutputStream(talkClient.mySocket.getOutputStream());
                    in = new DataInputStream(talkClient.mySocket.getInputStream());
                    String s = in.readUTF();
                    StringTokenizer stringTokenizer = new StringTokenizer(s);
                    String date = String.format("%tY-%<tm-%<td(%<tA)  %<tT", new Date());
                    int j = 0;
                    String[] a = new String[10];
                    while(stringTokenizer.hasMoreTokens()){
                        a[j] = stringTokenizer.nextToken();
                        j++;
                    }
                    if (a[1].equals(user.getUserName())&&a[0].equals("用户--")){
                        talkClient.showArea.append("自己"+ "   " +  date + "\n" +a[j-1]+"\n");
                    }
                    else if (a[0].equals("用户")){
                        talkClient.onlineMes.append(s+"\n");
                    }
                    else {
                        talkClient.showArea.append(s);
                    }
                } catch (IOException e) {
                }
            }
        }
    }
}



