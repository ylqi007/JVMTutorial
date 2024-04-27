package com.atguigu.chapter02;

/**
 * Description:
 *
 * @Author: ylqi007
 * @Create: 4/24/24 17:21
 */
public class DeadThreadTest {
    public static void main(String[] args) {
        Runnable run = () -> {
            System.out.println(Thread.currentThread().getName() + "开始");
            DeadThread deadThread = new DeadThread();
            System.out.println(Thread.currentThread().getName() + "结束");
        };

        Thread t1 = new Thread(run, "Thread 1");
        Thread t2 = new Thread(run, "Thread 2");

        t1.start();
        t2.start();
    }
}


class DeadThread {
    static {
        if(true) {
            System.out.println(Thread.currentThread().getName() + "：正在初始化当前类DeadThread");
            while (true) {}
        }
    }
}
