package com.ylqi007.chap08heap.demo2;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试MinorGC 、 MajorGC、FullGC
 * -Xms9m -Xmx9m -XX:+PrintGCDetails
 * @author shkstart  shkstart@126.com
 * @create 2020  14:19
 * @Created: 2025.09.01
 *
 * [GC (Allocation Failure) [PSYoungGen: 3592K->480K(4608K)] 3592K->1474K(15872K), 0.0005100 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
 * [GC (Allocation Failure) [PSYoungGen: 4077K->352K(4608K)] 5072K->4162K(15872K), 0.0014706 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
 * [Full GC (Ergonomics) [PSYoungGen: 3289K->0K(4608K)] [ParOldGen: 9442K->6052K(11264K)] 12732K->6052K(15872K), [Metaspace: 3198K->3198K(1056768K)], 0.0010492 secs] [Times: user=0.01 sys=0.01, real=0.00 secs]
 * [GC (Allocation Failure) --[PSYoungGen: 2843K->2843K(4608K)] 8895K->11711K(15872K), 0.0002383 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
 * [Full GC (Ergonomics) [PSYoungGen: 2843K->0K(4608K)] [ParOldGen: 8868K->8868K(11264K)] 11711K->8868K(15872K), [Metaspace: 3213K->3213K(1056768K)], 0.0010007 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
 * [GC (Allocation Failure) [PSYoungGen: 0K->0K(4608K)] 8868K->8868K(15872K), 0.0001740 secs] [Times: user=0.00 sys=0.00, real=0.00 secs]
 * [Full GC (Allocation Failure) [PSYoungGen: 0K->0K(4608K)] [ParOldGen: 8868K->8768K(11264K)] 8868K->8768K(15872K), [Metaspace: 3213K->3213K(1056768K)], 0.0016988 secs] [Times: user=0.01 sys=0.00, real=0.01 secs]
 * 遍历次数为：17
 * Heap
 *  PSYoungGen      total 4608K, used 139K [0x00000007bfb00000, 0x00000007c0000000, 0x00000007c0000000)
 *   eden space 4096K, 3% used [0x00000007bfb00000,0x00000007bfb22ee8,0x00000007bff00000)
 *   from space 512K, 0% used [0x00000007bff00000,0x00000007bff00000,0x00000007bff80000)
 *   to   space 512K, 0% used [0x00000007bff80000,0x00000007bff80000,0x00000007c0000000)
 *  ParOldGen       total 11264K, used 8768K [0x00000007bf000000, 0x00000007bfb00000, 0x00000007bfb00000)
 *   object space 11264K, 77% used [0x00000007bf000000,0x00000007bf890350,0x00000007bfb00000)
 *  Metaspace       used 3254K, capacity 4564K, committed 4864K, reserved 1056768K
 *   class space    used 345K, capacity 388K, committed 512K, reserved 1048576K
 * java.lang.OutOfMemoryError: Java heap space
 * 	at java.util.Arrays.copyOf(Arrays.java:3332)
 * 	at java.lang.AbstractStringBuilder.ensureCapacityInternal(AbstractStringBuilder.java:124)
 * 	at java.lang.AbstractStringBuilder.append(AbstractStringBuilder.java:448)
 * 	at java.lang.StringBuilder.append(StringBuilder.java:141)
 * 	at com.ylqi007.chap08heap.demo2.Demo03GCTest.main(Demo03GCTest.java:21)
 */
public class Demo03GCTest {
    public static void main(String[] args) {
        int i = 0;
        try {
            List<String> list = new ArrayList<>();
            String a = "atguigu.com";
            while (true) {
                list.add(a);
                a = a + a;
                i++;
            }

        } catch (Throwable t) {
            t.printStackTrace();
            System.out.println("遍历次数为：" + i);
        }
    }
}
