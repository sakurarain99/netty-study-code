package com.sakura.netty.thirdexample;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * @ClassName : MyChatClientHandler
 * @Description : 这里的泛型是String，说明这个传输的是个String对象
 * @Author : Mr.Sakura
 * @Date: 2020-03-07 18:43
 */
public class MyChatClientHandler extends SimpleChannelInboundHandler<String> {

    /**
     *
     * @param ctx 上下文请求对象
     * @param msg 表示服务端发来的消息
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        System.out.println(msg);
    }
}
