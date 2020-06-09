package com.sakura.netty.secondexample;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.HashMap;

/**
 * @ClassName : MyServer
 * @Description : 服务器端
 * @Author : Mr.Sakura
 * @Date: 2020-03-07 16:15
 */
public class MyServer {
    public static void main(String[] args) throws Exception {
        /*
            NioEventLoopGroup(1) 如果创建的时候传入一个int值，那么它将使用这个int值个线程
            不设置构造参数的话使用默认个线程(如果没有设置"io.netty.eventLoopThreads"系统属性那么他就会使用系统核心数*2的核心数)

            为什么很多人设置为1，因为它是异步的只需要一个线程来不断的监听事件循环，当事件发生的时候获取到事件循环本身，
                然后将事件相应的处理工作丢给workerGroup

            NioEventLoopGroup并没有做什么处理只是一些准备工作/变量的赋值

            Loop的上一组都叫做Executor
        */
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            /*
                所有的Handler() 都是针对bossGroup发挥作用
                所有的childHandler() 都是针对workerGroup发挥作用，连接丢给workerGroup
                    之后由childHandler对象对workerGroup这个Nio线程发挥作用
             */
            serverBootstrap.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new MyServerInitializer());
            ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
            channelFuture.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
