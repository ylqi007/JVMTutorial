package com.atguigu.chapter02.classloader;

import sun.misc.Launcher;
import sun.security.ec.SunEC;


import java.net.URL;
import java.security.Provider;

/**
 * @Author: ylqi007
 * @Create: 4/27/24 13:59
 * @Description: 测试不同 ClassLoader 可以加载的类
 */
public class Demo02ClassLoaderTest1 {
    public static void main(String[] args) {

        testBootstrapClassLoader();

        System.out.println("--------------------------------------------------");
        // 从上面的路径中随意选择一个类，来看看它的类加载器是什么
        // Provider是jsse.jar中的一个类
        ClassLoader classLoader = Provider.class.getClassLoader();
        System.out.println(classLoader);  // null, 表明Provider的加载器就是“引导类加载器(Bootstrap ClassLoader)”


        System.out.println("--------------------------------------------------");
        testExtensionClassLoader();


        System.out.println("--------------------------------------------------");
        //  /Library/Java/JavaVirtualMachines/amazon-corretto-8.jdk/Contents/Home/jre/lib/ext/sunec.jar
        // 即SunEC的加载器为扩展类加载器(Extension ClassLoader)
        ClassLoader classLoader1 = SunEC.class.getClassLoader();  // sun.misc.Launcher$ExtClassLoader@2626b418,
        System.out.println(classLoader1);
    }

    /**
     * ====== 启动类加载器 (Bootstrap ClassLoader) =====
     * file:/Library/Java/JavaVirtualMachines/amazon-corretto-8.jdk/Contents/Home/jre/lib/resources.jar
     * file:/Library/Java/JavaVirtualMachines/amazon-corretto-8.jdk/Contents/Home/jre/lib/rt.jar
     * file:/Library/Java/JavaVirtualMachines/amazon-corretto-8.jdk/Contents/Home/jre/lib/sunrsasign.jar
     * file:/Library/Java/JavaVirtualMachines/amazon-corretto-8.jdk/Contents/Home/jre/lib/jsse.jar
     * file:/Library/Java/JavaVirtualMachines/amazon-corretto-8.jdk/Contents/Home/jre/lib/jce.jar
     * file:/Library/Java/JavaVirtualMachines/amazon-corretto-8.jdk/Contents/Home/jre/lib/charsets.jar
     * file:/Library/Java/JavaVirtualMachines/amazon-corretto-8.jdk/Contents/Home/jre/lib/jfr.jar
     * file:/Library/Java/JavaVirtualMachines/amazon-corretto-8.jdk/Contents/Home/jre/classes
     */
    private static void testBootstrapClassLoader() {
        System.out.println("====== 启动类加载器 (Bootstrap ClassLoader) =====");
        // 获取BootstrapClassLoader能够加载的API的路径
        URL[] urLs = Launcher.getBootstrapClassPath().getURLs();
        for(URL url: urLs) {
            System.out.println(url.toExternalForm());
        }
    }


    /**
     * ====== 扩展类加载器 (Extension ClassLoader) =====
     * /Users/ylqi007/Library/Java/Extensions:/Library/Java/JavaVirtualMachines/amazon-corretto-8.jdk/Contents/Home/jre/lib/ext:/Library/Java/Extensions:/Network/Library/Java/Extensions:/System/Library/Java/Extensions:/usr/lib/java
     * /Users/ylqi007/Library/Java/Extensions
     * /Library/Java/JavaVirtualMachines/amazon-corretto-8.jdk/Contents/Home/jre/lib/ext
     * /Library/Java/Extensions
     * /Network/Library/Java/Extensions
     * /System/Library/Java/Extensions
     * /usr/lib/java
     */
    private static void testExtensionClassLoader() {
        System.out.println("====== 扩展类加载器 (Extension ClassLoader) =====");
        String extDirs = System.getProperty("java.ext.dirs");
        System.out.println(extDirs);
        for(String dir: extDirs.split(":")) {
            System.out.println(dir);
        }
    }
}
