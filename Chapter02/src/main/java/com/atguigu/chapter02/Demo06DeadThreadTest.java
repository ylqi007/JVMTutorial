package com.atguigu.chapter02;

/**
 * @Author: ylqi007
 * @Create: 4/24/24 17:21
 * @Description: 两个线程都尝试去初始化
 * 一个类只会被加载一次
 */
public class Demo06DeadThreadTest {
    public static void main(String[] args) {
        Runnable run = () -> {
            System.out.println(Thread.currentThread().getName() + "开始");
            DeadThread deadThread = new DeadThread();   // 初始化，只会有一个线程初始化
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
