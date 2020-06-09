package com.sakura.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 *
 *
 * @author Mr.Miki
 * @date 2020-06-02 10:38
 * @since 1.0
 **/
public class NioTest12 {
    public static void main(String[] args)throws IOException {
        int[] ports = {5000, 5001, 5002, 5003, 5004};

        Selector selector = Selector.open();

        //将一个selector对象注册到系统的多个端口号上，使得一个selector监听多个端口
        for (int i = 0; i < ports.length; i++) {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            //configureBlocking()   配置是否阻塞 false：不阻塞 true：阻塞
            serverSocketChannel.configureBlocking(false);
            //获取到ServerSocket对象
            ServerSocket serverSocket = serverSocketChannel.socket();
            //创建端口绑定对象
            InetSocketAddress address = new InetSocketAddress(ports[i]);
            //端口绑定
            serverSocket.bind(address);
            /*
                将一个Channel注册到一个selector上，并返回selection key
                register(待注册的selector,对什么key感兴趣)

                SelectionKey.OP_ACCEPT   接受   SelectionKey.OP_CONNECT 连接
                SelectionKey.OP_READ    读   SelectionKey.OP_WRITE   写

                本程序register()的意思是：
                    将当前这个Channel注册到selector上面，并且注册的感兴趣的key是接受连接，
                    表示当有客户端向服务器端发起一个连接的时候，服务器端就会获取到这个连接，然后与之建立真正的连接
            */
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("监听端口：" + ports[i]);
        }

        while (true){
            //返回的是一个key的数量，可能是0
            //进行一个阻塞，等待感兴趣的事件被触发
            int numbers = selector.select();
            System.out.println("numbers = " + numbers);

            //通过SelectionKey不仅能获取事件的集合，还可以反过来获取到与之关联的(发送这个事件的Channel对象)
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            System.out.println("selectionKeys = " + selectionKeys);
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();
                //如果selectionKey是一个ACCEPT事件
                if(selectionKey.isAcceptable()){
                    ServerSocketChannel serverSocketChannel1 =
                            (ServerSocketChannel) selectionKey.channel();
                    //接受连接 服务端通过返回的SocketChannel有客户端进行交互
                    SocketChannel socketChannel = serverSocketChannel1.accept();
                    socketChannel.configureBlocking(false);
                    //这个表示真正连接的对象连接的Channel
                    socketChannel.register(selector,SelectionKey.OP_READ);
                    //将其从SelectionKey中移除，不移除则还会监听这个已经创建的连接
                    iterator.remove();
                    System.out.println("获得客户端连接：" + socketChannel);
                }else if(selectionKey.isReadable()){
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    int bytesRead = 0;

                    while (true){
                        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
                        byteBuffer.clear();

                        int read = socketChannel.read(byteBuffer);
                        if (read <= 0){
                            break;
                        }

                        byteBuffer.flip();

                        socketChannel.write(byteBuffer);

                        bytesRead += read;
                    }
                    System.out.println("读取：" + bytesRead + ", 来自于：" + socketChannel);

                    iterator.remove();
                }
            }
        }

    }
}
