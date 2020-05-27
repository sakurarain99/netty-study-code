package com.sakura.netty.thirdexample;

import com.sakura.netty.secondexample.MyServerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @ClassName : MyChatServer
 * @Description : 聊天室 多个客户端连接服务端,可以相互发送消息 并且有上下线通知
 * @Author : Mr.Sakura
 * @Date: 2020-03-07 17:36
 */
public class MyChatServer {
    public static void main(String[] args){
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new MyChatServerInitializer());

            ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
            channelFuture.channel().closeFuture().sync();
        }catch (Exception e){
            System.out.println("服务端异常");
            System.out.println(e.getMessage());
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
