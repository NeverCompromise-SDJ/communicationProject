package client.service;

import common.Message;
import common.MessageType;
import common.User;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * 该类完成用户登录注册等功能
 */
public class UserClientService {
    //我们可能在其他地方使用User对象，因此将User对象做为成员属性
    private User user = new User();

    //发送userId和pwd到服务器验证该用户是否合法
    public boolean checkUser(String userId, String pwd) {
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
            //
            if (msg.getMsgType().equals(MessageType.MESSAGE_LOGIN_SUCCEED)) {//登陆成功
                //创建一个与服务器端保持通信的线程（ClientConnectServerThread）

            } else {//登录失败

            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
