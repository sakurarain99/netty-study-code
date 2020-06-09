package com.sakura.thrift;

import org.apache.thrift.TProcessorFactory;
import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TNonblockingServerSocket;
import thrift.generated.PersonService;

/**
 * @ClassName : ThriftServer
 * @Description : 服务端  实现client远程调用Server方法
 *  这个结果说明了方法还是在Server端。
 *  Client调用方法，方法走了一遍，但其实还是在server端走，最后的结果通过网络传输到Client
 *  方法体里的打印值，还是在Server端打印。只有Client端自己打印的值，才会出现在Client中。
 * @Author : Mr.Sakura
 * @Date: 2020-03-19 16:32
 */
public class ThriftServer {
    public static void main(String[] args) throws Exception {
        //非阻塞的socket  绑定端口号8899,表示客户端与服务端建立的连接
        TNonblockingServerSocket socket = new TNonblockingServerSocket(8899);
        //高可用的server，并设置工作线程的最大值和最小值  arg作用就是构建一系列信息
        THsHaServer.Args arg = new THsHaServer.Args(socket)
                .minWorkerThreads(2).maxWorkerThreads(4);
        //设置处理器,将实现接口作为泛型，因为客户端那边调用的就是这个，所以后面传输的也是这个对象new PersonServiceImpl()
        PersonService.Processor<PersonServiceImpl> processor =
                new PersonService.Processor<>(new PersonServiceImpl());

        //设置协议工厂
        //协议层:表示数据传输格式，这里TCompactProtocol(二进制压缩协议)表示压缩格式,速率很快
        arg.protocolFactory(new TCompactProtocol.Factory());
        //传输层:表示数据的传输方式，这里TFramedTransport是以frame为单位传输,非阻塞式传输
        arg.transportFactory(new TFramedTransport.Factory());
        arg.processorFactory(new TProcessorFactory(processor));

        //启动server  支持的服务模型：THsHaServer半同步，半异步Server
        TServer server = new THsHaServer(arg);

        System.out.println("Thrift Server Started!");

        //一个异步死循环，永远不会退出
        server.serve();
    }
}
