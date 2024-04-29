package com.atguigu;

/**
 * Description:
 *
 * @Author: ylqi007
 * @Create: 4/28/24 14:01
 */
public class StackTest {
    public static void main(String[] args) {
        StackTest stackTest = new StackTest();
        stackTest.methodA();
    }

    private void methodA() {
        int i = 10;
        int j = 20;

        methodB();
    }

    private void methodB() {
        int k = 30;
        int m = 40;
    }
}
