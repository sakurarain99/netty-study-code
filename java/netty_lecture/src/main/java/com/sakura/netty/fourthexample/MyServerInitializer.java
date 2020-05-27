package com.sakura.netty.fourthexample;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName : MyServerInitializer
 * @Description : 描述
 * @Author : Mr.Sakura
 * @Date: 2020-03-12 16:04
 */
public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        /*
            IdleStateHandler 对空闲检测提供的handler/处理器   检测的是服务器端
            说明：当某个Channel在一定的时间，间隔之内没有进行读、写或读和写的操作时
            就会出发IdleStateEvent事件

            IdleStateHandler(读的时间,写的时间,读写的时间)　默认秒
                读写的时间　读和写空闲触发一个就会触发这个事件
                例：IdleStateHandler(10,10,10)  如果在10秒之内都没有进行读操作，就会触发一个事件，后面两个一样
            IdleStateHandler(读的时间,写的时间,读和写的时间,时间单位<不指定默认是秒>)
         */
        pipeline.addLast(new IdleStateHandler(5,7,3, TimeUnit.SECONDS));
        pipeline.addLast(new MyServerHandler());
    }
}
