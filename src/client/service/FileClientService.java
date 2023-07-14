package client.service;

import common.MessageType;
import utilityTool.UtilityTool;
import common.Message;

import java.io.*;

/**
 * 该类提供文件传输的服务
 */
public class FileClientService {
    /**
     * 发送文件发给另一个在线用户
     *
     * @param senderId       文件发送方的用户ID
     * @param getterId       文件接收方的用户ID
     * @param fileSourcePath 文件发送方上，源文件的路径
     * @param fileDestPath   文件接收方接收文件时，将文件保存的路径
     */
    public void sendFileToOne(String senderId, String getterId, String fileSourcePath, String fileDestPath) {
        Message msg = new Message();
        msg.setMsgType(MessageType.MESSAGE_FILE);
        msg.setSender(senderId);
        msg.setGetter(getterId);
        msg.setFileSourcePath(fileSourcePath);
        msg.setFileDestPath(fileDestPath);
        msg.setSendTime(UtilityTool.getLocalTime());
        //将本地文件写入到内存中，并作为msg对象的属性
        FileInputStream fis = null;
        byte[] fileBytes = new byte[(int) new File(fileDestPath).length()];
        try {
            fis = new FileInputStream(fileSourcePath);
            fis.read(fileBytes);
            msg.setFileBytes(fileBytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        //将包含文件数据的msg对象发送给服务器进行转发
        try {
            ObjectOutputStream oos = new ObjectOutputStream(ManageClientConnectServerThread.getClientConnectServerThread(
                    senderId).getSocket().getOutputStream());
            oos.writeObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //发送方客户端提示
        System.out.println("\n" + msg.getSendTime() + "  " + msg.getSender() + "对" + msg.getGetter()
                + "发送了文件:" + msg.getFileSourcePath() + "到对方的" + msg.getFileDestPath() + "处");

    }
}
