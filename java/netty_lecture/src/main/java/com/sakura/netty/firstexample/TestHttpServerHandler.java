package com.sakura.netty.firstexample;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * @ClassName : TestHttpServerHandler
 * @Description : 自定义处理器
 * @Author : Mr.Sakura
 * @Date: 2020-03-07 14:19
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    /**
     * channelRead0 读取客户端发过来的请求，并且向客户端返回响应的方法
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {

        System.out.println(msg.getClass());

        System.out.println(ctx.channel().remoteAddress());
        Thread.sleep(8000);
        if(msg instanceof HttpRequest){

            System.out.println("请求处理");
            //向客户端返回的响应内容
            ByteBuf content =
                    Unpooled.copiedBuffer("Hello World", CharsetUtil.UTF_8);
            //构建一个响应
            //DefaultFullHttpResponse(http协议的版本,返回的状态码,响应内容);
            FullHttpResponse response = new DefaultFullHttpResponse(
                    HttpVersion.HTTP_1_1, HttpResponseStatus.OK,content);
            //设置response相应的头信息
            response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH,content.readableBytes());

            //返回客户端
            ctx.writeAndFlush(response);
            //手动关闭连接
            ctx.channel().close();
        }
    }


    /**通道注册时调用*/
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel registered");
        super.channelRegistered(ctx);
    }

    /**通道活跃时调用*/
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel active");
        super.channelActive(ctx);
    }

    /**处理程序已添加时调用*/
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("handler added");
        super.handlerAdded(ctx);
    }
    /**通道停止活跃时调用*/
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel inactive");
        super.channelInactive(ctx);
    }

    /**通道取消注册时调用*/
    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channel unregistered");
        super.channelUnregistered(ctx);
    }

    /**连接断开之后*/
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        super.handlerRemoved(ctx);
    }
}
