package com.ylqi007.chap08heap.demo1;

/**
 * @author shkstart  shkstart@126.com
 * @create 2020  17:28
 */
public class Demo03SimpleHeap {
    private int id;//属性、成员变量

    public Demo03SimpleHeap(int id) {
        this.id = id;
    }

    public void show() {
        System.out.println("My ID is " + id);
    }
    public static void main(String[] args) {
        Demo03SimpleHeap sl = new Demo03SimpleHeap(1);
        Demo03SimpleHeap s2 = new Demo03SimpleHeap(2);

        int[] arr = new int[10];

        Object[] arr1 = new Object[10];
    }
}
