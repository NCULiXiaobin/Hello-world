package com.ncu.ui;

import com.ncu.dao.FriendDao;
import com.ncu.dao.UserDao;
import com.ncu.pojo.Friend;
import com.ncu.pojo.User;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;


public class ClientMainUI extends JFrame implements ActionListener{
    private ImageIcon img;
    private JLabel showImg;
    private JPanel jPanel;
    private JLabel userName;
    private JLabel userSex;
    private JList friendList;
    private JScrollPane jScrollPane;
    private JButton all;
    private JButton friend;
    private int y = 0;
    DataOutputStream out;
    Font font;
    Border border;
    User user;
    Socket logSocket;
    UserDao userDao;
    FriendDao friendDao;
    ArrayList<Friend> list;

    private final static int WIDTH=100;
    private final static int HEIGHT=100;

    public ClientMainUI(final User user) throws SQLException {
        this.user = user;
        userDao = new UserDao();
        friendDao = new FriendDao();
        this.setLayout(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    out = new DataOutputStream(logSocket.getOutputStream());
                    out.writeUTF("用户@"+user.getUserName() + "@下线了");
                    userDao.UpdateLog(user.getUserId(),"离线");
                } catch (IOException e1){}
            }
        });
        img=new ImageIcon("E:\\IdeaTest\\QQ\\src\\main\\useImg.png");
        img.setImage(img.getImage().getScaledInstance(this.WIDTH,this.HEIGHT,Image.SCALE_DEFAULT));
        font = new Font("宋体",1,25);

        showImg=new JLabel();
        showImg.setIcon(img);

        this.add(showImg);
        showImg.setBounds(0,0,100,100);
        this.setBounds(1200,100,400,745);

        jPanel = new JPanel(new BorderLayout());
        userName = new JLabel("  昵称:"+user.getUserName());
        userName.setFont(font);
        jPanel.add(BorderLayout.CENTER,userName);
        userSex = new JLabel("  性别:"+user.getUserSex());
        userSex.setFont(font);
        jPanel.add(BorderLayout.SOUTH,userSex);
        this.add(jPanel);
        jPanel.setBounds(100,0,300,100);
        border = BorderFactory.createEtchedBorder();
        jPanel.setBorder(border);

        friend = new JButton("好友列表");
        friend.addActionListener(this);
        this.add(friend);
        friend.setBounds(0,100,400,30);

        list = new ArrayList<Friend>();
        list = friendDao.FindFriend(user.getUserId());
        String[] name = new String[list.size()];
        for (int i = 0;i<list.size();i++){
            name[i] = userDao.FindById(list.get(i).getSecondUserId()).getUserName();
        }
        jScrollPane = new JScrollPane();
         friendList = new JList(name);
        friendList.setFixedCellHeight(50);
        friendList.setFont(font);
        friendList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount()==2){
                    try {
                        System.out.println(friendList.getSelectedValue());
                        new PrivateTalk(user,userDao.FindByName((String) friendList.getSelectedValue()));
                    } catch (SQLException e1) {
                        JOptionPane.showMessageDialog(null,"该用户已被注销,现在删除","提示",JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        });
//        jScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.add(jScrollPane);
        jScrollPane.setBounds(0,130,395,500);

        all = new JButton("群聊");
        all.addActionListener(this);
        this.add(all);
        all.setBounds(0,630,400,80);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        try {
            logSocket = new Socket("192.168.7.28", 6666);
            out = new DataOutputStream(logSocket.getOutputStream());
            out.writeUTF("用户@"+user.getUserName() + "@上线了");
            this.setVisible(true);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "登陆失败,服务器未开启", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == all){
            new TalkClient(user,"群聊中");
        }
        if (e.getSource() ==friend){
            jScrollPane.setViewportView(friendList);
        }
    }
}