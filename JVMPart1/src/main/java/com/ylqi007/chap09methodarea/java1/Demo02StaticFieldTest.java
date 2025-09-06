package com.ylqi007.chap09methodarea.java1;

/**
 * 结论：
 *  静态引用对应的"对象实体"始终都存在堆空间
 *
 * jdk7：
 * -Xms200m -Xmx200m -XX:PermSize=300m -XX:MaxPermSize=300m -XX:+PrintGCDetails
 *
 * jdk 8：
 * -Xms200m -Xmx200m -XX:MetaspaceSize=300m -XX:MaxMetaspaceSize=300m -XX:+PrintGCDetails
 * @author shkstart  shkstart@126.com
 * @create 2020  21:20
 */
public class Demo02StaticFieldTest {
    // 变量名 arr 在 JDK 版本变化中，位置有变化
    private static byte[] arr = new byte[1024 * 1024 * 100];//100MB, new出来的对象是放在堆空间的

    public static void main(String[] args) {
        System.out.println(Demo02StaticFieldTest.arr);

        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
