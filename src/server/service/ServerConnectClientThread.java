package server.service;

import common.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * 该类的对象持有一个服务器socket，是与客户端保持通信的线程
 */
public class ServerConnectClientThread extends Thread {
    private Socket socket;
    private String userId;//客户端连接到服务器的用户id,，用于区分与哪个客户端保持通信

    public ServerConnectClientThread(Socket socket, String userId) {
        this.socket = socket;
        this.userId = userId;
    }

    @Override
    public void run() {//服务器线程，用于发送/接收客户端的消息
        while (true) {
            ObjectInputStream ois = null;
            try {
                System.out.println("服务端和客户端" + userId + "保持通信，读取数据。。。");
                ois = new ObjectInputStream(socket.getInputStream());
                Message msg = (Message) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
