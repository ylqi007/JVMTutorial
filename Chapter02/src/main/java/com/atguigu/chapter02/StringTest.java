package com.atguigu.chapter02;

/**
 * Description:
 *
 * @Author: ylqi007
 * @Create: 4/27/24 19:20
 */
public class StringTest {
    public static void main(String[] args) {
        java.lang.String str = new java.lang.String();  // 并非自定义的String类，而是核心APi的String类
        System.out.println("hello, atguigu.com");

        StringTest test = new StringTest();
        System.out.println(test.getClass().getClassLoader());
    }
}
