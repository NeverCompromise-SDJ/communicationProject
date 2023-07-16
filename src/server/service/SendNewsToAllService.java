package server.service;

import common.Message;
import common.MessageType;
import utilityTool.UtilityTool;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;

/**
 * 用于推送新闻至所有在线客户端
 *
 * @author SongDongJie
 * @create 2023/7/16 - 12:53
 */
public class SendNewsToAllService implements Runnable {
    Scanner scanner = new Scanner(System.in);

    @Override
    public void run() {
        //单独起一个线程，不断接收输入的新闻内容来进行推送。
        while (true) {
            System.out.println("请输入需要推送的新闻[输入\"exit\"退出推送服务]：");
            String news = scanner.next();
            if (news.equals("exit")) {
                break;
            }
            //给所有在线客户端推送消息
            String[] onlineList = ManageServerConnectClientThread.getOnlineUserList().split(" ");
            //没有在线的客户端则取消此次推送
            if (onlineList[0].equals("")) {
                System.out.println("没有在线的客户端，推送失败请重试");
                continue;
            }
            //有在线的客户端，则向在线的客户端推送消息
            Message msg = new Message();
            msg.setContent(news);
            msg.setSendTime(UtilityTool.getLocalTime());
            msg.setSender("服务器");
            msg.setMsgType(MessageType.MESSAGE_TO_ALL);
            for (String onlineUser : onlineList) {
                try {
                    ObjectOutputStream oos = new ObjectOutputStream(ManageServerConnectClientThread.getServerConnectClientThread(
                            onlineUser).getSocket().getOutputStream());
                    oos.writeObject(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        System.out.println("推送服务已退出");
    }
}
