package com.ylqi007.chap05stack;

/**
 * @Description: 动态连接演示
 *
 * @Author: ylqi007
 * @Create: 5/1/24 08:09
 */
public class Demo07DynamicLinkingTest {
    int num = 10;

    public void methodA() {
        System.out.println("methodA() ...");
    }

    public void methodB() {
        System.out.println("methodB() ...");
        methodA();
        num++;
    }
}
