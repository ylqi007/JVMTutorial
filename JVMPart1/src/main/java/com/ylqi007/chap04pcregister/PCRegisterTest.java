package com.ylqi007.chap04pcregister;

/**
 * @Author: ylqi007
 * @Create: 08/31/25 10:28
 * @Create: 4/28/24 12:39
 *
 * javap -v PCRegisterTest
 *    public static void main(java.lang.String[]);
 *     descriptor: ([Ljava/lang/String;)V
 *     flags: (0x0009) ACC_PUBLIC, ACC_STATIC
 *     Code:
 *       stack=2, locals=5, args_size=1
 *          0: bipush        10
 *          2: istore_1
 *          3: bipush        20
 *          5: istore_2
 *          6: iload_1
 *          7: iload_2
 *          8: iadd
 *          9: istore_3
 *         10: ldc           #2                  // String abc
 *         12: astore        4
 *         14: getstatic     #3                  // Field java/lang/System.out:Ljava/io/PrintStream;
 *         17: iload_1
 *         18: invokevirtual #4                  // Method java/io/PrintStream.println:(I)V
 *         21: getstatic     #3                  // Field java/lang/System.out:Ljava/io/PrintStream;
 *         24: iload_3
 *         25: invokevirtual #4                  // Method java/io/PrintStream.println:(I)V
 *         28: return
 *
 * 第一列的0，2，3，5.... 就是指令地址(偏移地址)，就存放于 PC register中
 * 第二例的`bipush`, `istore_1`, ...操作指令
 */
public class PCRegisterTest {
    public static void main(String[] args) {
        int i = 10;
        int j = 20;
        int k = i + j;

        String str = "abc";

        System.out.println(i);
        System.out.println(k);
    }
}
