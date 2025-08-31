package com.atguigu.chapter02;

/**
 * Description:
 *
 * @Author: ylqi007
 * @Create: 4/27/24 12:06
 */
public class Demo03ClinitTest1 {
    // 任何一个类声明以后，内部至少存在一个类的构造器
    private static int c = 3;
    private int a = 1;

    static {
        c = 4;
        number = 20; // linking的prepare阶段，number=10 --> Initialization阶段: 10-->20
        System.out.println(c);

        // 报错：非法的前向引用
        // System.out.println(number); // Cannot read value of field 'number' before the field's definitio
    }

    private static int number = 10;

    public static void main(String[] args) {
        int b = 2;
    }

    public Demo03ClinitTest1() {
        a = 10;
        int d = 20;
    }
}
