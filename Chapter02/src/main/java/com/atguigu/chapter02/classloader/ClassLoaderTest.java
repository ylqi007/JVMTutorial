package com.atguigu.chapter02.classloader;

/**
 * Description:
 *
 * @Author: ylqi007
 * @Create: 4/27/24 12:39
 */
public class ClassLoaderTest {
    public static void main(String[] args) {
        // 获取系统类加载器
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        // jdk8: sun.misc.Launcher$AppClassLoader@18b4aac2
        // jdk17: jdk.internal.loader.ClassLoaders$AppClassLoader@251a69d7
        System.out.println(systemClassLoader);

        // 通过系统类加载器，获取其上层: 扩展类加载器
        ClassLoader classLoader = systemClassLoader.getParent();
        // jdk8: sun.misc.Launcher$ExtClassLoader@49476842
        // jdk17: jdk.internal.loader.ClassLoaders$PlatformClassLoader@816f27d
        System.out.println(classLoader);

        // 试图获取其上层，但是得不到引导类加载器
        // jdk8: null
        // jdk17: null
        ClassLoader bootstrapClassLoader = classLoader.getParent();
        System.out.println(bootstrapClassLoader);

        // 对于用户自定义类，它的类加载器是？ 默认使用系统类加载器进行加载
        // jdk8: sun.misc.Launcher$AppClassLoader@18b4aac2
        // jdk17: jdk.internal.loader.ClassLoaders$AppClassLoader@251a69d7
        ClassLoader classLoader1 = ClassLoaderTest.class.getClassLoader();
        System.out.println(classLoader1);

        // String类使用引导类加载器进行加载的。--> Java的核心类库都是使用引导类加载器进行加载的。
        // jdk8: null
        // jdk17: null
        ClassLoader classLoader2 = String.class.getClassLoader();
        System.out.println(classLoader2);
    }
}
