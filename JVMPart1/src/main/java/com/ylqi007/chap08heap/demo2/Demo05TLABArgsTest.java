package com.ylqi007.chap08heap.demo2;

/**
 * 测试-XX:UseTLAB参数是否开启的情况:默认情况是开启的
 *
 * @author shkstart  shkstart@126.com
 * @create 2020  16:16
 *
 * ~ jinfo -flag UseTLAB 5914
 * -XX:+UseTLAB  ==> 默认情况是开启的
 */
public class Demo05TLABArgsTest {
    public static void main(String[] args) {
        System.out.println("我只是来打个酱油~");
        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
