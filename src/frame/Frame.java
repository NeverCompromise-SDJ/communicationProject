package frame;

import server.service.Server;

/**
 * 创建服务器进程，启动后台的服务
 */
public class Frame {
    public static void main(String[] args) {
        //启动服务器进程
        new Server();
    }
}
