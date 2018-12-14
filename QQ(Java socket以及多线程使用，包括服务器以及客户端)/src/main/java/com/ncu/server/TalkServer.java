package com.ncu.server;

import com.ncu.dao.MessageDao;
import com.ncu.dao.UserDao;
import com.ncu.pojo.HistoryMessage;
import com.ncu.pojo.User;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class TalkServer {
    private HashMap<String,ClientThread> hashtable;
    private ArrayList<ClientThread> clients;
    public static void main(String[] args) throws IOException {
        new TalkServer();
    }
    public TalkServer() throws IOException {
        hashtable = new HashMap<>();
        clients = new ArrayList<>();
        ServerSocket ss = new ServerSocket(6666);
        ServerThread serverThread = new ServerThread(ss);
        serverThread.start();
    }

    class ServerThread extends Thread {
        ServerSocket serverSocket;
        public ServerThread(ServerSocket serverSocket) {
            this.serverSocket = serverSocket;
        }
        public void run() {
            while (true){
                try {
                    Socket socket = serverSocket.accept();
                    ClientThread clientThread = new ClientThread(socket);
                    clientThread.start();
                    clients.add(clientThread);
                } catch (IOException e) {
                }
            }
        }
    }
    class ClientThread extends Thread{
        private Socket socket;
        private DataInputStream in;
        private DataOutputStream out;

        public ClientThread(Socket socket) throws IOException {
            this.socket = socket;
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        }

        public Socket getSocket() {
            return socket;
        }

        public void setSocket(Socket socket) {
            this.socket = socket;
        }

        public DataInputStream getIn() {
            return in;
        }

        public DataOutputStream getOut() {
            return out;
        }

        public void run(){
            String message;
            String[] a = new String[10];
            String[] b = new String[10];
            while (true) {
                try {
                    message = in.readUTF();
                    StringTokenizer s1 = new StringTokenizer(message);
                    StringTokenizer s2 = new StringTokenizer(message,"用户");
                    int i = 0;
                    int n = 0;
                    int m = 0;
                    while (s1.hasMoreTokens()) {
                        a[i] = s1.nextToken();
                        i++;
                    }
                    while (s2.hasMoreTokens()){
                        b[n] = s2.nextToken();
                        System.out.println(b[n]);
                        n++;
                    }
                    if (a[0].equals("用户--")||a[i-1].equals("进入聊天室")||a[i-1].equals("离开了聊天室")) {
                        for (int j = clients.size() - 1; j >= 0; j--) {
                            clients.get(j).getOut().writeUTF(message);
                        }
                    }
                    else if (n==2){
                        hashtable.put(b[0],this);
                    }
                    else if(n>2){
                        if (hashtable.get(b[1]) == null){
                            hashtable.get(b[0]).getOut().writeUTF(message);
                            MessageDao messageDao = new MessageDao();
                            UserDao userDao = new UserDao();
                            HistoryMessage historyMessage = new HistoryMessage();
                            historyMessage.setUserSendId(userDao.FindByName(b[0]).getUserId());
                            historyMessage.setUserRecId(userDao.FindByName(b[1]).getUserId());
                            historyMessage.setMessageInfo(b[2]);
                            historyMessage.setMessage_state("未读");
                            messageDao.InsertMessage(historyMessage);
                        }
                        else {
                            hashtable.get(b[0]).getOut().writeUTF(message);
                            hashtable.get(b[1]).getOut().writeUTF(message);
                        }
                    }
                } catch (IOException e) {

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}



