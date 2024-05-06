package com.atguigu.java;

/**
 * Description:
 *  -Xms10m -Xmx10m
 *
 * @Author: ylqi007
 * @Create: 5/5/24 11:08
 */
public class HeapDemo {
    public static void main(String[] args) {
        System.out.println("Start...");
        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("end...");
    }
}
