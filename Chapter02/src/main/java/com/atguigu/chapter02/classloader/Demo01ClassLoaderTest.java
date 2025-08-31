package com.atguigu.chapter02.classloader;

import sun.misc.Launcher;

/**
 * @Author: ylqi007
 * @Create: 4/27/24 12:39
 * @Description: 不同类加载器演示
 * 1. Bootstrap ClassLoader
 * 2. Extension ClassLoader
 * 3. Application ClassLoader
 */
public class Demo01ClassLoaderTest {
    public static void main(String[] args) {
        // 获取系统类加载器, System Class Loader。也叫做应用程序类加载器 Application Class Loader
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        // jdk8: sun.misc.Launcher$AppClassLoader@18b4aac2
        // jdk17: jdk.internal.loader.ClassLoaders$AppClassLoader@251a69d7
        System.out.println(systemClassLoader);

        // 通过系统类加载器，获取其上层: 扩展类加载器
        ClassLoader extensionClassLoader = systemClassLoader.getParent();
        // jdk8: sun.misc.Launcher$ExtClassLoader@49476842
        // jdk17: jdk.internal.loader.ClassLoaders$PlatformClassLoader@816f27d
        System.out.println(extensionClassLoader);

        // 试图获取其上层，但是得不到引导类加载器，即从 extensionClassLoader 无法获得 Bootstrap classloader
        // jdk8: null
        // jdk17: null
        ClassLoader bootstrapClassLoader = extensionClassLoader.getParent();
        System.out.println(bootstrapClassLoader);


        System.out.println("==================");
        testCustomClassLoader();


        System.out.println("==================");
        testStringClassLoader();
    }


    private static void testCustomClassLoader() {
        // 对于用户自定义类，它的类加载器是？ 默认使用系统类加载器进行加载
        // jdk8: sun.misc.Launcher$AppClassLoader@18b4aac2
        // jdk17: jdk.internal.loader.ClassLoaders$AppClassLoader@251a69d7
        ClassLoader classLoader1 = Demo01ClassLoaderTest.class.getClassLoader();
        System.out.println(classLoader1);
    }


    private static void testStringClassLoader() {
        // String类使用引导类加载器进行加载的。--> Java的核心类库都是使用引导类加载器进行加载的。
        // 即 String 类是使用 Bootstrap class loader 加载的
        // jdk8: null
        // jdk17: null
        ClassLoader classLoader2 = String.class.getClassLoader();
        System.out.println(classLoader2);
    }


    private static void testExtClassLoader() {
        // ExtClassLoader 是 Launcher 的内部类
        Launcher launcher = new Launcher();
    }
}
