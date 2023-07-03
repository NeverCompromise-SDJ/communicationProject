package client.service;

import java.util.HashMap;

/**
 * 该类管理客户端连接到服务器端的线程的类
 */
public class ManageClientConnectServerThread {
    private static HashMap<String, ClientConnectServerThread> hm = new HashMap<>();

    /**
     * 添加某个线程到集合中
     *
     * @param userId 用户id
     * @param ccst   用户id对应的ClientConnectServerThread对象
     */
    public static void addClientConnectServerThread(String userId, ClientConnectServerThread ccst) {
        hm.put(userId, ccst);
    }

    /**
     * 通过用户id得到对应线程
     *
     * @param userId 用户id
     */
    public static void getClientConnectServerThread(String userId) {
        hm.get(userId);
    }
}
