package com.atguigu.chapter02.classloader;

import java.io.IOException;
import java.io.InputStream;

/**
 * @Author: ylqi007
 * @Date: 8/30/25  9:26 PM
 * @Description: 不同的类加载器对 instanceof 关键字运算的结果的影响
 */
public class Demo05ClassLoaderTest3 {
    public static void main(String[] args) throws Exception {
        // 构建一个简单的类加载器
        ClassLoader myClassLoader = new ClassLoader() {
            @Override
            public Class<?> loadClass(String name) throws ClassNotFoundException {
                try {
                    String fileName = name.substring(name.lastIndexOf(".")  + 1) + ".class";
                    InputStream is = getClass().getResourceAsStream(fileName);
                    if(is == null) {
                        return super.loadClass(name);
                    }
                    byte[] b = new byte[is.available()];
                    is.read(b);
                    return defineClass(name, b, 0, b.length);
                } catch (IOException e) {
                    throw new ClassNotFoundException(name);
                }
            }
        };

        Object obj = myClassLoader.loadClass("com.atguigu.chapter02.classloader.Demo05ClassLoaderTest3").newInstance();

        System.out.println(obj.getClass());
        System.out.println(obj.getClass().getClassLoader());
        System.out.println(obj instanceof com.atguigu.chapter02.classloader.Demo05ClassLoaderTest3);
    }
}
