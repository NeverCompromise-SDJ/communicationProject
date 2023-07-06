package client.service;

import common.Message;
import common.MessageType;
import common.User;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * 该类为客户端的服务，与服务端交互，完成用户登录注册等功能
 */
public class UserClientService {
    //我们可能在其他地方使用User对象，因此将User对象做为成员属性
    private User user = new User();

    //发送userId和pwd到服务器验证该用户是否合法
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

}
