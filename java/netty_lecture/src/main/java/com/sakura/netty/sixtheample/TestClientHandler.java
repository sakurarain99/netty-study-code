package com.sakura.netty.sixtheample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;

/**
 * @ClassName : TestClientHandler
 * @Description : 处理器
 * @Author : Mr.Sakura
 * @Date: 2020-03-16 16:04
 */
public class TestClientHandler extends SimpleChannelInboundHandler<DataInfo.Message> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DataInfo.Message msg) throws Exception {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        int randomInt = new Random().nextInt(3);
        DataInfo.Message message = null;
        if (randomInt == 0){
            DataInfo.Person person = DataInfo.Person.newBuilder()
                    .setName("星空").setAge(18).setAddress("宇宙").build();
            message = DataInfo.Message.newBuilder()
                    .setDataType(DataInfo.Message.DataType.PersonType).setPerson(person).build();
        }else if(randomInt == 1){
            message = DataInfo.Message.newBuilder().setDataType(DataInfo.Message.DataType.DogType)
                    .setDog(DataInfo.Dog.newBuilder().setName("阿拉斯加").setAge(2).build()).build();
        }else {
            message = DataInfo.Message.newBuilder().setDataType(DataInfo.Message.DataType.CatType)
                    .setCat(DataInfo.Cat.newBuilder().setName("大花猫").setCity("北京").build()).build();
        }
        ctx.channel().writeAndFlush(message);
    }
}
