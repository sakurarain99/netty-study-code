package com.test;

import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.security.SecureRandom;

/**
 * @description: nio 测试
 * @author: Mr.Miki
 * @create: 2020-05-30 09:48
 **/
public class NioTest {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        //要向外写出的字节数组
        byte[] messages = "hello world rainbow sea".getBytes();

        for (int i = 0; i < messages.length; i++) {
            byteBuffer.put(messages[i]);
        }
        boolean b = true;

        for (int i = 0; i < 5; i++) {
            if (i == 0){
                byteBuffer.flip();
            }else {
                byteBuffer.clear();
            }
            System.out.println(byteBuffer.limit());

            int index = 0;
            while (byteBuffer.hasRemaining()) {
                if(index == 10 && b){
                    b = false;
                    break;
                }
                System.out.print((char) byteBuffer.get());
                index++;
            }
            System.out.println("\t" + index);

            System.out.println("\n-------------------");

        }

    }

    @Test
    public void readWhenMarkOrReset(){
        IntBuffer intBuffer = IntBuffer.allocate(15);

        //1 2 3 4 5
        for (int i = 1; i < 10; i++) {
            if(i == 5){
                intBuffer.mark();
            }
            intBuffer.put(i);
        }

        System.out.println("no flip and before mark reset：");

        while (intBuffer.hasRemaining()) {
            //0*5
            System.out.print(intBuffer.get());
        }

        intBuffer.reset();
        System.out.println("\nno flip and after mark reset：");
        while (intBuffer.hasRemaining()) {
            //6 - 9
            System.out.print(intBuffer.get());
        }
    }

    @Test
    public void writeWhenMarkOrReset(){
        IntBuffer intBuffer = IntBuffer.allocate(15);

        //1 2 3 4 5
        for (int i = 1; i < 10; i++) {
            intBuffer.put(i);
        }
        intBuffer.flip();

        System.out.println("flip after before mark reset：");

        int beforeIndex = 1;

        while (intBuffer.hasRemaining()) {
            //1-9
            if(beforeIndex == 5){
                intBuffer.mark();
                break;
            }
            System.out.print(intBuffer.get());
            beforeIndex++;
        }

        intBuffer.reset();
        System.out.println("\nflip after mark reset：");
        while (intBuffer.hasRemaining()) {
            //5 - 9
            System.out.print(intBuffer.get());
        }
        System.out.println();
    }
}
