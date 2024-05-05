package com.atguigu.java;

/**
 * Description:
 *
 * native 和 abstract不能共用
 *  native是有方法体的
 *  abstract是没有方法体的
 * @Author: ylqi007
 * @Create: 5/5/24 09:43
 */
public class IHaveNatives {
    public native void Native1(int x);

    native static public long Native2();

    native synchronized private float Native3(Object o);

    native void Native4(int[] arr) throws Exception;
}
