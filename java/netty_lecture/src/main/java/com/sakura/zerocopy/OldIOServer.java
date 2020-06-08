package com.sakura.zerocopy;

import java.io.DataInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 客户端向服务器发送数据，服务器接收，传统的方式服务端
 *
 * @author Mr.Miki
 * @date 2020-06-05 11:53
 * @since 1.0
 **/
public class OldIOServer {
    public static void main(String[] args)throws Exception {
        ServerSocket serverSocket = new ServerSocket(8899);
        System.out.println("server start");
        while (true){
            //阻塞，等待连接到来
            Socket socket = serverSocket.accept();

            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());

            try {
                byte[] bytes = new byte[4096];

                while (true){
                    int readCount = dataInputStream.read(bytes);
                    if(readCount == -1){
                        break;
                    }
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }
}
