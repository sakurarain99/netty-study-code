package com.sakura.grpc;

import com.sakura.proto.*;
import io.grpc.stub.StreamObserver;

/**
 * @ClassName : StudentServiceImpl
 * @Description : 远程调用的方法的具体实现，实现生成代码中的内部抽象类
 * @Author : Mr.Sakura
 * @Date: 2020-03-27 16:19
 */
public class StudentServiceImpl extends StudentServiceGrpc.StudentServiceImplBase {

    /**
     * 重写父类的方法
     * @param request 客户端发来的数据
     * @param responseObserver 响应观察者 用于响应客户端的对象
     */
    @Override
    public void getRealNameByUsername(MyRequest request, StreamObserver<MyResponse> responseObserver) {
        System.out.println("接收到客户端信息：" + request.getUsername());
        /*
            onCompleted()   标示这个方法调用结束，只能调用一次
            onError()   异常时调用
            onNext()    接下来要做什么事，可以用于结果返回
         */
        //构造响应对象，并返回
        responseObserver.onNext(MyResponse.newBuilder().setRealname("星空").build());
        //标示服务器处理结束
        responseObserver.onCompleted();
    }


    @Override
    public void getStudentsByAge(StudentRequest request, StreamObserver<StudentResponse> responseObserver) {
        System.out.println("接受到客户端信息：" + request.getAge());
        responseObserver.onNext(StudentResponse.newBuilder().setName("彩虹海").setAge(18).setCity("北京").build());
        responseObserver.onNext(StudentResponse.newBuilder().setName("彩虹海2").setAge(20).setCity("上海").build());
        responseObserver.onNext(StudentResponse.newBuilder().setName("彩虹海3").setAge(22).setCity("广州").build());
        responseObserver.onNext(StudentResponse.newBuilder().setName("彩虹海4").setAge(18).setCity("深圳").build());

        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<StudentRequest> getStudentsWrapperByAges(StreamObserver<StudentResponseList> responseObserver) {
        //实现StreamObserver接口，实现方法当特定的事件触发时，回调方法就会的到调用
        return new StreamObserver<StudentRequest>() {
            /**
             * 接收客户端的请求，请求到来时被调用
             * 每来一次请求，onNext()方法就会被调用一次
             * 因为请求是流式的，onNext会被调用多次
             * @param value
             */
            @Override
            public void onNext(StudentRequest value) {
                System.out.println("onNext：" + value.getAge());
            }

            /**
             * 出现异常时被调用
             * @param t
             */
            @Override
            public void onError(Throwable t) {
                System.out.println(t.getMessage());
            }

            /**
             * 表示客户端将流式数据全部发给服务器端之后，客户端就会有一个onCompleted事件，服务器端就会感知到
             * 然后服务器端在onCompleted中为客户端返回最终结果
             */
            @Override
            public void onCompleted() {
                StudentResponse.Builder studentResponse =
                        StudentResponse.newBuilder().setName("彩虹海1").setAge(18).setCity("宇宙");
                StudentResponse.Builder studentResponse2 =
                        StudentResponse.newBuilder().setName("彩虹海2").setAge(20).setCity("宇宙");
                StudentResponseList studentResponseList = StudentResponseList.newBuilder().addStudentResponse(studentResponse)
                        .addStudentResponse(studentResponse2).build();
                //返回结果
                responseObserver.onNext(studentResponseList);
                //表示处理完成
                responseObserver.onCompleted();
            }
        };
    }
}
