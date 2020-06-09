package com.sakura.nio;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 内存映射文件，修改一个文件
 *
 * @author Mr.Miki
 * @date 2020-06-01 09:50
 * @since 1.0
 **/
public class NioTest9 {
    public static void main(String[] args) throws IOException {
        //RandomAccessFile(文件的名字,读写状态(rw表示能读能写))
        RandomAccessFile randomAccessFile = new RandomAccessFile("NioTest9.txt", "rw");
        FileChannel fileChannel = randomAccessFile.getChannel();

        /*
            map(映射模式,映射的起始位置,映射多少位)
                映射模式：表示是读模式、写模式还是读写模式
                FileChannel.MapMode.READ_WRITE  能读能写
                FileChannel.MapMode.READ_ONLY  只读
                FileChannel.MapMode.PRIVATE  专用（写时复制）映射的模式
         */
        MappedByteBuffer mappedByteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE,0,5);

        //修改文件
        mappedByteBuffer.put(0,(byte)'1');
        mappedByteBuffer.put(3,(byte)'3');

        randomAccessFile.close();
    }
}
