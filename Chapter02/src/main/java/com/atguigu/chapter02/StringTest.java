package com.atguigu.chapter02;

/**
 * @Author: ylqi007
 * @Create: 4/27/24 19:20
 * @Description: 双亲委派机制
 */
public class StringTest {
    public static void main(String[] args) {
        java.lang.String str = new java.lang.String();  // 并非自定义的java.lang.String类，而是核心API的String类
        System.out.println("hello, atguigu.com");

        // 使用的是 Application ClassLoader
        StringTest test = new StringTest();
        System.out.println(test.getClass().getClassLoader());
    }
}
