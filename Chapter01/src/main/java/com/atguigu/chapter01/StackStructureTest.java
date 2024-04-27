package com.atguigu.chapter01;

/**
 * Description:
 *
 * @Author: ylqi007
 * @Create: 4/20/24 15:04
 */
public class StackStructureTest {

    private static final int a = 0;

    public static void main(String[] args) {
        int i = 2;
        int j = 3;
        int k = i + j;
        System.out.println(k);

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Hello World!p");
    }
}
