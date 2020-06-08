var service = require('../static_codegen/proto/Student_grpc_pb');
var messages = require('../static_codegen/proto/Student_pb');

var grpc = require('grpc');

var server = new grpc.Server();

server.addService(service.StudentServiceService,{
    //函数: 服务(想要自定义)
    getRealNameByUsername: getRealNameByUsername,
    getStudentsByAge: getStudentsByAge,
    getStudentsWrapperByAges: getStudentsWrapperByAges,
    biTalk: biTalk
});
server.bind("localhost:8899",grpc.ServerCredentials.createInsecure());
server.start();

//参数(客户端的请求,回调(函数,请求收到之后最后调用这个回调把结果返回给客户端))
function getRealNameByUsername(call,callback) {
    console.log("request：" + call.request.getUsername());

    var myResponse = new messages.MyResponse();
    myResponse.setRealname("目标，彩虹海");
    callback(null, myResponse);
}



//用不到 空实现
function getStudentsByAge() {}
function getStudentsWrapperByAges() {}
function biTalk() {}