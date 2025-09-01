package com.ylqi007.chap08heap.demo3;

/**
 * 栈上分配测试
 * -Xmx1G -Xms1G -XX:-DoEscapeAnalysis -XX:+PrintGCDetails
 *  * 花费的时间为： 30 ms
 *  * 在 VisualVM 中，通过 Sampler 可以看到有 10M 个 live objects
 *
 * -Xmx1G -Xms1G -XX:+DoEscapeAnalysis -XX:+PrintGCDetails
 *  * 花费的时间为： 2 ms
 *  * 在 VisualVM 中，通过 Sampler 可以看到有 5k 个 live objects
 *
 * -Xmx256M -Xms256M -XX:+DoEscapeAnalysis -XX:+PrintGCDetails
 *  * 时间更短
 *  * 没有发生 GC
 * @author shkstart  shkstart@126.com
 * @create 2020  10:31
 */
public class Demo02StackAllocation {
    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        for (int i = 0; i < 10000000; i++) {
            alloc();
        }
        // 查看执行时间
        long end = System.currentTimeMillis();
        System.out.println("花费的时间为： " + (end - start) + " ms");
        // 为了方便查看堆内存中对象个数，线程sleep
        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        }
    }

    private static void alloc() {
        User user = new User(); //未发生逃逸, 分配到栈空间上
    }

    static class User {

    }
}
