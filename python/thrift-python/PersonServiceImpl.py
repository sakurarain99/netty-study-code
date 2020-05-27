# -*- coding:utf-8 -*-

from py.thrift.generated import *

class PersonServiceImpl:
    def getPersonByUsername(self,username):
        print "Python Got client param：" + username

        person = ttypes.Person()
        person.username = username
        person.age = 18
        person.married = False

        return person
    
    def savePersion(self,person):
        print "Python Got client param："

        print person.username
        print person.age
        print person.married