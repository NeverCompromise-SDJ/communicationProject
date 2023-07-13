package client.service;

import utilityTool.UtilityTool;
import common.Message;
import common.MessageType;

import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * 该类提供和消息相关的服务
 */
public class MessageClientService {

    /**
     * 用户间的私聊（在线）
     *
     * @param senderId 发送消息的用户ID
     * @param getterId 接收消息的用户ID
     * @param content  消息内容
     */
    public void sendMessageToOneUser(String senderId, String getterId, String content) {
        //先发送消息给服务端，由服务端转发给另一个用户
        Message msg = new Message();
        msg.setSender(senderId);
        msg.setGetter(getterId);
        msg.setMsgType(MessageType.MESSAGE_COMMON);
        msg.setSendTime(UtilityTool.getLocalTime());
        msg.setContent(content);
        System.out.println(msg.getSendTime() + "  " + senderId + "对" + getterId + "说：" + content);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(
                    senderId).getSocket().getOutputStream());
            oos.writeObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 用户间的群聊（在线）
     *
     * @param senderId 发送消息的用户ID
     * @param content  消息内容
     */
    public void sendMessageToAllUser(String senderId, String content) {
        //先发送消息给服务端，由服务端转发给除自己外的所有在线用户
        Message msg = new Message();
        msg.setSender(senderId);
        msg.setMsgType(MessageType.MESSAGE_TO_ALL);
        msg.setSendTime(UtilityTool.getLocalTime());
        msg.setContent(content);
        System.out.println(msg.getSendTime() + "  " + senderId + "对大家说：" + content);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(
                    senderId).getSocket().getOutputStream());
            oos.writeObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
