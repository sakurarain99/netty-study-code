package com.sakura.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @description: 获取一个文件的内容，然后写到另一个文件中
 * @author: Mr.Miki
 * @create: 2020-05-30 11:53
 **/
public class NioTest4 {
    public static void main(String[] args) throws IOException {
        FileInputStream inputStream = new FileInputStream("input.txt");
        FileOutputStream outputStream = new FileOutputStream("output.txt");

        FileChannel inputChannel = inputStream.getChannel();
        FileChannel outputChannel = outputStream.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(512);

        while (true){
            //如果这行被删掉，将会出现，position = limit 没有数据可读问题  while永远不会跳出
            //buffer.clear();

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
