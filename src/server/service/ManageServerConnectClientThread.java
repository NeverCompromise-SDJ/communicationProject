package server.service;

import java.util.HashMap;
import java.util.Iterator;

/**
 * 该类管理服务器连接到客户端的线程
 */
public class ManageServerConnectClientThread {
    //用于存放服务器连接到客户端的线程，
    private static HashMap<String, ServerConnectClientThread> connectList = new HashMap<>();

    /**
     * 添加某个线程到集合中
     *
     * @param userId 用户id
     * @param scct   用户id对应的ServerConnectClientThread对象
     */
    public static void addServerConnectClientThread(String userId, ServerConnectClientThread scct) {
        connectList.put(userId, scct);
    }

    /**
     * 通过用户id得到对应线程
     *
     * @param userId 用户id
     */
    public static ServerConnectClientThread getServerConnectClientThread(String userId) {
        return connectList.get(userId);
    }

    /**
     * 得到在线用户的列表，onlineUserList的格式为每个在线用户名之间以空格分开，如：sdj sdj1 sdj2
     *
     * @return 返回在线用户列表
     */
    public static String getOnlineUserList() {
        String onlineUserList = "";
        Iterator<String> onlineUserIterator = connectList.keySet().iterator();
        while (onlineUserIterator.hasNext()) {
            onlineUserList += onlineUserIterator.next() + " ";
        }
        return onlineUserList;
    }
}
