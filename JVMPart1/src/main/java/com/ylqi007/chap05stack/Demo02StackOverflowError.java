package com.ylqi007.chap05stack;

/**
 * @Author: ylqi007
 * @Date: 8/31/25  11:03 AM
 * @Description: StackOverflowError 演示
 *
 * Exception in thread "main" java.lang.StackOverflowError
 * 	at com.ylqi007.chap05stack.Demo02StackOverflowError.main(Demo02StackOverflowError.java:10)
 * 	at com.ylqi007.chap05stack.Demo02StackOverflowError.main(Demo02StackOverflowError.java:10)
 * 	...
 * 	...
 */
public class Demo02StackOverflowError {
    public static void main(String[] args) {
        main(args);
    }
}
