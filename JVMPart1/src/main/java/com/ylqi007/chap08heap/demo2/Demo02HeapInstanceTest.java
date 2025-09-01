package com.ylqi007.chap08heap.demo2;

import com.ylqi007.utils.CommonUtils;

import java.util.ArrayList;
import java.util.Random;

/**
 * -Xms600m -Xmx600m
 * @author shkstart  shkstart@126.com
 * @create 2020  17:51
 *
 * @Created: 2025.09.10
 *
 * Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
 * 	at com.ylqi007.chap08heap.demo2.Demo02HeapInstanceTest.<init>(Demo02HeapInstanceTest.java:16)
 * 	at com.ylqi007.chap08heap.demo2.Demo02HeapInstanceTest.main(Demo02HeapInstanceTest.java:21)
 */
public class Demo02HeapInstanceTest {
    byte[] buffer = new byte[new Random().nextInt(1024 * 200)];

    public static void main(String[] args) {
        ArrayList<Demo02HeapInstanceTest> list = new ArrayList<Demo02HeapInstanceTest>();
        while (true) {
            list.add(new Demo02HeapInstanceTest());
            CommonUtils.sleepMillis(20);
        }
    }
}
