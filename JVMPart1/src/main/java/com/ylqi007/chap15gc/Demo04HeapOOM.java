package com.ylqi007.chap15gc;

import java.util.ArrayList;

/**
 * -Xms8m -Xmx8m -XX:+HeapDumpOnOutOfMemoryError
 *
 * @author shkstart  shkstart@126.com
 * @create 2020  15:29
 */
public class Demo04HeapOOM {
    byte[] buffer = new byte[1 * 1024 * 1024];//1MB

    public static void main(String[] args) {
        ArrayList<Demo04HeapOOM> list = new ArrayList<>();

        int count = 0;
        try{
            while(true){
                list.add(new Demo04HeapOOM());
                count++;
            }
        }catch (Throwable e){
            System.out.println("count = " + count);
            e.printStackTrace();
        }
    }
}
