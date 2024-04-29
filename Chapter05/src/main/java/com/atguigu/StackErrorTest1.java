package com.atguigu;

/**
 * Description: 演示Stack异常: StackOverflowError
 *  默认情况下, -Xss1024K, 打印出10825
 *  设置未 -Xss256K，打印出1875
 * @Author: ylqi007
 * @Create: 4/28/24 14:31
 */
public class StackErrorTest1 {

    private static int count = 1;
    public static void main(String[] args) {
        System.out.println(count);
        count++;
        main(args);
    }
}

/* 未设置 -Xss 时
10823
10824
10825
*** java.lang.instrument ASSERTION FAILED ***: "!errorOutstanding" with message transform method call failed at JPLISAgent.c line: 844
Exception in thread "main" java.lang.StackOverflowError
	at java.io.PrintStream.write(PrintStream.java:526)
 */

/* 设置-Xss=256K. Click Run --> Edit Configurations...
1874
1875
*** java.lang.instrument ASSERTION FAILED ***: "!errorOutstanding" with message transform method call failed at JPLISAgent.c line: 844
*** java.lang.instrument ASSERTION FAILED ***: "!errorOutstanding" with message transform method call failed at JPLISAgent.c line: 844
*** java.lang.instrument ASSERTION FAILED ***: "!errorOutstanding" with message transform method call failed at JPLISAgent.c line: 844
Exception in thread "main" java.lang.StackOverflowError
	at sun.nio.cs.UTF_8.updatePositions(UTF_8.java:77)
 */
