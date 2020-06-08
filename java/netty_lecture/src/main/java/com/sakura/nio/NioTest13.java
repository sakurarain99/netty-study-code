package com.sakura.nio;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Arrays;

/*
    作业：
        1.为什么这个程序使用ISO-8859-1的编码方式还可以实现中文的正确输出，中文却不会乱码？
            因为本程序中使用ISO-8859-1的解码与编码的过程中并没有更改原字节的值以及多字节的配对(因为ISO-8859-1就只支持一个字节表示一个字符，
            并不会组合字符)，程序中charBuffer中乱码的原因是因为Charset的编码方式是ISO-8859-1在生成CharBuffer时，是以一个字节一个字符的规则
            进行解析/转换的。然而中文在UTF-8中可能是以3个字节或更多字节表示所以在解析本应该以三个字节组合一起的字节进行了逐个单一的解析，然后将每
            一个字节的十六进制数对应在ISO-8859-1编码表上的字符，逐一赋给了charBuffer所以charBuffer的中文部分是乱码的。
            当程序将ByteBuffer的内容写入到目标文件中后，因为idea默认文件使用的就是UTF-8所以在解析程序写到目标文件的字节码时是使用UTF-8的规则
            进行的解析(与原文件解析规则相同)，则不会出现乱码。

            如果使用解析规则大于一个字节或者中途会改变字节配对的编码方式则会出现乱码。



        2.为什么平时使用iso-8859-1就会出现乱码？
        3.深入理解utf-8 utf-16 utf-32 Unicode 到底意味着什么？ 它们都是什么？ 它们之间的关系又是什么？
            理解 UTF-16BE(大端) UTF-16LE(小端) 它们代表着什么？ 它们在编解码的时候是怎么执行的？ 执行逻辑是什么样的？
        了解iso-8859-1的时候了解它与ASCII码的关系
        了解utf的时候 Unicode转换格式的缩写 Unicode的一种处理方式，可以认为Unicode是一种编码方式 utf是一种存储方式
        utf-8是Unicode的实现方式之一
        utf-8存储信息的时候采用的是变长的处理方式，变长的规则是什么？
 */

/**
 * Java编解码问题，将一个文件的内容拷贝到另一个文件
 *
 * @author Mr.Miki
 * @date 2020-06-03 11:01
 * @since 1.0
 **/
public class NioTest13 {
    public static void main(String[] args) throws Exception {
        String inputFile = "NioTest13_In.txt";
        String outputFile = "NioTest13_Out.txt";

        //RandomAccessFile(文件位置,可进行什么操作) 随机存取文件
        //"r" 可进行读操作 "w" 可进行写操作 "rw" 可进行读写操作
        RandomAccessFile inputRandomAccessFile = new RandomAccessFile(inputFile, "r");
        RandomAccessFile outputRandomAccessFile = new RandomAccessFile(outputFile, "rw");

        long inputLength = new File(inputFile).length();

        FileChannel inputFileChannel = inputRandomAccessFile.getChannel();
        FileChannel outputFileChannel = outputRandomAccessFile.getChannel();

        /*
            内存映射文件
            map(映射模式,映射的起始位置,映射多少位)
                映射模式：表示是读模式、写模式还是读写模式
                FileChannel.MapMode.READ_WRITE  能读能写
                FileChannel.MapMode.READ_ONLY  只读
                FileChannel.MapMode.PRIVATE  专用（写时复制）映射的模式
         */
        MappedByteBuffer inputData = inputFileChannel.map(FileChannel.MapMode.READ_ONLY, 0, inputLength);

        System.out.println("----------------------");

        //输出系统的所有编码方式
        Charset.availableCharsets().forEach((k,v) -> {
            System.out.println(k + "  ,  " + v);
        });

        System.out.println("----------------------");

        //创建一个字符集对象
        Charset charset = Charset.forName("ISO-8859-1");
        //解码器 将字节数组转换为字符串
        CharsetDecoder decoder = charset.newDecoder();
        //编码器 将字符串转换为字节数组
        CharsetEncoder encoder = charset.newEncoder();

        CharBuffer charBuffer = decoder.decode(inputData);
        ByteBuffer byteBuffer = encoder.encode(charBuffer);

        //文件写入
        outputFileChannel.write(byteBuffer);

        inputFileChannel.close();
        outputFileChannel.close();
    }
}
