var service = require('../static_codegen/proto/Student_grpc_pb');
var messages = require('../static_codegen/proto/Student_pb');

var grpc = require('grpc');
//创建一个客户端
var client = new service.StudentServiceClient("localhost:8899",
    grpc.credentials.createInsecure());

var request = new messages.MyRequest();
request.setUsername("米龙");

//发出请求
client.getRealNameByUsername(request, function (error, respData) {
    console.log(respData.getRealname());
});