package com.ylqi007.chap05stack;

/**
 * Description:
 *
 * 方法结束的方式有两种:
 *  1. 正常结束，以return为代表
 *  2. 方法执行中出现未捕获处理的异常，以抛出Exception的方式结束。
 *
 * @Author: ylqi007
 * @Create: 4/28/24 14:56, 2025/08/31
 */
public class Demo04StackFrameTest {
    /*
    Exception in thread "main" java.lang.ArithmeticException: / by zero
	at com.atguigu.StackFrameTest.method1(StackFrameTest.java:25)
	at com.atguigu.StackFrameTest.main(StackFrameTest.java:17)
     */
    public static void main(String[] args) {
        Demo04StackFrameTest stackFrameTest = new Demo04StackFrameTest();
//        stackFrameTest.method1();
//        System.out.println("#### main()正常结束");

        /* 执行方法中出现异常，以捕获并处理异常的方式结束
        java.lang.ArithmeticException: / by zero
        at com.atguigu.StackFrameTest.method1(StackFrameTest.java:38)
        at com.atguigu.StackFrameTest.main(StackFrameTest.java:27)
         */
        try {
            stackFrameTest.method1();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("#### main()正常结束：以捕获并处理异常");

        // 执行方法中出现未被捕获的异常，以抛出异常的方式结束
//        stackFrameTest.method1();
//        System.out.println("#### main()正常结束: 以抛出异常的方式正常结束");
    }

    public void method1() {
        System.out.println("method1()开始运行");
        method2();
        System.out.println("method1()执行完毕");
        // System.out.println(10 / 0);
        // return; // 可以省略
    }

    public int method2() {
        System.out.println("method2()开始运行");
        int i = 10;
        int m = (int)method3();
        System.out.println("method2()即将结束");
        return i + m;
    }

    public double method3() {
        System.out.println("method3()开始运行");
        double j = 20;
        System.out.println("method3()即将结束");
        return j;
    }
}
