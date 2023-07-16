package server.service;

import common.Message;

import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 该类管理服务器连接到客户端的线程
 */
public class ManageServerConnectClientThread {
    //用于存放服务器连接到客户端的线程
    private static ConcurrentHashMap<String, ServerConnectClientThread> connectList = new ConcurrentHashMap<>();
    //用于存放所有用户的离线消息
    private static ConcurrentHashMap<String, Vector<Message>> offlineMsgAll = new ConcurrentHashMap<>();

    /**
     * 得到指定用户的所有离线消息
     *
     * @param userId 用户ID
     * @return 返回一个离线消息的集合
     */
    public static Vector<Message> getOfflineMsgSingle(String userId) {
        return offlineMsgAll.get(userId);
    }

    /**
     * 向某个用户中添加离线消息
     *
     * @param userId 用户ID
     * @param msg    添加的离线消息
     */
    public static void addOfflineMsgSingle(String userId, Message msg) {
        //在存放该用户的离线消息集合上，添加新的离线消息。
        Vector<Message> offlineMsgSingle = getOfflineMsgSingle(userId);
        //如果该用户之前没有离线消息，则新建new一个集合来存放离线消息，并将单个用户离线消息的集合添加至存放了所有用户离线消息的集合中
        if (offlineMsgSingle == null) {
            offlineMsgSingle = new Vector<>();
            offlineMsgAll.put(userId, offlineMsgSingle);
        }
        offlineMsgSingle.add(msg);
    }

    /**
     * 输出指定用户的所有离线消息
     *
     * @param userId 用户ID
     * @return 返回一个离线消息的集合
     */
    public static Vector<Message> outputOfflineMsgSingle(String userId) {
        //如果该用户有离线消息，则将该用户的离线消息从所有用户离线消息的集合中删除。并且返回该用户的离线消息。
        return offlineMsgAll.remove(userId);
    }

    /**
     * 返回在线的用户线程集合
     *
     * @return 服务端在线用户线程的集合
     */
    public static ConcurrentHashMap<String, ServerConnectClientThread> getConnectList() {
        return connectList;
    }

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
     * 删除集合中的某个线程
     *
     * @param userId 用户id
     */
    public static void removeServerConnectClientThread(String userId) {
        connectList.remove(userId);
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
