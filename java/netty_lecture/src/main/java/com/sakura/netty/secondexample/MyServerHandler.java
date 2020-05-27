package com.sakura.netty.secondexample;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

/**
 * @ClassName : MyServerHandler
 * @Description : 描述
 * SimpleChannelInboundHandler<?> 的泛型表示客户端发来的数据 真正的类型是什么
 * @Author : Mr.Sakura
 * @Date: 2020-03-07 16:25
 */
public class MyServerHandler extends SimpleChannelInboundHandler<String> {

    /**
     *
     * @param ctx 上下文，可以获取远程的信息，地址、连接对象
     * @param msg 客户端发来的请求对象
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "," + msg);

        /*
            write 写入缓冲
            flush 把缓冲的内容清/推出去
            writeAndFlush 写入缓冲，并把缓冲的内容推出去
         */
        //ctx.channel().writeAndFlush("from server：" + UUID.randomUUID());
    }

    /**
     * 出现异常的情况下怎么办
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }


}
