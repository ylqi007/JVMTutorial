package com.atguigu.chapter02.classloader;

import sun.misc.Launcher;
import sun.security.ec.SunEC;


import java.net.URL;
import java.security.Provider;

/**
 * Description:
 *
 * @Author: ylqi007
 * @Create: 4/27/24 13:59
 */
public class ClassLoaderTest1 {
    public static void main(String[] args) {
        System.out.println("====== 启动类加载器 =====");
        // 获取BootstrapClassLoader能够加载的API的路径
        URL[] urLs = Launcher.getBootstrapClassPath().getURLs();
        for(URL url: urLs) {
            System.out.println(url.toExternalForm());
        }

        // 从上面的路径中随意选择一个类，来看看它的类加载器是什么
        // Provider是jsse.jar中的一个类
        ClassLoader classLoader = Provider.class.getClassLoader();
        System.out.println(classLoader);  // null, 表明Provider的加载器就是“引导类加载器”

        System.out.println("====== 扩展类加载器 =====");
        String extDirs = System.getProperty("java.ext.dirs");
        for(String dir: extDirs.split(":")) {
            System.out.println(dir);
        }

        //  /Library/Java/JavaVirtualMachines/amazon-corretto-8.jdk/Contents/Home/jre/lib/ext/sunec.jar
        // 即SunEC的加载器为扩展类加载器
        ClassLoader classLoader1 = SunEC.class.getClassLoader();  // sun.misc.Launcher$ExtClassLoader@2626b418,
        System.out.println(classLoader1);
    }
}
