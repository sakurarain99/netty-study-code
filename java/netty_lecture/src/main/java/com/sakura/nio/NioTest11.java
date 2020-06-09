package com.sakura.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * 关于Buffer的Scattering(散开，分散成多个)与Gathering(收集，将多个汇成一个)
 * Scattering将来自于Channel的内容读到多个Buffer中，只有前面的Buffer读满了并且Channel中还有数据，
 *  那么他就会将数据读到下一个Buffer中，以此类推
 * Gathering写的时候从第一个开始，将Buffer数组中的Buffer一、个一个的写
 * @author Mr.Miki
 * @date 2020-06-01 14:52
 * @since 1.0
 **/
public class NioTest11 {

    public static void main(String[] args)throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress address = new InetSocketAddress(8899);
        serverSocketChannel.socket().bind(address);

        int messageLength = 2 + 3 + 4;

        ByteBuffer[] buffers = new ByteBuffer[3];
        buffers[0] = ByteBuffer.allocate(2);
        buffers[1] = ByteBuffer.allocate(3);
        buffers[2] = ByteBuffer.allocate(4);

        SocketChannel socketChannel = serverSocketChannel.accept();

        while (true){
            int bytesRead = 0;
            while (bytesRead < messageLength){
                long r = socketChannel.read(buffers);
                bytesRead += r;

                System.out.println("bytesRead：" + bytesRead);
                Arrays.asList(buffers).stream()
                        .map(buffer -> "position：" + buffer.position() + ", limit：" + buffer.limit())
                        .forEach(System.out::println);
            }

            Arrays.asList(buffers).forEach(buffer ->{
                buffer.flip();
            });

            long bytesWritten = 0;
            while (bytesWritten < messageLength){
                long w = socketChannel.write(buffers);
                bytesWritten += w;
            }

            Arrays.asList(buffers).forEach(buffer -> {
                buffer.clear();
            });

            System.out.println("byteRead：" + bytesRead + ", bytesWritten：" + bytesWritten +
                    ", messageLength：" + messageLength);
        }

    }
}
