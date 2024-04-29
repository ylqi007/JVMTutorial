package com.atguigu;

/**
 * Description: 演示Stack异常: StackOverflowError
 *
 * @Author: ylqi007
 * @Create: 4/28/24 14:31
 */
public class StackErrorTest {

    /*
    Exception in thread "main" java.lang.StackOverflowError
	at com.atguigu.StackErrorTest.main(StackErrorTest.java:16)
     */
    public static void main(String[] args) {
        main(args);
    }
}
