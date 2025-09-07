package com.ylqi007.chap13stringtable.demo01;

import com.ylqi007.utils.CommonUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *  -XX:StringTableSize=1009, 花费的时间为：123ms
 *  -XX:StringTableSize=100009, 花费的时间为：26ms
 * @author shkstart  shkstart@126.com
 * @create 2020  23:53
 *
 * ➜  ~ jinfo -flag StringTableSize 95748
 * -XX:StringTableSize=60013
 *
 * ➜  ~ jinfo -flag StringTableSize 95853
 * -XX:StringTableSize=1009
 *
 * JDK8 StringTableSize 有最小的要求
 * StringTable size of 1000 is invalid; must be between 1009 and 2305843009213693951
 * Error: Could not create the Java Virtual Machine.
 * Error: A fatal exception has occurred. Program will exit.
 *
 * 1009
 * 我来打个酱油
 * 花费的时间为：58
 *
 * 100009
 * 我来打个酱油
 * 花费的时间为：20
 */
public class StringTest2 {
    public static void main(String[] args) {
        //测试StringTableSize参数
        System.out.println("我来打个酱油");

        // CommonUtils.sleepMillis(1000000);

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("words.txt"));
            long start = System.currentTimeMillis();
            String data;
            while((data = br.readLine()) != null){
                data.intern(); //如果字符串常量池中没有对应data的字符串的话，则在常量池中生成
            }

            long end = System.currentTimeMillis();

            System.out.println("花费的时间为：" + (end - start));//1009:143ms  100009:47ms
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(br != null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
