package com.atguigu;

/**
 * Description:
 *
 * 方法结束的方式有两种:
 *  1. 正常结束，以return为代表
 *  2. 方法执行中出现未捕获处理的异常，以抛出Exception的方式结束。
 *
 * @Author: ylqi007
 * @Create: 4/28/24 14:56
 */
public class StackFrameTest {
    /*
    Exception in thread "main" java.lang.ArithmeticException: / by zero
	at com.atguigu.StackFrameTest.method1(StackFrameTest.java:25)
	at com.atguigu.StackFrameTest.main(StackFrameTest.java:17)
     */
    public static void main(String[] args) {
//        StackFrameTest stackFrameTest = new StackFrameTest();
//        stackFrameTest.method1();
//        System.out.println("main()正常结束");

        /*
        java.lang.ArithmeticException: / by zero
        at com.atguigu.StackFrameTest.method1(StackFrameTest.java:38)
        at com.atguigu.StackFrameTest.main(StackFrameTest.java:27)
         */
        try {
            StackFrameTest stackFrameTest = new StackFrameTest();
            stackFrameTest.method1();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("main()正常结束");
    }

    public void method1() {
        System.out.println("method1()开始运行");
        method2();
        System.out.println("method1()执行完毕");
        // System.out.println(10 / 0);
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
