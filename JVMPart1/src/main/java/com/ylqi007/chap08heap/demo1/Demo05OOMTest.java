package com.ylqi007.chap08heap.demo1;

import com.ylqi007.utils.CommonUtils;

import java.util.ArrayList;
import java.util.Random;

/**
 * -Xms600m -Xmx600m -XX:+PrintGCDetails
 * @author ylqi007
 * @create 2025 09 01, 11:00AM
 *
 * Heap
 *  PSYoungGen      total 179200K, used 3072K [0x00000007b3800000, 0x00000007c0000000, 0x00000007c0000000)
 *   eden space 153600K, 2% used [0x00000007b3800000,0x00000007b3b00388,0x00000007bce00000)
 *   from space 25600K, 0% used [0x00000007bce00000,0x00000007bce00000,0x00000007be700000)
 *   to   space 25600K, 0% used [0x00000007be700000,0x00000007be700000,0x00000007c0000000)
 *  ParOldGen       total 409600K, used 5560K [0x000000079a800000, 0x00000007b3800000, 0x00000007b3800000)
 *   object space 409600K, 1% used [0x000000079a800000,0x000000079ad6e028,0x00000007b3800000)
 *  Metaspace       used 9270K, capacity 9564K, committed 9984K, reserved 1058816K
 *   class space    used 1056K, capacity 1131K, committed 1280K, reserved 1048576K
 * Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
 * 	at com.ylqi007.chap08heap.demo1.Picture.<init>(Demo05OOMTest.java:27)
 * 	at com.ylqi007.chap08heap.demo1.Demo05OOMTest.main(Demo05OOMTest.java:18)
 */
public class Demo05OOMTest {
    public static void main(String[] args) {
        ArrayList<Picture> list = new ArrayList<>();
        while(true){
            CommonUtils.sleepMillis(60);
            list.add(new Picture(new Random().nextInt(1024 * 1024)));
        }
    }
}

class Picture {
    private byte[] pixels;

    public Picture(int length) {
        this.pixels = new byte[length];
    }
}
