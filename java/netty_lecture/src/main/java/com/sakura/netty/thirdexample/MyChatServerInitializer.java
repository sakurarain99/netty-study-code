package com.sakura.netty.thirdexample;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * @ClassName : MyChatServerInitializer
 * @Description : 初始化器
 * @Author : Mr.Sakura
 * @Date: 2020-03-07 17:37
 */
public class MyChatServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        /*
            DelimiterBasedFrameDecoder 解码器  根据分隔符进行解析
            StringDecoder 字符串解码器
            StringEncoder 字符串编码器
         */
        pipeline.addLast(new DelimiterBasedFrameDecoder(4096, Delimiters.lineDelimiter()));
        //虽然默认StringDecoder/StringEncoder默认就是UTF_8，但是最好还是写上
        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
        pipeline.addLast(new MyChatServerHandler());
    }
}
