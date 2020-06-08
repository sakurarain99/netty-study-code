package com.sakura.nio;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @description: 通过普通io读取文件内容，然后转换为nio再写入/输出到某个文件中
 * @author: Mr.Miki
 * @create: 2020-05-29 20:45
 **/
public class NioTest3 {
    public static void main(String[] args) throws IOException {
        FileOutputStream fileOutputStream =
                new FileOutputStream("NioTest3.txt");
        FileChannel fileChannel = fileOutputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        //要向外写出的字节数组
        byte[] messages = "hello world rainbow sea".getBytes();

        for (int i = 0; i < messages.length; i++) {
            byteBuffer.put(messages[i]);
        }

        byteBuffer.flip();
        //将数据写入到文件中
        fileChannel.write(byteBuffer);

        fileChannel.close();
    }
}
