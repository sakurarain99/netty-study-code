package com.sakura.thrift;

import org.apache.thrift.protocol.TCompactProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import thrift.generated.Person;
import thrift.generated.PersonService;

/**
 * @ClassName : ThriftClient
 * @Description : 客户端
 * @Author : Mr.Sakura
 * @Date: 2020-03-19 16:48
 */
public class ThriftClient {
    public static void main(String[] args) {
        //传输层/传输协议：要和服务端的传输协议保持一致,设置地址,端口号,和超时时间，是一个连接/socket
        TTransport transport = new TFramedTransport(
                new TSocket("localhost",8899),600);
        //协议层设置，设置数据传输格式,传入传输层,要与服务端保持一致
        TProtocol protocol = new TCompactProtocol(transport);
        //获得thrift自动生成的Client对象，可以与服务端进行远程调用的对象
        PersonService.Client client = new PersonService.Client(protocol);
        try {
            //打开socket
            transport.open();
            //关键：client本来就没有getPersonByUsername方法，这是通过网络传输调用
            Person person = client.getPersonByUsername("星空");

            System.out.println(person.getUsername());
            System.out.println(person.getAge());
            //对于boolean型，不是get，而是is开头.但是set都一样
            System.out.println(person.isMarried());

            System.out.println("------------------");

            Person person1 = new Person().setUsername("测试").setAge(18).setMarried(false);
            client.savePersion(person1);

        }catch (Exception e){
            throw new RuntimeException(e.getMessage(),e);
        }finally {
            //最后关闭transport
            transport.close();
        }
    }
}
