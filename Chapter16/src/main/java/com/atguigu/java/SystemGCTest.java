package com.atguigu.java;

/**
 * Description:
 *
 * @Author: ylqi007
 * @Create: 5/31/24 12:31
 */
public class SystemGCTest {
    public static void main(String[] args) {
        new SystemGCTest();
        System.gc(); // 提醒JVM的垃圾回收器执行GC,但是不确定是否马上执行GC
        // 与Runtime.getRuntime().gc()的作用一样。
        System.runFinalization();   // 强制调用使用引用的对象的finalize()方法
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("SystemGCTest重写了finalize()");
    }
}
