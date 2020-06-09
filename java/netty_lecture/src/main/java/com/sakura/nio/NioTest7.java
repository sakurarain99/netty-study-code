package com.sakura.nio;

import java.nio.ByteBuffer;

/**
 * 只读Buffer，我们可以随时将一个普通Buffer(可读写Buffer)调用asReadOnlyBuffer()方法返回一个只读Buffer
 * 但是不能将一个只读Buffer转换为读写Buffer
 * @author Mr.Miki
 * @date 2020-05-30 22:24
 * @since 1.0
 **/
public class NioTest7 {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);

        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put((byte)i);
        }

        //返回一个只读Buffer，不允许写入
        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();

        //查看Buffer的类型
        System.out.println(buffer.getClass());
        System.out.println(readOnlyBuffer.getClass());

        readOnlyBuffer.position(0);
        //readOnlyBuffer.put((byte)2);
    }
}
