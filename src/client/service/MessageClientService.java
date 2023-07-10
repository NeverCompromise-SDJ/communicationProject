package client.service;

import common.Message;
import common.MessageType;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;

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
        LocalDateTime sendTime = LocalDateTime.now();
        String sendTimeOfYear = String.valueOf(sendTime.getYear());
        String sendTimeOfMonth = String.valueOf(sendTime.getMonthValue());
        String sendTimeOfDay = String.valueOf(sendTime.getDayOfMonth());
        String sendTimeOfHour = String.valueOf(sendTime.getHour());
        String sendTimeOfMinute = String.valueOf(sendTime.getMinute());
        String sendTimeOfSecond = String.valueOf(sendTime.getSecond());
        msg.setSendTime(sendTimeOfYear + "-" + sendTimeOfMonth + "-" + sendTimeOfDay + " " + sendTimeOfHour + ":" +
                sendTimeOfMinute + ":" + sendTimeOfSecond);
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
}
