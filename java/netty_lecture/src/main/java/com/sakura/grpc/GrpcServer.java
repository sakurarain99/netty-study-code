package com.sakura.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;

/**
 * @ClassName : ProtoServer
 * @Description : grpc server
 * @Author : Mr.Sakura
 * @Date: 2020-03-27 16:24
 */
public class GrpcServer {

    private Server server;

    public static void main(String[] args) throws Exception{
        GrpcServer server = new GrpcServer();

        server.start();
        server.awaitTermination();

    }


    private void start()throws Exception{
        //创建服务通道配置端口，传入映射的方法的实现类，然后构建并启动
        this.server = ServerBuilder.forPort(8899).addService(new StudentServiceImpl())
                .build().start();

        System.out.println("server started!");
        //设置一个回调钩子
        Runtime.getRuntime().addShutdownHook(new Thread(() ->{
            System.out.println("JVM 关闭");
            try {
                this.stop();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));
        System.out.println("执行到这里");
    }

    private void stop() throws InterruptedException {
        if(this.server != null){
            //关闭服务
            this.server.shutdown();
        }
    }

    private void awaitTermination() throws InterruptedException {
        if(this.server != null){
            //等待终止，让服务不停止，可以设置超时时长
            this.server.awaitTermination();
            //this.server.awaitTermination(3000, TimeUnit.MILLISECONDS);
        }
    }

}
