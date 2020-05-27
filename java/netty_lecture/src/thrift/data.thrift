namespace java thrift.generated
#定义命名空间： namespace 语言名 路径
namespace py py.thrift.generated

#定义类型别名
typedef i16 short
typedef i32 int
typedef i64 long
typedef bool boolean
typedef string String

#定义一个消息/对象/结构体  关键字struct
struct Person{
    1:optional String username;
    2:optional int age;
    3:optional boolean married;
}
/*说明：
    唯一标记数:修饰符 数据类型 属性名
  修饰符： 默认就是optional
       required 必须的，必须存在，必须赋值
       optional 可选的，可以不使用
*/

#定义一个异常，数据传递时/方法调用是可能出现的异常 关键字exception
#服务端如果出现异常的话直接抛给客户端，让客户端catch处理
exception DataException{
    1:optional String message;
    2:optional String callStack;
    3:optional String date;
}

#定义服务接口 关键字service
#定义一系列方法，就是客户端于服务端进行交互所调用的方法，具体实现由服务端完成
service PersonService{
    Person getPersonByUsername(1:required String username) throws (1:DataException dataException),
    void savePersion(1:required Person person) throws (1:DataException dataException)
}
