package com.sakura.netty.sixtheample;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

/**
 * @ClassName : TestServerInitializer
 * @Description : 初始化器
 * @Author : Mr.Sakura
 * @Date: 2020-03-16 15:48
 */
public class TestServerInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        //用于decode前解决半包和粘包问题（利用包头中的包含数组长度来识别半包粘包）
        pipeline.addLast(new ProtobufVarint32FrameDecoder());
        /*
            ProtobufDecoder 是解码器，将protobuf构造出来的字节数组，转化成真正的对象
            MessageLite(参数)表示要转换的类的实例

            反序列化指定的Probuf字节数组为protobuf类型。
         */
        pipeline.addLast(new ProtobufDecoder(DataInfo.Message.getDefaultInstance()));
        //用于在序列化的字节数组前加上一个简单的包头，只包含序列化的字节长度。
        pipeline.addLast(new ProtobufVarint32LengthFieldPrepender());
        //用于对Probuf类型序列化。
        pipeline.addLast(new ProtobufEncoder());
        pipeline.addLast(new TestServerHandler());
    }
}
