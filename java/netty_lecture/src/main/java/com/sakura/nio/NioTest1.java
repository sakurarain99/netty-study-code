package com.sakura.nio;

import java.nio.IntBuffer;
import java.security.SecureRandom;

/**
 * @description: nio第一个程序  生成随机数并put添加到Buffer中  然后再get获取打印
 * @author: Mr.Miki
 * @create: 2020-05-29 15:52
 **/
public class NioTest1 {
    public static void main(String[] args) {
        //beforeFixing();
        afterMdification();
    }

    private static void beforeFixing() {
        //获取一个int类型的Buffer  allocate(10)    设置最大存储容量为10
        IntBuffer intBuffer = IntBuffer.allocate(10);

        //IntBuffer.capacity()  获取最大存储容量
        for (int i = 0; i < intBuffer.capacity(); i++) {
            //SecureRandom()  生成一个更随机的随机数
            //nextInt(20)   int类型的 范围是0-20之间
            int randomNumber = new SecureRandom().nextInt(20);
            //往IntBuffer中put值，并将position自增
            intBuffer.put(randomNumber);
        }

        //反转此缓冲区  将最大存储容量(limit)设置为当前下表值，将当前下标(position)重置为0
        intBuffer.flip();

        //下面是否还有内容
        while (intBuffer.hasRemaining()){
            System.out.println(intBuffer.get());
        }
    }
    private static void afterMdification() {
        IntBuffer intBuffer = IntBuffer.allocate(10);

        System.out.println("capacity：" + intBuffer.capacity());

        for (int i = 0; i < 5; i++) {
            int randomNumber = new SecureRandom().nextInt(20);
            intBuffer.put(randomNumber);
        }
        //10
        System.out.println("before limit：" + intBuffer.limit());

        intBuffer.flip();

        //5
        System.out.println("after limit：" + intBuffer.limit());

        System.out.println("enter while loop");
        while (intBuffer.hasRemaining()){
            //0 - 4
            System.out.println("position：" + intBuffer.position());
            //5
            System.out.println("limit：" + intBuffer.limit());
            //10
            System.out.println("capacity：" + intBuffer.capacity());

            System.out.println(intBuffer.get());
        }
    }
}
