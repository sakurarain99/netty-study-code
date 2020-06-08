var PROTO_FILE_PATH = "/home/ideaHome/netty/nodejs/grpc_nodejs/proto/Student.proto";
var grpc = require("grpc");
var grpcService = grpc.load(PROTO_FILE_PATH).com.sakura.proto;

var server = new grpc.Server();

server.addService(grpcService.StudentService.service,{
    //函数: 服务(想要自定义)
    getRealNameByUsername: getRealNameByUsername,
    getStudentsByAge: getStudentsByAge,
    getStudentsWrapperByAges: getStudentsWrapperByAges,
    biTalk: biTalk
});
//grpc.ServerCredentials.createInsecure() 使用纯文本的方式传输 不使用加密的
server.bind("localhost:8899",grpc.ServerCredentials.createInsecure());
server.start();

//实现具体方法被调用时,处理的方法(服务)
//参数(客户端的请求,回调(函数,请求收到之后最后调用这个回调把结果返回给客户端))
function getRealNameByUsername(call, callback){
    console.log("username：" + call.request.username);

    //参数(错误对象,真正要给客户端返回的结果值)
    callback(null,{realname: "星空"});
}

//用不到 空实现
function getStudentsByAge() {}
function getStudentsWrapperByAges() {}
function biTalk() {}