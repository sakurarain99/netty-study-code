package com.sakura.decorator;

/**
 * 实现抽象构建角色 Component
 * 装饰角色 对应Java io中输入流中的 FilterInputStream
 */
public class Decorator implements Component {

    /**持有被装饰者抽象对象*/
    private Component component;

    public Decorator(Component component) {
        this.component = component;
    }

    @Override
    public void doSomething() {
        component.doSomething();
    }
}
