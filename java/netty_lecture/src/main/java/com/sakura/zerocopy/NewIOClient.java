package com.sakura.zerocopy;

import java.io.FileInputStream;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * 客户端向服务器发送数据，服务器接收，零拷贝方式的客户端
 *
 * @author Mr.Miki
 * @date 2020-06-05 15:46
 * @since 1.0
 **/
public class NewIOClient {
    public static void main(String[] args)throws Exception {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost",8899));
        socketChannel.configureBlocking(true);

        String filePath = "/home/tar.gz/pdf-books-master.zip";
        FileChannel channel = new FileInputStream(filePath).getChannel();

        //系统毫秒数
        long startTime = System.currentTimeMillis();
        //transferTo(传输的起始位置,传输的最大字节数,目标channel)
        long transferCount = channel.transferTo(0, channel.size(), socketChannel);

        System.out.println("发送总字节数：" + transferCount +
                ", 耗时：" + (System.currentTimeMillis() - startTime));

    }
}
