package client.view;

import client.service.UserClientService;

import java.util.Scanner;

/**
 * 客户端界面
 */

public class ClientView {
    private boolean loop = true;//决定界面的循环展示
    //用于登录服务器、注册用户
    private UserClientService ucs = new UserClientService();

    private ClientView() {
        Scanner scanner = new Scanner(System.in);
        while (loop) {
            System.out.println("=========欢迎登录网络通信系统=========");
            System.out.println("\t\t\t1 登录系统");
            System.out.println("\t\t\t9 退出系统");
            System.out.print("请输入您的选择：");
            String chooseOfFirstMenu = scanner.next();//记录一级菜单的选择
            //一级菜单
            switch (chooseOfFirstMenu) {
                case "1":
                    System.out.print("请输入用户名：");
                    String userId = scanner.next();
                    System.out.print("请输入密码：");
                    String password = scanner.next();
                    //进行用户校验，校验通过则展示二级菜单
                    if (ucs.checkUser(userId, password)) {
                        while (loop) {
                            System.out.println("========网络通信系统二级菜单(用户" + userId + ")========");
                            System.out.println("\t\t\t1 显示在线用户列表");
                            System.out.println("\t\t\t2 群发消息");
                            System.out.println("\t\t\t3 私聊消息");
                            System.out.println("\t\t\t4 发送文件");
                            System.out.println("\t\t\t9 退出系统");
                            System.out.print("请输入您的选择：");
                            String chooseOfSecondMenu = scanner.next();//记录二级菜单的选择
                            switch (chooseOfSecondMenu) {
                                case "1":
                                    ucs.getOnlineUserList();
                                    break;
                                case "2":
                                    System.out.println("群发消息");
                                    break;
                                case "3":
                                    System.out.println("私聊消息");
                                    break;
                                case "4":
                                    System.out.println("发送文件");
                                    break;
                                case "9":
                                    ucs.clientExit();
                                    loop = false;
                                    break;
                                default:
                                    System.out.println("您的输入有误，请重新选择");
                                    break;
                            }
                        }
                    } else {
                        System.out.println("用户名或密码错误");
                    }
                    break;
                case "9":
                    System.out.println("您已成功退出系统");
                    loop = false;
                    break;
                default:
                    System.out.println("您的输入有误，请重新选择");
                    break;
            }
        }

    }

    public static void main(String[] args) {
        new ClientView();
    }
}
