package com.sakura.netty.thirdexample;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @ClassName : MyChatClient
 * @Description : 客户端
 * @Author : Mr.Sakura
 * @Date: 2020-03-07 18:41
 */
public class MyChatClient {
    public static void main(String[] args) {

        //事件循环组,只有一个循环组
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
                    .handler(new MyChatClientInitializer());
            //与对应的url建立连接通道  .channel 拿到对应的通道对象
            //可以直接与连接该通道的服务交互
            Channel channel = bootstrap.connect("localhost", 8899).sync().channel();

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            for (; ;){
                //readLine每次读取一行
                //读取一行数据，回车即读取
                channel.writeAndFlush(br.readLine() + "\r\n");
            }

        }catch (Exception e){
            System.out.println("异常");
            System.out.println(e.getMessage());
        }finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
}
