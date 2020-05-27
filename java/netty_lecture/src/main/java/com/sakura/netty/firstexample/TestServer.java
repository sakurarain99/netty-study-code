package com.sakura.netty.firstexample;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @ClassName : TestServer
 * @Description : 服务器启动
 * @Author : Mr.Sakura
 * @Date: 2020-03-07 13:52
 */
public class TestServer {
    public static void main(String[] args) throws Exception {
        /*
                定义两个事件循环组
                NioEventLoopGroup 就是一个死循环，不断接受客户端发起的连接并处理，和tomcat这种服务器一样
                bossGroup 用于接受客户端的连接但是不做处理，会把连接转给workerGroup
                workerGroup 会对连接进行处理，进行对应的业务处理，最后把结果返回给客户端
            */
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //ServerBootstrap用于启动服务端
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            /*
                group() 事件循环组
                channel() 用到的管道
                childHandler() 子处理器，自己编写的处理器，
                    请求到来之后由我们自己编写的处理器进行真正的处理
            */
            serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new TestServerInitializer());
            //绑定端口
            ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
            //关闭的监听
            channelFuture.channel().closeFuture().sync();
        }finally {
            //关闭
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
