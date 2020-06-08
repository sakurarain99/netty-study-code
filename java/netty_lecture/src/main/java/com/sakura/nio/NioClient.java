package com.sakura.nio;

import io.netty.util.concurrent.DefaultThreadFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.*;

/**
 * 多人聊天室，client
 *
 * @author Mr.Miki
 * @date 2020-06-03 09:28
 * @since 1.0
 **/
public class NioClient {
    public static void main(String[] args) {
        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);

            Selector selector = Selector.open();
            socketChannel.connect(new InetSocketAddress("localhost", 8899));
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
            while (true) {
                selector.select();

                Set<SelectionKey> keySet = selector.selectedKeys();
                for (SelectionKey selectionKey : keySet) {
                    if (selectionKey.isConnectable()) {
                        SocketChannel client = (SocketChannel) selectionKey.channel();
                        //指示此通道上是否正在进行连接操作。如果是则可以进行连接成功标示
                        if (client.isConnectionPending()) {
                            //表示连接建立成功，完成连接
                            client.finishConnect();

                            ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                            writeBuffer.put((LocalDateTime.now() + " 连接成功").getBytes());
                            writeBuffer.flip();
                            //向服务器端写入数据
                            client.write(writeBuffer);
                            //获取一个线程池

                            //不推荐被使用
                            //ExecutorService executorService = Executors.newSingleThreadExecutor(Executors.defaultThreadFactory());
                            //及建议使用下面的


                            /*
                                corePoolSize - 线程池核心池的大小。
                                maximumPoolSize - 线程池的最大线程数。
                                keepAliveTime - 当线程数大于核心时，此为终止前多余的空闲线程等待新任务的最长时间。
                                unit - keepAliveTime 的时间单位。
                                workQueue - 用来储存等待执行任务的队列。
                                threadFactory - 线程工厂。
                                handler - 拒绝策略。
                            */
                            ExecutorService executorService = new ThreadPoolExecutor(10, 100, 2000,
                                    TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(1024), Executors.defaultThreadFactory());

                            executorService.submit(() -> {
                                while (true) {
                                    try {
                                        //清除Buffer
                                        writeBuffer.clear();
                                        InputStreamReader in = new InputStreamReader(System.in);
                                        BufferedReader bufferedReader = new BufferedReader(in);

                                        String sendMessage = bufferedReader.readLine();

                                        writeBuffer.put(sendMessage.getBytes());
                                        writeBuffer.flip();

                                        client.write(writeBuffer);
                                    } catch (Exception ex) {
                                        ex.printStackTrace();
                                    }
                                }
                            });
                            client.register(selector, SelectionKey.OP_READ);
                        }
                    } else if (selectionKey.isReadable()) {
                        SocketChannel serverWrite = (SocketChannel) selectionKey.channel();

                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        int read = serverWrite.read(byteBuffer);
                        if (read > 0) {
                            byteBuffer.flip();

                            Charset charset = Charset.forName("UTF-8");
                            String receivedMessage = String.valueOf(charset.decode(byteBuffer).array()).replace("\u0000", "");

                            System.out.println(receivedMessage);
                        }
                    }
                }
                //清除selectedKeys集合
                keySet.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
