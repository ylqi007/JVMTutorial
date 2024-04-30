## Q&A
### 1. 在学习JVM之前，要先学并发编程吗？

概述：可以将JVM分成三层
1. Class Loader Subsystem，类加载子系统
2. 运行时数据区，chap03～10 + chap12（String在不同JDK版本中有变化）
3. 执行引擎
   1. 解释器
   2. 即时编译器
   3. 垃圾回收

第二部分：垃圾回收
1. 算法：如何判断是否为垃圾
2. 垃圾回收器，未来发展方向


## 为什么要学习JVM？
> 如果你想成为公司不可或缺的顶梁柱，那 JVM 你得学，因为一般情况下，遇到的问题基本上 Google 下就能解决了，可一旦遇到 JVM 性能调优，就必须得有能查 OOM 的原因、能看懂字节码的老鸟出马了。
> 
> 应用程序一旦上线，出问题是板上钉钉的事，除了数据库、网络、代码逻辑上的问题，剩下的就有内存溢出啊，频繁 GC 导致的性能瓶颈啊等棘手问题。
> 
> 遇到这种问题，你就必须得能看懂 GC 日志，明白什么是老年代、永久代、元数据区等，这些都是 Java 虚拟机方面的知识。


## JVM的学习思路
三大部分:
1. 字节码与类的加载、
2. 内存与垃圾回收、
3. 性能监控和调优。

<img src="docs/images/沉默的王二_JVM学习思维导图.png" width="800">

## Reference
* [沉默王二: 二哥的Java进阶之路x沉默王二](https://javabetter.cn/home.html#%E4%B8%BA%E4%BB%80%E4%B9%88%E4%BC%9A%E6%9C%89%E8%BF%99%E4%B8%AA%E5%BC%80%E6%BA%90%E7%9F%A5%E8%AF%86%E5%BA%93)
* [沉默王二: 2024年最值得收藏的JVM学习路线](https://javabetter.cn/xuexiluxian/java/jvm.html)
* [Medium: Understanding JVM Architecture](https://medium.com/platform-engineer/understanding-jvm-architecture-22c0ddf09722)
* [20张图助你了解JVM运行时数据区，你还觉得枯燥吗？](https://cloud.tencent.com/developer/article/1823397)


## Initialize Repository and Setup
1. Create a new repository on the command line
   ```shell
   echo "# JVMTutorial" >> README.md
   git init
   git add README.md
   git commit -m "first commit"
   git branch -M main
   git remote add origin git@github.com:ylqi007/JVMTutorial.git
   git push -u origin main
   ```
2. Push an existing repository from the command line
   ```shell
   git remote add origin git@github.com:ylqi007/JVMTutorial.git
   git branch -M main
   git push -u origin main
   ```
3. Import code from another repository 
   * You can initialize this repository with code from a Subversion, Mercurial, or TFS project.


## Reference
* https://github.com/vectorxxxx/NOTE_JVM