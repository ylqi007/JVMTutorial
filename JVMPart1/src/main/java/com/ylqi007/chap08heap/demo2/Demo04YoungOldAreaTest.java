package com.ylqi007.chap08heap.demo2;

/** 测试：大对象直接进入老年代
 * -Xms60m -Xmx60m -XX:NewRatio=2 -XX:SurvivorRatio=8 -XX:+PrintGCDetails
 * @author shkstart  shkstart@126.com
 * @create 2020  21:48
 *
 * @Author: ylqi007
 * @Created: 2025.09.01
 *
 * Heap
 *  PSYoungGen      total 19456K, used 2440K [0x00000007beb00000, 0x00000007c0000000, 0x00000007c0000000)
 *   eden space 17408K, 14% used [0x00000007beb00000,0x00000007bed62108,0x00000007bfc00000)
 *   from space 2048K, 0% used [0x00000007bfe00000,0x00000007bfe00000,0x00000007c0000000)
 *   to   space 2048K, 0% used [0x00000007bfc00000,0x00000007bfc00000,0x00000007bfe00000)
 *  ParOldGen       total 44032K, used 20480K [0x00000007bc000000, 0x00000007beb00000, 0x00000007beb00000)
 *   object space 44032K, 46% used [0x00000007bc000000,0x00000007bd400010,0x00000007beb00000)
 *  Metaspace       used 3204K, capacity 4564K, committed 4864K, reserved 1056768K
 *   class space    used 340K, capacity 388K, committed 512K, reserved 1048576K
 */
public class Demo04YoungOldAreaTest {
    public static void main(String[] args) {
        byte[] buffer = new byte[1024 * 1024 * 20]; //20MB，大对象直接进入老年代
    }
}
