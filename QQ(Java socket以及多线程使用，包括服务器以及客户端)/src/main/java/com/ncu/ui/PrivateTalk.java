package com.ncu.ui;

import com.ncu.dao.MessageDao;
import com.ncu.dao.UserDao;
import com.ncu.pojo.HistoryMessage;
import com.ncu.pojo.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

/**
 * Created by LiXiaobin on 2017/12/25.
 */
public class PrivateTalk extends JFrame implements ActionListener{
    Socket mySocket;
    DataInputStream in = null;
    DataOutputStream out = null;
    Thread t;
    JTextArea showArea;
    JTextArea onlineMes;
    JTextArea inputArea;
    JButton send;
    JButton close;
    User user;
    MessageDao messageDao;
    UserDao userDao;
    public PrivateTalk(final User user1, final User user2) {
        this.user = user1;
        userDao = new UserDao();
        messageDao = new MessageDao();
        this.setTitle(user1.getUserName()+"  TO  "+user2.getUserName());
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
        onlineMes.append("昵称: "+user2.getUserName()+"\n");
        onlineMes.append("性别: "+user2.getUserSex()+"\n");
        onlineMes.append("状态: "+user2.getState()+"\n");
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
                int n = JOptionPane.showConfirmDialog(null,"是否关闭聊天窗口","提示",JOptionPane.YES_NO_OPTION);
                if(n == JOptionPane.YES_OPTION){
                    dispose();
                }
            }

            @Override
            public void windowOpened(WindowEvent e) {
                ArrayList<HistoryMessage> arrayList = new ArrayList<HistoryMessage>();
                arrayList = messageDao.FindMessqge(user1.getUserId());
                try {
                    for (int i = 0;i<arrayList.size();i++){
                        showArea.append(userDao.FindById(arrayList.get(i).getUserSendId()).getUserName()+" " +
                                arrayList.get(i).getMessageDate() +" "+arrayList.get(i).getMessage_state()+"\n"
                                +arrayList.get(i).getMessageInfo());
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        send.addActionListener(this);
        try {
            mySocket = new Socket("192.168.7.28", 6666);
            ClientThreadS clientThread = new ClientThreadS(this);
            t = new Thread(clientThread);
            t.start();
            out = new DataOutputStream(mySocket.getOutputStream());
            out.writeUTF("用户"+user1.getUserName() +"用户"+user2.getUserName()+"用户");
            this.setVisible(true);
        } catch (IOException e) {}
        inputArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER){
                    try {
                        out = new DataOutputStream(mySocket.getOutputStream());
                        out.writeUTF("用户"+user1.getUserName() +"用户"+user2.getUserName()+"用户" + inputArea.getText());
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

    }
    class ClientThreadS implements Runnable{
        DataInputStream in;
        PrivateTalk privateTalk;
        DataOutputStream out;

        public ClientThreadS(PrivateTalk privateTalk) {
            this.privateTalk= privateTalk;
        }

        @Override
        public void run() {
            while (true){
                try {
                    out = new DataOutputStream(privateTalk.mySocket.getOutputStream());
                    in = new DataInputStream(privateTalk.mySocket.getInputStream());
                    String s = in.readUTF();
                    String[] a = new String[10];
                    int i = 0;
//                    System.out.println("++++++"+s);
                    StringTokenizer ss = new StringTokenizer(s,"用户");
                    while (ss.hasMoreTokens()){
                        a[i] = ss.nextToken();
                        i++;
                    }
                    String date = String.format("%tY-%<tm-%<td(%<tA)  %<tT", new Date());
                    if (a[0].equals(privateTalk.user.getUserName())){
                        showArea.append("自己 "+ date +"\n"+a[2]);
                    }
                    else {
                        showArea.append(a[1]+ " " +date+"\n"+a[2]);
                    }
                } catch (IOException e) {
                }
            }
        }
    }
}
