package com.sakura.zerocopy;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * 客户端向服务器发送数据，服务器接收，零拷贝方式的服务器端
 *
 * @author Mr.Miki
 * @date 2020-06-05 15:27
 * @since 1.0
 **/
public class NewIOServer {
    public static void main(String[] args)throws Exception {
        //创建一个端口映射到8899端口
        InetSocketAddress address = new InetSocketAddress(8899);

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverSocketChannel.socket();
        //如果想绑定到某一个端口号上，但是那个端口号正处于超时状态，则不能绑定成功，此设置可以绑定成功
        serverSocket.setReuseAddress(true);
        //绑定端口
        serverSocket.bind(address);

        ByteBuffer byteBuffer = ByteBuffer.allocate(4096);

        System.out.println("server start");
        while (true){
            SocketChannel socketChannel = serverSocketChannel.accept();
            //返回来的socketchannel默认就是阻塞的，如果要注册到selector上，必须设置成非阻塞的
            socketChannel.configureBlocking(true);

            int readCount = 0;
            while (readCount != -1){
                try {
                    readCount = socketChannel.read(byteBuffer);
                }catch (Exception ex){
                    ex.printStackTrace();
                }

                //重新读
                byteBuffer.rewind();
            }
        }
    }
}
