package com.ylqi007.chap08heap.demo1;

/**
 * 1. 设置堆空间大小的参数
 * -Xms 用来设置堆空间（年轻代+老年代）的初始内存大小, 不包括 MetaSpace
 *      -X 是jvm的运行参数
 *      ms 是memory start
 * -Xmx 用来设置堆空间（年轻代+老年代）的最大内存大小, 不包括 MetaSpace
 *
 * 2. 默认堆空间的大小
 *    初始内存大小：物理电脑内存大小 / 64
 *             最大内存大小：物理电脑内存大小 / 4
 * 3. 手动设置：-Xms600m -Xmx600m
 *     开发中建议将初始堆内存和最大的堆内存设置成相同的值。
 *
 *
 * 4. 查看设置的参数：方式一： jps   /  jstat -gc 进程id
 *                  方式二：-XX:+PrintGCDetails
 * @author shkstart  shkstart@126.com
 * @create 2020  20:15
 *
 * Divided by 1024:
 * -Xms : 736M
 * -Xmx : 10923M
 * 系统内存大小为：46.0G
 * 系统内存大小为：42.66796875G
 *
 * Divided by 1000:
 * -Xms : 771M
 * -Xmx : 11453M
 * 系统内存大小为：49.344G
 * 系统内存大小为：45.812G
 */
public class Demo04HeapSpaceInitial {
    private static final int ONE_THOUSAND = 1000;
    private static final int ONE_KILO = 1024;
    public static void main(String[] args) {


        //返回Java虚拟机中的堆内存总量
        long initialMemory = Runtime.getRuntime().totalMemory() / ONE_KILO / ONE_KILO;
        //返回Java虚拟机试图使用的最大堆内存量
        long maxMemory = Runtime.getRuntime().maxMemory() / ONE_KILO / ONE_KILO;

        System.out.println("-Xms : " + initialMemory + "M");
        System.out.println("-Xmx : " + maxMemory + "M");

        System.out.println("系统内存大小为：" + initialMemory * 64.0 / ONE_KILO + "G");
        System.out.println("系统内存大小为：" + maxMemory * 4.0 / ONE_KILO + "G");
//
//        try {
//            Thread.sleep(1000000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
    }
}
