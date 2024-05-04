package com.atguigu.java1;

/**
 * Description:
 *
 * @Author: ylqi007
 * @Create: 5/1/24 08:09
 */
public class DynamicLinkingTest {
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
