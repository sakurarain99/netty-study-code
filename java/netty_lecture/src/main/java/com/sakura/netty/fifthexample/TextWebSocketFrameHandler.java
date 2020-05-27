package com.sakura.netty.fifthexample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.time.LocalDateTime;

/**
 * @ClassName : TextWebSocketFrameHandler
 * @Description : 用于处理文本的数据传送
 * 泛型是TextWebSocketFrame专门用于处理文本帧的类型的类
 * @Author : Mr.Sakura
 * @Date: 2020-03-13 15:01
 */
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    /**
     * @param ctx 上下文对象
     * @param msg 客户端发送来的消息的类型
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        System.out.println("收到消息：" + msg.text());
        //这里不能直接传输一个字符串，handler没法处理
        ctx.channel().writeAndFlush(new TextWebSocketFrame("服务器时间：" + LocalDateTime.now()));
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        /*每一个channel都会有一个全局唯一的id  通过channel.id()获取
            会返回一个channelId，里面有两个属性
            asLongText表示整个id，全局唯一的
            asShortText表示id的简写，全局不唯一的
         */
        System.out.println("handlerAdded：" + ctx.channel().id().asLongText());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handlerRemoved：" + ctx.channel().id().asLongText());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常发生");
        ctx.close();
    }
}
