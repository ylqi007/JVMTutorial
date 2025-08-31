package com.atguigu.chapter02.classloader;

/**
 * @Author: ylqi007
 * @Create: 4/27/24 14:58
 * @Description: 获取 ClassLoader 的途径
 *  1. 获取当前类的 ClassLoader : clazz.getClassLoader()
 *  2. 获取当前线程上下文的 ClassLoader: Thread.currentThread().getContextClassLoader()
 *  3. 获取系统的 ClassLoader: ClassLoader.getSystemClassLoader()
 *  4. 获取调用者的 ClassLoader: DriverManager.getCallerClassLoader()
 */
public class Demo04ClassLoaderTest2 {
    public static void main(String[] args) {
        try {
            //1. 获取当前类的 ClassLoader : clazz.getClassLoader()
            ClassLoader classLoader = Class.forName("java.lang.String").getClassLoader();
            System.out.println(classLoader);    // null ==> 说明是由 Bootstrap ClassLoader 加载

            //2. 通过线程
            ClassLoader classLoader1 = Thread.currentThread().getContextClassLoader();  // sun.misc.Launcher$AppClassLoader@18b4aac2
            System.out.println(classLoader1);

            //3. ClassLoader.getSystemClassLoader()
            ClassLoader classLoader2 = ClassLoader.getSystemClassLoader().getParent();  // sun.misc.Launcher$ExtClassLoader@3764951d
            System.out.println(classLoader2);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
