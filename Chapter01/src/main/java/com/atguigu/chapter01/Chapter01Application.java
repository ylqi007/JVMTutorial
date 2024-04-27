package com.atguigu.chapter01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Chapter01Application {

    // 任何一个类声明以后，内部至少存在一个类的构造器
    private static int num = 1;

    static {
        num = 5;
        number = 20;
        System.out.println(num);
        //System.out.println(number); // 报错: Illegal forward reference 非法的前向引用
    }

    private static int number = 10;  // linking之prepare: number=0 --> initial: 20 -> 10

    public static void main(String[] args) {
        SpringApplication.run(Chapter01Application.class, args);
    }

}
