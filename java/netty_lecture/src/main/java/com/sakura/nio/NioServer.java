package com.sakura.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.*;

/**
 * 聊天室功能，Nio server 用一个通道，服务器端只会有一个线程
 *
 * @author Mr.Miki
 * @date 2020-06-02 20:22
 * @since 1.0
 **/
public class NioServer {

    /**
     * 保存客户端的集合
     */
    private static Map<String, SocketChannel> clientMap = new HashMap<>();

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //设置为非阻塞的
        serverSocketChannel.configureBlocking(false);
        ServerSocket socket = serverSocketChannel.socket();
        //绑定端口号
        socket.bind(new InetSocketAddress(8899));
        //获取选择器
        Selector selector = Selector.open();
        //注册ServerSocketChannel到selector中，并设置感兴趣的事件为 连接接受时触发
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            try {
                //阻塞，等待感兴趣的事件触发
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();

                selectionKeys.forEach(selectionKey -> {
                    final SocketChannel client;

                    try {
                        //判断是否是Accep是事件
                        if (selectionKey.isAcceptable()) {
                            //这里向下类型转换为ServerSocketChannel是因为36行注册到Accep事件的Channel就是这个类型的
                            //当前selector只有一个Channel并且感兴趣的事件还是Accept，所以可以断定就是36注册的ServerSocketChannel
                            ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
                            client = server.accept();
                            client.configureBlocking(false);
                            //注册一个SocketChannel到selector中，感兴趣的事件为读事件
                            client.register(selector,SelectionKey.OP_READ);

                            String key = "[" + UUID.randomUUID().toString() + "]";
                            clientMap.put(key,client);
                        }else if(selectionKey.isReadable()){
                            //这个类型转换和上面的一样，都是确定触发这个事件的Channel类型
                            client = (SocketChannel) selectionKey.channel();
                            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

                            //将Channel对象中的数据写入Buffer中
                            int count = client.read(byteBuffer);
                            if (count > 0){
                                //反转Buffer
                                byteBuffer.flip();

                                Charset charset = Charset.forName("UTF-8");
                                //通过Charset.decode()解码ByteBuffer并调用array()返回一个char[]数组，String可通过valueOf()
                                //将char[]数组转换为一个String
                                String receivedMessage = String.valueOf(charset.decode(byteBuffer).array()).replace("\u0000","");

                                System.out.println(client + "：" + receivedMessage);

                                //发送者在clientMap中的key，也就是生成的uuid
                                String senderKey = null;
                                //循环遍历clientMap通过value得到发送者的key
                                for (Map.Entry<String,SocketChannel> entry : clientMap.entrySet()){
                                    if(entry.getValue() == client){
                                        senderKey = entry.getKey();
                                        break;
                                    }
                                }

                                for (Map.Entry<String, SocketChannel> entry : clientMap.entrySet()) {
                                    SocketChannel value = entry.getValue();

                                    ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                                    //将数据读入Buffer
                                    writeBuffer.put((senderKey + "：" + receivedMessage).getBytes());
                                    writeBuffer.flip();
                                    //向客户端写入数据
                                    value.write(writeBuffer);
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                //清空selectionKeys，以免出现已经处理过的selectionKey对象还存在与感兴趣的selectionKeys集合中
                selectionKeys.clear();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
