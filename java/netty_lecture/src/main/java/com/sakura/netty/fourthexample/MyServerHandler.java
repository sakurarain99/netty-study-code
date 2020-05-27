package com.sakura.netty.fourthexample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @ClassName : MyServerHandler
 * @Description : 描述
 * @Author : Mr.Sakura
 * @Date: 2020-03-12 16:39
 */
public class MyServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * 触发某个事件之后就会被调用
     * 它会调研ChannelHandlerContext的fireUserEventTriggered(Object)方法
     * 来将事件转发给ChannelPipeline管道的下一个ChannelInboundHandler对象
     * @param ctx 上下文对象
     * @param evt 事件对象
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        //如果这个事件本身是一个IdleStateEvent类型　表示他是一个空闲状态事件
        if(evt instanceof IdleStateEvent){
            //强制类型转换一下
            IdleStateEvent event= (IdleStateEvent)evt;
            String eventType = null;
            switch (event.state()){
                case READER_IDLE:
                    eventType = "读空闲";
                    break;
                case WRITER_IDLE:
                    eventType = "写空闲";
                    break;
                case ALL_IDLE:
                    eventType = "读写空闲";
                    break;
                default:
                    break;
            }
            System.out.println(ctx.channel().remoteAddress() + "超时事件：" + eventType);
            ctx.channel().close();
        }
    }
}
