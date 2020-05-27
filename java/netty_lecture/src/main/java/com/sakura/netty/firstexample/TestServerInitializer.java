package com.sakura.netty.firstexample;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @ClassName : TestServerInitializer
 * @Description : 初始化器
 * 客户端与服务端一旦连接之后，TestServerInitializer就会被创建
 * initChannel() 方法就会被调用
 * @Author : Mr.Sakura
 * @Date: 2020-03-07 14:08
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {

    /**
     * 连接(Channel)一旦被注册之后该方法就会被调用
     * 回调方法
     */
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //一个管道，一个管道当中可以有多个ChannelHandler(拦截器),
        // 每个拦截器做的事情就是针对自己本身的请求或业务情况，完成相应的处理
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast("httpServerCodec",new HttpServerCodec());
        pipeline.addLast("testHttpServerHandler",new TestHttpServerHandler());
    }
}
