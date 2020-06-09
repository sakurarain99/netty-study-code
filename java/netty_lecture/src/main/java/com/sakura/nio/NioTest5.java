package com.sakura.nio;

import java.nio.ByteBuffer;

/**
 * @description: ByteBuffer类型化的put与get方法
 * Buffer中put的是什么数据类型，get取的时候就需要以什么类型取，否则会因为数据类型所占据的字节大小不同读取的长度不同，多取或少取的情况，然后会导致数据读取错乱
 * @author: Mr.Miki
 * @create: 2020-05-30 16:01
 **/
public class NioTest5 {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        buffer.putInt(18);
        buffer.putDouble(5000000L);
        buffer.putChar('彩');
        buffer.putShort((short)2);
        buffer.putChar('虹');

        buffer.flip();

        System.out.println(buffer.getInt());
        System.out.println(buffer.getDouble());
        System.out.println(buffer.getChar());
        System.out.println(buffer.getShort());
        System.out.println(buffer.getChar());


    }
}
