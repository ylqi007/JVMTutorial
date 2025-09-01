package com.ylqi007.chap08heap.demo1;

/**
 * Description:
 *  -Xms10m -Xmx10m
 *  Young （Eden, S0, S1) + Old = 10MB
 *
 * -XX:+PrintGCDetails
 * @Author: ylqi007
 * @Create: 5/5/24 11:08, 2025.09.01
 */
public class Demo01Heap {
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
