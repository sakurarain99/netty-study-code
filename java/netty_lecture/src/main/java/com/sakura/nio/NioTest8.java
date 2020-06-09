package com.sakura.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 获取一个文件的内容，然后写到另一个文件中 使用 DirectByteBuffer 测试
 *
 * @author Mr.Miki
 * @date 2020-06-01 08:22
 * @since 1.0
 **/
public class NioTest8 {
    public static void main(String[] args) throws IOException {
        FileInputStream inputStream = new FileInputStream("input.txt");
        FileOutputStream outputStream = new FileOutputStream("output2.txt");

        FileChannel inputChannel = inputStream.getChannel();
        FileChannel outputChannel = outputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocateDirect(512);

        while (true){
            //如果这行被删掉，将会出现，position = limit 没有数据可读问题  while永远不会跳出
            buffer.clear();

            int read = inputChannel.read(buffer);

            System.out.println(read);

            if(read == -1){
                break;
            }
            buffer.flip();

            outputChannel.write(buffer);
        }

        inputChannel.close();
        outputChannel.close();
    }
}
