var PROTO_FILE_PATH = "/home/ideaHome/netty/nodejs/grpc_nodejs/proto/Student.proto";
var grpc = require("grpc");
var grpcService = grpc.load(PROTO_FILE_PATH).com.sakura.proto;

//grpc.credentials.createInsecure() 不使用加密的
var client = new grpcService.StudentService("localhost:8899",grpc.credentials.createInsecure());

//node是一个异步的框架,绝大多数都是通过回调的方式获取对端的响应,也有同步的操作,
//但是对与node来说绝大多数都是通过异步的方式来获取对方返回的结果

//动态的代码生成就是以一个json对象的方式传输给对端
//方法 (参数,回调方法(异常,对端返回的数据))
client.getRealNameByUsername({username: "麦当"},function(error,respData){
    console.log(respData);
});