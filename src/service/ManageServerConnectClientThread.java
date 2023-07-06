package service;

import client.service.ClientConnectServerThread;

import java.util.HashMap;

/**
 * 该类管理服务器连接到客户端的线程
 */
public class ManageServerConnectClientThread {
    //用于存放服务器连接到客户端的线程，
    private static HashMap<String, ServerConnectClientThread> hm = new HashMap<>();

    /**
     * 添加某个线程到集合中
     *
     * @param userId 用户id
     * @param scct   用户id对应的ServerConnectClientThread对象
     */
    public static void addServerConnectClientThread(String userId, ServerConnectClientThread scct) {
        hm.put(userId, scct);
    }

    /**
     * 通过用户id得到对应线程
     *
     * @param userId 用户id
     */
    public static ServerConnectClientThread getServerConnectClientThread(String userId) {
        return hm.get(userId);
    }
}
