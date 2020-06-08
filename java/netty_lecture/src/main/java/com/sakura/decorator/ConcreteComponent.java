package com.sakura.decorator;

/**
 * 具体构件角色 对应Java io中输入流中的 InputStream 抽象类下除FilterInputStream及子类以外的子类
 * 例如 FileInputStream 及其它节点流
 */
public class ConcreteComponent implements Component{
    @Override
    public void doSomething() {
        System.out.println("功能A");
    }
}
