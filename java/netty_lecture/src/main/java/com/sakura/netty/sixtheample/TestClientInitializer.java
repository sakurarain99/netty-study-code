package com.sakura.netty.sixtheample;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

/**
 * @ClassName : TestClientInitializer
 * @Description : 初始化器
 * @Author : Mr.Sakura
 * @Date: 2020-03-16 16:02
 */
public class TestClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new ProtobufVarint32FrameDecoder());
        /*
            ProtobufDecoder 是解码器，将protobuf构造出来的字节数组，转化成真正的对象
            MessageLite(参数)表示要转换的类的实例
         */
        pipeline.addLast(new ProtobufDecoder(DataInfo.Message.getDefaultInstance()));
        pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
        pipeline.addLast(new ProtobufEncoder());
        pipeline.addLast(new TestClientHandler());
    }
}
