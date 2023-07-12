package common;

import java.io.Serializable;

/**
 * 客户端和服务端通信时的消息对象
 */
public class Message implements Serializable {
    private static final Long serialVersionUID = 1L;//序列化ID
    private String sender;//发送者
    private String getter;//接收者
    private String content;//内容
    private String sendTime;//发送时间
    private String msgType;//消息类型(在接口定义消息类型)
    //文件相关的成员
    private byte[] fileBytes;//文件的字节数组
    private int

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
