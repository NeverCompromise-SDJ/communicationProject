package common;

import java.io.Serializable;

/**
 * 客户端和服务端通信时的消息对象
 */
public class Message implements Serializable {
    private static final Long serialVersionUID = 1L;//序列化ID
    String sender;//发送者
    String getter;//接收者
    String content;//内容
    String sendTime;//发送时间
    String msgType;//消息类型

    public static Long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getGetter() {
        return getter;
    }

    public void setGetter(String getter) {
        this.getter = getter;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }
}
