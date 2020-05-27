# -*- coding: utf-8 -*-

from py.thrift.generated import PersonService
from PersonServiceImpl import PersonServiceImpl

from thrift import Thrift
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TCompactProtocol
from thrift.server import TServer
import sys

#修改字符编码 解决thrift无法传输中文问题
reload(sys)
sys.setdefaultencoding('utf-8')


try:
    personServiceHandler = PersonServiceImpl()
    #设置Processor
    processor = PersonService.Processor(personServiceHandler)
    #绑定端口号
    serverSocket = TSocket.TServerSocket(port=8899)
    #服务端使用Factory 客户端使用Transport
    transportFactory = TTransport.TFramedTransportFactory()
    #服务端使用Factory 客户端使用Protocol
    protocolFactory = TCompactProtocol.TCompactProtocolFactory()

    server = TServer.TSimpleServer(processor,serverSocket,transportFactory,protocolFactory)
    
    server.serve()

except Thrift.TException, ex:
    print '%s' % ex.message