package com.sakura.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * 文件锁，可以锁一个文件的整体或部分
 * 锁有两种：
 *  1.共享锁   都可以读
 *  2.排查锁   只能写 只有一个程序来进行写，读多个程序都可以对锁住的部分进行读
 * @author Mr.Miki
 * @date 2020-06-01 11:56
 * @since 1.0
 **/
public class NioTest10 {
    public static void main(String[] args) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("NioTest10.txt", "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();

        /*
            lock(从那个位置开始锁,锁的长度,true表示共享锁false表示排查锁)
         */
        FileLock fileLock = fileChannel.lock(3,6,true);

        //isValid()查看是否有效
        System.out.println("valid：" + fileLock.isValid());
        //查看锁是否是共享锁
        System.out.println("lock type：" + fileLock.isShared());

        fileLock.release();
        randomAccessFile.close();
    }
}
