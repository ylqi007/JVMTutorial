package com.atguigu.java;

/**
 * non-final的类变量
 * @author shkstart  shkstart@126.com
 * @create 2020  20:37
 */
public class MethodAreaTest {
    public static void main(String[] args) {
        Order order = null;
        order.hello();  // 即使order是null，但是hello()方法是static方法，依然可以访问
        System.out.println(order.count);
    }
}

class Order {
    public static int count = 1;
    public static final int number = 2;


    public static void hello() {
        System.out.println("hello!");
    }
}

