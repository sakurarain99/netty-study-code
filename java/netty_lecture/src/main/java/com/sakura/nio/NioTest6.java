package com.sakura.nio;

import java.nio.ByteBuffer;

/**
 * 分片/分割Buffer，分割一个Buffer(A)指定起点和终点（就是指定position与limit，下标），
 * 然后调用Buffer的slice()方法会返回一个新的Buffer(B)的引用,这个新的Buffer的元素就是
 * 被分割Buffer(A)起点到终点之间的元素（包含起点不包含终点），这些元素可以被认为是老Buffer的快照
 * 新的Buffer与原有的Buffer的底层数据是一份，并不会重新拷贝一份，无论是对新的Buffer还是老的Buffer的起点到终点（包含起点不包含终点）
 * 范围内的元素进行修改都会反映到另外一个Buffer上/数据是同步的因为底层引用的都是同一个，但是两个Buffer的 position, limit, and mark
 * 都是互不相干独立的
 *
 * Slice Buffer 就相当于原有Buffer的快照，
 * Slice Buffer 与原有Buffer共享相同的底层数组(与原有的Buffer共同的底层数据就是一份)
 * 通过Slice Buffer或者原有Buffer对数据(起-终的数据，不包含终)进行修改都会反映到另一个Buffer上
 *
 * @author Mr.Miki
 * @date  2020-05-30 20:45
 **/
public class NioTest6 {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);

        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put((byte)i);
        }

        buffer.position(2);
        buffer.limit(6);

        ByteBuffer sliceBuffer = buffer.slice();

        buffer.clear();

        /*while (buffer.hasRemaining()) {
            System.out.println(buffer.get());
        }
        System.out.println("------------");*/


        for (int i = 0; i < sliceBuffer.capacity(); i++) {
            byte b = sliceBuffer.get(i);
            sliceBuffer.put(i,(b *= 2));
        }


        /*方式1
        buffer.position(0);
        buffer.limit(buffer.capacity());*/
        //方式2
        buffer.clear();

        while (buffer.hasRemaining()) {
            System.out.println(buffer.get());
        }

    }
}
