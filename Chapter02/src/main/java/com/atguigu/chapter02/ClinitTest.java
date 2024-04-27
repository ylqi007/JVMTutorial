package com.atguigu.chapter02;

/**
 * Description:
 *
 * @Author: ylqi007
 * @Create: 4/27/24 12:06
 */
public class ClinitTest {
    // 任何一个类声明以后，内部至少存在一个类的构造器
    private static int c = 3;
    private int a = 1;

    public static void main(String[] args) {
        int b = 2;
    }

    public ClinitTest() {
        a = 10;
        int d = 20;
    }
}
