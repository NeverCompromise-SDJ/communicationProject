package client.service;

import java.util.HashMap;

/**
 * 该类管理客户端连接到服务器的线程
 */
public class ManageClientConnectServerThread {
    //用于存放客户端与服务端之间链接的线程，
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
    public static ClientConnectServerThread getClientConnectServerThread(String userId) {
        return hm.get(userId);
    }
}
