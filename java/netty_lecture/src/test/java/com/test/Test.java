package com.test;

import io.netty.util.NettyRuntime;
import io.netty.util.internal.SystemPropertyUtil;

/**
 * 基础测试
 *
 * @author Mr.Miki
 * @date 2020-06-06 09:57
 * @since 1.0
 **/
public class Test {


    @org.junit.Test
    public void test0(){
        int anInt = SystemPropertyUtil.getInt(
                "io.netty.eventLoopThreads", NettyRuntime.availableProcessors() * 2);
        System.out.println("anInt = " + anInt);
    }
}
