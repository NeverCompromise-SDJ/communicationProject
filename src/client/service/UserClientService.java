package client.service;

import common.Message;
import common.MessageType;
import common.User;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.Scanner;

/**
 * 该类为客户端的服务，与服务端交互，完成用户登录注册等功能
 */
public class UserClientService {
    //我们可能在其他地方使用User对象，因此将User对象做为成员属性
    private User user = new User();

    /**
     * 发送userId和pwd到服务器验证该用户是否合法
     *
     * @param userId 用户id
     * @param pwd    用户密码
     * @return 用户是否合法
     */
    public boolean checkUser(String userId, String pwd) {
        boolean isLoginSuccess = false;
        user.setUserId(userId);
        user.setPasswd(pwd);
        Socket socket = null;//服务器端口号为9999
        try {
            socket = new Socket(InetAddress.getLocalHost(), 9999);
            //发送user对象给服务端校验
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(user);
            //接受服务端的返回信息（Message对象）
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Message msg = (Message) ois.readObject();
            //登录成功和失败的操作
            if (msg.getMsgType().equals(MessageType.MESSAGE_LOGIN_SUCCEED)) {//登陆成功
                //启动一个含有客户端socket的线程，用于与服务器socket进行通信
                ClientConnectServerThread ccst = new ClientConnectServerThread(socket);
                ccst.start();
                //为了实现多客户端与服务器进行通信，我们将含有客户端socket的线程放入到集合管理
                ManageClientConnectServerThread.addClientConnectServerThread(userId, ccst);
                //将登录状态置为成功
                isLoginSuccess = true;
            } else {//登录失败
                //登录失败则关闭socket相关资源，不启动与服务器通信的线程
                oos.close();
                ois.close();
                socket.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return isLoginSuccess;
    }

    /**
     * 向服务端请求在线用户信息
     */
    public void getOnlineUserList() {
        //向服务端发送请求在线用户信息的消息
        Message msg = new Message();
        msg.setMsgType(MessageType.MESSAGE_GET_ONLINE_LIST);
        msg.setSender(user.getUserId());
        try {
            //通过用户ID，得到相应的线程，然后得到线程持有的socket，再得到socket对应的输出流，将该流包装成对象流
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(
                    user.getUserId()).getSocket().getOutputStream());
            oos.writeObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 客户端向服务端发送客户端退出信息
     */
    public void clientExit() {
        Message msg = new Message();
        msg.setSender(user.getUserId());
        msg.setMsgType(MessageType.MESSAGE_CLIENT_EXIT);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(
                    user.getUserId()).getSocket().getOutputStream());
            oos.writeObject(msg);
            System.out.println(user.getUserId() + "退出系统");
            /*退出客户端进程（不是退出线程）,这里可以直接用因为是客户端层面的退出，而不是客户端线程层面的退出。
            如果是客户端线程层面的退出，就不能用这个，需要在ClientConnectServerThread上根据服务端发送的退出消息，执行退出操作*/
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String getUser, String content) {
        Message msg = new Message();
        msg.setSender(user.getUserId());
        msg.setGetter(getUser);
        msg.setMsgType(MessageType.MESSAGE_COMMON);
        LocalDateTime sendTime = LocalDateTime.now();
        String sendTimeOfYear = String.valueOf(sendTime.getYear());
        String sendTimeOfMonth = String.valueOf(sendTime.getMonth());
        String sendTimeOfDay = String.valueOf(sendTime.getDayOfMonth());
        String sendTimeOfHour = String.valueOf(sendTime.getHour());
        String sendTimeOfMinute = String.valueOf(sendTime.getMinute());
        String sendTimeOfSecond = String.valueOf(sendTime.getSecond());
        msg.setSendTime(sendTimeOfYear + "-" + sendTimeOfMonth + "-" + sendTimeOfDay + "\t" + sendTimeOfHour + ":" +
                sendTimeOfMinute + ":" + sendTimeOfSecond);
        msg.setContent(content);
    }

}
