package com.ylqi007.chap08heap.demo3;

/**
 * 标量替换测试：未开启(User 变量在 heap 中)
 *  -Xmx100m -Xms100m -XX:+DoEscapeAnalysis -XX:+PrintGC -XX:-EliminateAllocations
 *
 * [GC (Allocation Failure)  27136K->632K(102400K), 0.0006490 secs]
 * [GC (Allocation Failure)  27768K->584K(102400K), 0.0005307 secs]
 * [GC (Allocation Failure)  27720K->616K(102400K), 0.0004852 secs]
 * [GC (Allocation Failure)  27752K->568K(102400K), 0.0004611 secs]
 * [GC (Allocation Failure)  27704K->584K(102400K), 0.0004242 secs]
 * [GC (Allocation Failure)  27720K->568K(105472K), 0.0005442 secs]
 * [GC (Allocation Failure)  33848K->456K(105472K), 0.0004867 secs]
 * [GC (Allocation Failure)  33736K->440K(105472K), 0.0002268 secs]
 * 花费的时间为： 19 ms
 *
 * 标量替换测试：开启(User 变量在 VM stack 中)
 *  -Xmx100m -Xms100m -XX:+DoEscapeAnalysis -XX:+PrintGC -XX:+EliminateAllocations
 *
 * 花费的时间为： 2 ms
 *
 *
 * 对比结果：
 * 1. 时间更短
 * 2. 没有GC
 *
 * @author shkstart  shkstart@126.com
 * @create 2020  12:01
 */
public class Demo04ScalarReplace {
    public static class User {
        public int id;
        public String name;
    }

    public static void alloc() {
        User u = new User(); //未发生逃逸
        u.id = 5;
        u.name = "www.atguigu.com";
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
            alloc();
        }
        long end = System.currentTimeMillis();
        System.out.println("花费的时间为： " + (end - start) + " ms");
    }
}

/*
class Customer{
    String name;
    int id;
    Account acct;

}

class Account{
    double balance; // 标量
}

 */
