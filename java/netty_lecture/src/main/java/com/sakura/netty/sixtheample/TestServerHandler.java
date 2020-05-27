package com.sakura.netty.sixtheample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @ClassName : TestServerHandler
 * @Description : 服务端处理器
 * @Author : Mr.Sakura
 * @Date: 2020-03-16 15:51
 */
public class TestServerHandler extends SimpleChannelInboundHandler<DataInfo.Message> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DataInfo.Message msg) throws Exception {
        DataInfo.Message.DataType dataType = msg.getDataType();
        switch (dataType){
            case PersonType:
                DataInfo.Person person = msg.getPerson();
                System.out.println(person.getName());
                System.out.println(person.getAge());
                System.out.println(person.getAddress());
                break;
            case DogType:
                DataInfo.Dog dog = msg.getDog();
                System.out.println(dog.getName());
                System.out.println(dog.getAge());
                break;
            case CatType:
                DataInfo.Cat cat = msg.getCat();
                System.out.println(cat.getName());
                System.out.println(cat.getCity());
                break;
            default:
                break;
        }
    }
}
