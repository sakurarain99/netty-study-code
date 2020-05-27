package com.sakura.netty.secondexample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.time.LocalDateTime;

/**
 * @ClassName : MyClientHandler
 * @Description : 客户端处理器
 * @Author : Mr.Sakura
 * @Date: 2020-03-07 17:06
 */
public class MyClientHandler extends SimpleChannelInboundHandler<String> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        //打印远程地址
        System.out.println(ctx.channel().remoteAddress());

        System.out.println("client output：" + msg);
        ctx.writeAndFlush("from client：" + LocalDateTime.now());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("来自于客户端的问候");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
