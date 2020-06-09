package com.sakura.grpc;

import com.sakura.proto.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import java.util.Iterator;

/**
 * @ClassName : GrpcClient
 * @Description : grpc client
 * @Author : Mr.Sakura
 * @Date: 2020-03-27 16:53
 */
public class GrpcClient {



    public static void main(String[] args) throws InterruptedException {
        ManagedChannel managedChannel =
                ManagedChannelBuilder.forTarget("localhost:8899").usePlaintext().build();
        //客户端与服务端交互的对象  server与client通信的对象
        //blockingStub 阻塞的方式/同步  发出一个请求一定要等到另一端返回了响应才继续往下执行
       /* StudentServiceGrpc.StudentServiceBlockingStub blockingStub =
                StudentServiceGrpc.newBlockingStub(managedChannel);
        //构建消息
        MyRequest request = MyRequest.newBuilder().setUsername("出发，目标彩虹海").build();
        //调用具体方法，接收到响应
        MyResponse response = blockingStub.getRealNameByUsername(request);
        System.out.println("接收到服务器信息：" + response.getRealname());

        System.out.println("--------------------普通请求与响应 结束----------------------");

        //返回一个流式的响应就是一个迭代器，每返回一个对象就进入到迭代器中，再返回对象再进入迭代器，以此类推
        Iterator<StudentResponse> iter =
                blockingStub.getStudentsByAge(StudentRequest.newBuilder().setAge(18).build());
        //iter.hasNext()  还有没有下一个
        while (iter.hasNext()){
            StudentResponse studentResponse = iter.next();
            System.out.println(studentResponse.getName() + " , " +
                    studentResponse.getAge() + " , " + studentResponse.getCity());
        }
        System.out.println("-----------------------普通请求 流式响应 结束-------------------");
        //客户端请求一个steam(流式) blockingStub(同步)无法使用 只有使用异步形式
*/
        //构造接收服务端信息的方法
        StreamObserver<StudentResponseList> studentResponseListStreamObserver = new StreamObserver<StudentResponseList>() {
            /**
             * 服务端向客户端响应结果时会被调用
             * 服务端返回的数据，每返回一次数据则被调用一次
             * 如果服务器端也是流式的并且返回了多个数据，那么每次返回数据的时候都会被调用一次
             * @param value
             */
            @Override
            public void onNext(StudentResponseList value) {
                value.getStudentResponseList().forEach(studentResponse -> {
                    System.out.println(studentResponse.getName() + " , " +
                            studentResponse.getAge() + " , " + studentResponse.getCity());
                    System.out.println("**************");
                });
            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("onCompleted");
            }
        };

        //构造客户端向服务端发送的数据
        //只要是客户端是以流式的方式向服务器发送请求，这种请求一定以异步的
        //blockingStub是同步的阻塞的，则不会被提供方法
        //获取一个异步的通信对象
        //创建一个支持该服务的所有呼叫类型的新异步存根，不会等待对方响应会一直向下执行
        StudentServiceGrpc.StudentServiceStub stub = StudentServiceGrpc.newStub(managedChannel);
        //getStudentsWrapperByAges(传入处理服务端返回数据的回调对象)
        StreamObserver<StudentRequest> studentsWrapperByAges =
                stub.getStudentsWrapperByAges(studentResponseListStreamObserver);
        //发送数据
        studentsWrapperByAges.onNext(StudentRequest.newBuilder().setAge(18).build());
        studentsWrapperByAges.onNext(StudentRequest.newBuilder().setAge(28).build());
        studentsWrapperByAges.onNext(StudentRequest.newBuilder().setAge(38).build());
        studentsWrapperByAges.onNext(StudentRequest.newBuilder().setAge(48).build());
        //表示客户端调用结束
        studentsWrapperByAges.onCompleted();
        //客户端向服务器端发送数据，数据还没发送就继续向下执行走到了onCompleted()，
        //然后在数据还未发出时程序就正常执行完成结束了
        //线程睡眠，强制让程序等待，等待studentsWrapperByAges将数据全部发送给服务器端
        //如果不睡眠的话，数据还没发送给服务器端，jvm就停止了，也就发送不出去了
        System.out.println("-----------------------流式请求 普通响应 结束-------------------");





        Thread.sleep(5000);
    }
}
