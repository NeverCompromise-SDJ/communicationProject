package server.service;

import common.Message;
import common.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
                //如果收到了来自客户端的拉取用户在线列表的请求，则将在线用户的信息返回给客户端
                if (msg.getMsgType().equals(MessageType.MESSAGE_GET_ONLINE_LIST)) {
                    //用户请求在线列表时，服务端会打印日志
                    System.out.println(msg.getSender() + "用户请求在线用户列表");
                    //将在线用户的信息返回给客户端
                    Message onlineUserListMsg = new Message();
                    onlineUserListMsg.setMsgType(MessageType.MESSAGE_RETURN_ONLINE_LIST);
                    onlineUserListMsg.setGetter(msg.getSender());
                    //从服务管理类那里得到在线用户列表，写入到Message对象的内容中
                    onlineUserListMsg.setContent(ManageServerConnectClientThread.getOnlineUserList());
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(onlineUserListMsg);
                } else if (msg.getMsgType().equals(MessageType.MESSAGE_CLIENT_EXIT)) {
                    //如果收到了来自客户端的退出请求
                    System.out.println(msg.getSender() + "退出");
                    //则从管理服务端和客户端的集合中删除该服务端线程
                    ManageServerConnectClientThread.removeServerConnectClientThread(msg.getSender());
                    //关闭该服务端线程对应的socket
                    socket.close();
                    //关闭该服务端线程
                    break;
                } else {

                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
