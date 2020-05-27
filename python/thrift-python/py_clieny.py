# -*- coding: utf-8 -*-

from thrift import Thrift
from thrift.transport import TSocket
from thrift.transport import TTransport
from thrift.protocol import TCompactProtocol
import sys

#修改字符编码 解决thrift无法传输中文问题
reload(sys)
sys.setdefaultencoding('utf-8')

from py.thrift.generated import *
try:
    #设置连接 指定地址以及端口号
    tSocket = TSocket.TSocket('localhost',8899)
    #设置超时时间
    tSocket.setTimeout(600)
    #设置数据传输格式，设为TFramedTransport并传入tSocket
    transport = TTransport.TFramedTransport(tSocket)
    #设置数据的传输方式，设为TCompactProtocol并传入transport
    protocol = TCompactProtocol.TCompactProtocol(transport)
    #创建client 于服务端交互的接口
    client = PersonService.Client(protocol)
    #开启连接
    transport.open()

    #调用相应的方法
    person = client.getPersonByUsername("三木")

    print person.username
    print person.age
    print person.married

    print "------------"

    newPerson = ttypes.Person()
    newPerson.username = "星空"    
    newPerson.age = 18
    newPerson.married = True

    client.savePersion(newPerson)
    #关闭连接
    transport.close()
    

except Thrift.TException, tx:
    print '%s' % tx.message