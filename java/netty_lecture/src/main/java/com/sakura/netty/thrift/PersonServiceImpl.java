package com.sakura.netty.thrift;

import org.apache.thrift.TException;
import thrift.generated.DataException;
import thrift.generated.Person;
import thrift.generated.PersonService;

/**
 * @ClassName : PersonService
 * @Description : thrift生成的接口文件的实现类
 * @Author : Mr.Sakura
 * @Date: 2020-03-19 16:27
 */
public class PersonServiceImpl implements PersonService.Iface {

    @Override
    public Person getPersonByUsername(String username) throws DataException, TException {
        System.out.println("Got Client Param：" + username);

        Person person = new Person().setUsername(username)
                .setAge(18).setMarried(false);
        return person;
    }

    @Override
    public void savePersion(Person person) throws DataException, TException {
        System.out.println("Got Client Param：");
        System.out.println(person.getUsername());
        System.out.println(person.getAge());
        System.out.println(person.isMarried());
    }
}
