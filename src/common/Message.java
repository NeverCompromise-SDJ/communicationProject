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
    private int fileLen;//文件的大小（字节为单位）
    private String fileSourcePath;//文件的原路径（文件发送方的源文件位置）
    private String fileDestPath;//文件的目标路径（文件接收方接受的文件，其保存的位置）

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

    public byte[] getFileBytes() {
        return fileBytes;
    }

    public void setFileBytes(byte[] fileBytes) {
        this.fileBytes = fileBytes;
    }

    public int getFileLen() {
        return fileLen;
    }

    public void setFileLen(int fileLen) {
        this.fileLen = fileLen;
    }

    public String getFileSourcePath() {
        return fileSourcePath;
    }

    public void setFileSourcePath(String fileSourcePath) {
        this.fileSourcePath = fileSourcePath;
    }

    public String getFileDestPath() {
        return fileDestPath;
    }

    public void setFileDestPath(String fileDestPath) {
        this.fileDestPath = fileDestPath;
    }
}
