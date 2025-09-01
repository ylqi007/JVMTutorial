package com.ylqi007.chap08heap.demo3;

/**
 * 同步省略说明: 代码中对hollis这个对象进行加锁，但是hollis对象的生命周期只在f()方法中，并不会被其他线程访问到，所以在JIT编译阶段就会被优化掉。
 * @author shkstart  shkstart@126.com
 * @create 2020  11:07
 */
public class Demo03SynchronizedTest {
    // 字节码中仍然可以看到 monitorenter, monitorexit, 只是在实际运行时才会考虑去掉
    public void f() {
        Object hollis = new Object();
        synchronized(hollis) {
            System.out.println(hollis);
        }
    }

    public void optimizedF() {
        Object hollis = new Object();
        System.out.println(hollis);
    }
}
