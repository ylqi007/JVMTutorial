package com.atguigu.chapter02;

/**
 * Description:
 *
 * @Author: ylqi007
 * @Create: 4/27/24 11:50
 */
public class Demo05ClassInitTest {
    private static int num = 1;

    static {
        num = 2;
        number = 20;
        System.out.println(num);
        // System.out.println(number); // 报错：Illegal forward reference，非法的前向引用
    }

    private static int number = 10; // linking的prepare环节中: number=0; --> initial: 20 -> 10

    public static void main(String[] args) {
        System.out.println(num);
        System.out.println(number);
    }
}
