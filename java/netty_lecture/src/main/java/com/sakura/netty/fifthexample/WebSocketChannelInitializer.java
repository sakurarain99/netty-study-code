package com.sakura.netty.fifthexample;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @ClassName : WebSocketChannelInitializer
 * @Description : 描述
 * @Author : Mr.Sakura
 * @Date: 2020-03-12 17:41
 */
public class WebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        /*
            HttpServerCodec http编解码器
            ChunkedWriteHandler 以块的方式写
            HttpObjectAggregator 通道处理器，会对一个http消息进行聚合
                会将HttpMessage和HttpContent等等... 聚合到一个FullHttpRequest或者FullHttpResponse，这个取决于它是处理请求的还是响应的
                后面就不会有HttpContent了
            HttpObjectAggregator(maxContentLength) 构造器
                maxContentLength以字节的方式来聚合内容的最大长度，如果聚合的长度超过了maxContentLength最大长度
                就会调用handleOversizedMessage方法
            WebSocketServerProtocolHandler WebSocket服务器协议，他负责WebSocket的握手
                以及控制帧的处理(关闭，心跳)，文本和二进制数据帧将传递到管道中的下一个处理程序（由您实现）以进行处理

         */
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new ChunkedWriteHandler());
        pipeline.addLast(new HttpObjectAggregator(8192));
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));
        pipeline.addLast(new TextWebSocketFrameHandler());
    }
}
