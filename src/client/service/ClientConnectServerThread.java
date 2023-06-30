package client.service;

import common.Message;
import org.ietf.jgss.Oid;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * 与服务器保持链接的线程
 */
public class ClientConnectServerThread extends Thread {
    //该线程需要持有socket与服务器保持连接
    private Socket socket;

    //通过构造器，接收传入的socket对象
    public ClientConnectServerThread(Socket socket) {
        this.socket = socket;
    }

    //更方便得到socket
    public Socket getSocket() {
        return socket;
    }

    @Override
    public void run() {
        //因为线程需要和服务器通讯，因此做成死循环来不断读取服务器发送的信息
        while (true) {
            try {
                System.out.println("客户端信息，等待从服务端发送的消息");
                ObjectInputStream ois = new ObjectInputStream(new ObjectInputStream(socket.getInputStream()));
                Message msg = (Message) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
    }
}
