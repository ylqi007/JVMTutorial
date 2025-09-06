package com.ylqi007.chap09methodarea.java1;

/**
 * 《深入理解Java虚拟机》中的案例：
 * staticObj、instanceObj、localObj存放在哪里？
 *
 * 三个对象的数据在内存中的地址都落在 Eden 范围内，所以结论：只要是对象实例，必然会在 heap 中分配。
 * @author shkstart  shkstart@126.com
 * @create 2020  11:39
 */
public class Demo03StaticObjTest {
    static class Test {
        static ObjectHolder staticObj = new ObjectHolder();
        ObjectHolder instanceObj = new ObjectHolder();

        void foo() {
            ObjectHolder localObj = new ObjectHolder();
            System.out.println("done");
        }
    }

    private static class ObjectHolder {
    }

    public static void main(String[] args) {
        Test test = new Test();
        test.foo();
    }
}
