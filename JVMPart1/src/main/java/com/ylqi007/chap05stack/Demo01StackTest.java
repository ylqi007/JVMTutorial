package com.ylqi007.chap05stack;

/**
 * @Author: ylqi007
 * @Date: 8/31/25  10:51 AM
 * @Description:
 */
public class Demo01StackTest {
    public static void main(String[] args) {
        Demo01StackTest demo01StackTest = new Demo01StackTest();
        demo01StackTest.methodA();
    }

    public void methodA() {
        int i = 10;
        int j = 20;
        methodB();
    }

    private void methodB() {
        int m = 11;
        int n = 22;
    }
}
