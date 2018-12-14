package com.ncu.exe;

import com.ncu.ui.Log;

import javax.swing.*;
import java.awt.*;

public class ManageMain {
    public static void main(String[] args) {
        Dimension screenSize =Toolkit.getDefaultToolkit().getScreenSize();
        Log log =new Log("在线课堂管理端登陆");
        log.setSize(512,400);
        //让窗口居中显示
        log.setLocation(screenSize.width/2-512/2,screenSize.height/2-400/2);
        log.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        log.setVisible(true);
    }
}
