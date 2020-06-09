package com.sakura.zerocopy;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 客户端向服务器发送数据，服务器接收，传统的方式客户端
 *
 * @author Mr.Miki
 * @date 2020-06-05 11:57
 * @since 1.0
 **/
public class OldIOClient {
    public static void main(String[] args)throws Exception {
        Socket socket = new Socket("localhost", 8899);

        String filePath = "/home/tar.gz/pdf-books-master.zip";
        FileInputStream fileInputStream = new FileInputStream(filePath);
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        byte[] bytes = new byte[4096];
        int readCount = 0;
        //读取数据的总数量
        int total = 0;

        //开始时间
        long startTime = System.currentTimeMillis();

        while ((readCount = fileInputStream.read(bytes)) >= 0){
            total += readCount;
            dataOutputStream.write(bytes);
        }

        System.out.println("发送总字节数: " + total + ", 耗时: " + (System.currentTimeMillis() - startTime));

        dataOutputStream.close();
        socket.close();
        fileInputStream.close();
    }
}
