package server.service;

import common.Message;
import common.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;

/**
 * 该类的对象持有一个服务器socket，是与客户端保持通信的线程
 */
public class ServerConnectClientThread extends Thread {
    private Socket socket;
    private String userId;//客户端连接到服务器的用户id,，用于区分与哪个客户端保持通信

    //更方便得到socket
    public Socket getSocket() {
        return socket;
    }

    public ServerConnectClientThread(Socket socket, String userId) {
        this.socket = socket;
        this.userId = userId;
    }

    @Override
    public void run() {//服务器线程，用于发送/接收客户端的消息
        //用户登录时，服务端的日志
        System.out.println("服务端和客户端" + userId + "保持通信，读取数据。。。");
        //查看登录的用户是否有离线消息，有的话将离线消息发送到对应的客户端中
        Vector<Message> offlineMessages = ManageServerConnectClientThread.outputOfflineMsgSingle(userId);
        if (offlineMessages != null) {
            for (Message msg : offlineMessages) {
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(ManageServerConnectClientThread.getServerConnectClientThread(
                            userId).getSocket().getOutputStream());
                    oos.writeObject(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        while (true) {
            ObjectInputStream ois = null;
            try {
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
                } else if (msg.getMsgType().equals(MessageType.MESSAGE_COMMON)) {
                    //如果收到了来自客户端的私聊信息，那么就转发给接收方的客户端
                    ServerConnectClientThread scctMsgToOne = ManageServerConnectClientThread.getServerConnectClientThread(
                            msg.getGetter());
                    //如果接收方不在线，则将离线消息保存到服务器
                    if (scctMsgToOne == null) {
                        ManageServerConnectClientThread.addOfflineMsgSingle(msg.getGetter(), msg);
                    } else {
                        //如果接收方在线，则通过接收方的userId，拿到接收方服务端的socket即可，然后使用该socket向接收方的客户端发送消息
                        ObjectOutputStream oos = new ObjectOutputStream(scctMsgToOne.getSocket().getOutputStream());
                        oos.writeObject(msg);
                    }
                } else if (msg.getMsgType().equals(MessageType.MESSAGE_TO_ALL)) {
                    //如果收到了来自客户端的群聊信息（在线），那么就转发给出发送方以外的所有在线客户端
                    String[] onlineList = ManageServerConnectClientThread.getOnlineUserList().split(" ");
                    for (String onlineUser : onlineList) {
                        //排除自己，给其他在线的用户发消息
                        if (msg.getSender().equals(onlineUser)) {
                            continue;
                        }
                        ObjectOutputStream oos = new ObjectOutputStream(ManageServerConnectClientThread.getServerConnectClientThread(
                                onlineUser).getSocket().getOutputStream());
                        oos.writeObject(msg);
                    }

                } else if (msg.getMsgType().equals(MessageType.MESSAGE_FILE)) {
                    //如果收到了来自客户端的文件信息，则转发给接收方的客户端
                    ServerConnectClientThread ssctChatToOne = ManageServerConnectClientThread.getServerConnectClientThread(
                            msg.getGetter());
                    //如果接收方不在线，则将离线文件保存到服务器
                    if (ssctChatToOne == null) {
                        ManageServerConnectClientThread.addOfflineMsgSingle(msg.getGetter(), msg);
                    } else {
                        //如果接收方在线，则通过接收方的userId，拿到接收方服务端的socket即可，然后使用该socket向接收方的客户端发送离线文件
                        ObjectOutputStream oos = new ObjectOutputStream(ssctChatToOne.getSocket().getOutputStream());
                        oos.writeObject(msg);
                        //服务端日志
                        System.out.println(msg.getSendTime() + "  " + msg.getSender() + "对" + msg.getGetter()
                                + "发送了文件");
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
