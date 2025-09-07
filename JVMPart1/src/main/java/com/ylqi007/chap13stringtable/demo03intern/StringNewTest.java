package com.ylqi007.chap13stringtable.demo03intern;

/**
 * 题目：
 * new String("ab")会创建几个对象？看字节码，就知道是两个。
 *     一个对象是：new关键字在堆空间创建的
 *     另一个对象是：字符串常量池中的对象"ab"。 字节码指令：ldc
 * 查看字节码：
 *  0 new #2 <java/lang/String>  ==> new关键字在heap中创建对象
 *  3 dup
 *  4 ldc #3 <ab>               ==> 在字符串常量池中创建对象"ab"
 *  6 invokespecial #4 <java/lang/String.<init> : (Ljava/lang/String;)V>
 *  9 astore_1
 * 10 return
 *
 *
 * 思考：
 * new String("a") + new String("b")呢？
 *  对象1：new StringBuilder()
 *  对象2： new String("a")
 *  对象3： 常量池中的"a"
 *  对象4： new String("b")
 *  对象5： 常量池中的"b"
 *
 *  深入剖析： StringBuilder的toString():
 *      对象6 ：new String("ab")
 *       强调一下，toString()的调用，在字符串常量池中，没有生成"ab"
 *
 * 字节码：
 *  0 new #2 <java/lang/StringBuilder>
 *  3 dup
 *  4 invokespecial #3 <java/lang/StringBuilder.<init> : ()V>
 *  7 new #4 <java/lang/String>
 * 10 dup
 * 11 ldc #5 <a>
 * 13 invokespecial #6 <java/lang/String.<init> : (Ljava/lang/String;)V>
 * 16 invokevirtual #7 <java/lang/StringBuilder.append : (Ljava/lang/String;)Ljava/lang/StringBuilder;>
 * 19 new #4 <java/lang/String>
 * 22 dup
 * 23 ldc #8 <b>
 * 25 invokespecial #6 <java/lang/String.<init> : (Ljava/lang/String;)V>
 * 28 invokevirtual #7 <java/lang/StringBuilder.append : (Ljava/lang/String;)Ljava/lang/StringBuilder;>
 * 31 invokevirtual #9 <java/lang/StringBuilder.toString : ()Ljava/lang/String;>
 * 34 astore_1
 * 35 return
 *
 *
 * @author shkstart  shkstart@126.com
 * @create 2020  20:38
 */
public class StringNewTest {
    public static void main(String[] args) {
        // Constant Pool 中有 "ab"
        String str = new String("ab");

        // Constant Pool 中没有 "ab"
//         String str = new String("a") + new String("b");
    }

//    private static void test01() {
//        String str = new String("ab");
//    }
}
