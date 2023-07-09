package client.service;

import common.Message;
import common.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * 该类的一个对象持有客户端socket，是与服务器保持链接的线程
 */
public class ClientConnectServerThread extends Thread {
    //该线程需要持有socket与服务器保持连接
    private Socket socket;

    //通过构造器，接收传入的socket对象
    public ClientConnectServerThread(Socket socket) {
        this.socket = socket;
    }

    //更方便得到socket
    public Socket getSocket() {
        return socket;
    }

    @Override
    public void run() {
        //因为线程需要和服务器通讯，因此做成死循环来不断读取服务器发送的信息
        while (true) {
            try {
                System.out.println("客户端信息，等待从服务端发送的消息");
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                //注意如果服务器没有发生Message对象，则线程会阻塞在这里
                Message msg = (Message) ois.readObject();
                //如果服务器返回的是在线用户的列表，则输出其内容
                if (msg.getMsgType().equals(MessageType.MESSAGE_RETURN_ONLINE_LIST)) {
                    //我们规定在线列表信息，每个用户之间以空格分开，因此取每一个用户的信息时以空格为分隔符
                    String[] onlineUserList = msg.getContent().split(" ");
                    System.out.println("\n===========在线用户列表==========");
                    for (String username : onlineUserList) {
                        System.out.println("用户：" + username);
                    }

                } else {
                    System.out.println("客户端-其他消息暂未处理");
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }
}
