package com.sakura.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @description: 传统io 切换到Nio   使用FileInputStream流关联要读取的文件，
 * 然后使用nio的方式将文件的内容读取到程序当中
 * @author: Mr.Miki
 * @create: 2020-05-29 20:27
 **/
public class NioTest2 {
    public static void main(String[] args) throws IOException, InterruptedException {
        FileInputStream fileInputStream =
                new FileInputStream("NioTest2.txt");
        //getChannel()  不是InputStream中的方法 子类新增的
        FileChannel channel = fileInputStream.getChannel();

        //创建一个长度为512字节的ByteBuffer对象，实际512是底层数组的大小
        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        //将channel对象中的数据读到ByteBuffer中，写到ByteBuffer中
        channel.read(byteBuffer);

        //操作的反转
        byteBuffer.flip();


        //hasRemaining()  是否还有值 底层就是判断当前下表是否小于最大范围 position < limit
        //remaining()   返回limit - position的值 大于0就说明还有值
        while (byteBuffer.remaining() > 0){
            System.out.println("Character：" + (char) byteBuffer.get());
        }


        channel.close();
    }
}
