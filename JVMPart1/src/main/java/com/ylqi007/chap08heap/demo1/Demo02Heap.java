package com.ylqi007.chap08heap.demo1;

/**
 * Description:
 * -Xms20m -Xmx20m
 * Young （Eden, S0, S1) + Old = 20MB
 *
 * @Author: ylqi007
 * @Create: 5/5/24 11:08
 */
public class Demo02Heap {
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
