package server.service;

import common.Message;
import common.MessageType;
import common.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 服务器，监听9999端口，等待客户端的链接，并保持通信
 */
public class Server {
    private ServerSocket ss = null;
    //用于存放多个已注册用户的信息，模拟数据库
    private static ConcurrentHashMap<String, String> vaildUsers = new ConcurrentHashMap<>();

    //初始化已注册的用户信息
    static {
        vaildUsers.put("100", "123");
        vaildUsers.put("sdj1", "1234");
        vaildUsers.put("肖华", "xiaohua");
    }

    public Server() {
        //端口可以写在配置文件中
        System.out.println("服务器在9999端口监听。。。");
        try {
            //启动服务端推送新闻的线程
            new Thread(new SendNewsToAllService()).start();
            //服务端开始监听客户端的连接
            ss = new ServerSocket(9999);
            while (true) {//当和某个客户端建立连接后，会继续监听等待下一个客户端的链接
                Socket socket = ss.accept();
                //得到socket对象关联的对象输入流，读取客户端的输出
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                //得到socket对象关联的对象输出流，向客户端输出内容
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                //首次客户端输出的是User对象，用于在服务端校验该用户是否存在
                User user = (User) ois.readObject();
                //创建一个Message对象，准备回复客户端
                Message msg = new Message();
                //进行用户合法性的校验
                if (checkUser(user.getUserId(), user.getPasswd())) {
                    //给客户端发送登录成功的信息
                    msg.setMsgType(MessageType.MESSAGE_LOGIN_SUCCEED);
                    oos.writeObject(msg);
                    //启动一个含有服务器socket的线程，用于与客户端socket进行通信
                    ServerConnectClientThread scct = new ServerConnectClientThread(socket, user.getUserId());
                    //为了实现多客户端与服务器进行通信，我们将含有服务器socket的线程放入到集合管理
                    ManageServerConnectClientThread.addServerConnectClientThread(user.getUserId(), scct);
                    scct.start();
                } else {//如果该用户不存在，则服务器不与客户端建立连接，并释放相关的资源
                    //给客户端发送登录失败的信息
                    msg.setMsgType(MessageType.MESSAGE_LOGIN_FAILED);
                    oos.writeObject(msg);
                    System.out.println("服务端的校验-该用户不正确或不存在");
                    //释放相关资源
                    ois.close();
                    oos.close();
                    socket.close();
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            //如果服务端停止监听新的客户端连接了，那么需要关闭ServerSocket
            try {
                ss.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 检查客户端传入的用户是否存在且正确
     *
     * @param userId 用户名
     * @param pwd    密码
     * @return 用户合法返回true，否则返回false
     */
    private boolean checkUser(String userId, String pwd) {
        //如果用户名不存在
        if (vaildUsers.get(userId) == null) {
            return false;
        }
        //如果用户名存在但密码错误
        if (!pwd.equals(vaildUsers.get(userId))) {
            return false;
        }
        //如果用户名存在切密码正确
        return true;
    }
}
