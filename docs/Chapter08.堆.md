# Chapter08 堆(Heap)

## 8.1 堆的核心概述
<img src="JVM.Images.I/第02章_JVM架构-简图.jpg">

* 红色的方法区和堆对于一个进程而言，都是唯一的。

堆(Heap)是JVM运行时数据区(Runtime Data Area)占用内存最大的一块区域。每一个运行的Java程序对应一个JVM进程，每一个JVM进程只存在一个堆区，它在JVM启动时被创建。JVM规范中规定堆区可以是物理上不连续的内存，但必须是逻辑上连续的内存。
* 一个JVM实例只存在一个堆内存，堆也是Java内存管理的核心区域。
* Java堆区在JVM启动的时候即被创建，其空间大小也就确定了，是JVM管理的最大一块内存空间。
  * 堆内存的大小是可以调节的。
* 《Java虚拟机规范》规定，堆可以处于物理上不连续的内存空间中，但在逻辑上，它应该被视为连续的。
* 所有的线程共享Java堆，在这里还可以划分线程私有的缓冲区(Thread Local Allocation Buffer, TLAB)
* 《Java虚拟机规范》中对Java堆的描述是: 所有的对象实例以及数组都应当在运行时分配堆上。(The heap is the runtime data area from which memory for all class instances and arrays is allocated)。
  * 我(shk)要说的是:"几乎"所有的对象实例都在这里分配内存。--从实际使用角度看。
* 数组和对象可能永远不会存储在栈上，因为栈帧中保存引用，这个引用指向对象或者数组在堆中的位置。
* 在方法结束后，堆中的对象不会马上被移除，仅仅在垃圾收集的时候才会被移除。
* 堆，是GC(Garbage Collection, 垃圾收集器)执行垃圾回收的重点区域。

<img src="JVM.Images.I/第08章_Runtime.Data.Area_堆_栈_方法区.png">

* 栈负责运行
* 堆负责存储
* 方法区

### 8.1.1 堆的核心概述: 内存细分
现代垃圾回收器大部分都基于分代收集理论设计，堆空间细分为:
* Java7及以前的堆内存逻辑上分为三部分: 新生区 + 养老区 + **永久区**
  * Young Generation Space
    * 又被划分为Eden区和Survivor区
  * Tenure Generation Space
  * **Permanent Space**
* Java8及以后内存逻辑上分为三部分: 新生区 + 养老区 + **元空间**
  * Young Generation Space
    * 又被划分为Eden区和Survivor区
  * Tenure Generation Space
  * **Meta Space**
* 约定: 新生去 <==> 新生代 <==> 年轻代； 养老区 <==> 老年区 <==> 老年代；永久区 <==> 永久代

**堆内存细节，自JDK8开始有区别:**

<img src="JVM.Images.I/第08章_堆空间-java7.jpg">
<img src="JVM.Images.I/第08章_堆空间-java8.jpg">

<img src="JVM.Images.I/第08章_堆和方法区图.jpg">

Example: `com.atguigu.java.HeapDemo`
<img src="JVM.Images.I/第08章_VisualGC_新生代_老年代.png">

## 8.2 设置堆内存大小与OOM

## 8.3 年轻代与老年代

## 8.4 图解对象分配过程

## 8.5 Minor GC, Major GC, Full GC

## 8.6 堆空间分代思想

## 8.7 内存分配策略
## 8.8 为对象分配内存: TLAB
## 8.9 小结堆空间的参数设置
## 8.10 堆是分配对象的唯一选择吗？


## Reference
* 宋红康
* [第十二篇 JVM之运行时数据区<8>: Java堆](https://www.cnblogs.com/zhexuejun/p/15705428.html)
* [What Is a TLAB or Thread-Local Allocation Buffer in Java?](https://www.baeldung.com/java-jvm-tlab)
* [终于搞明白Java8内存结构](https://cloud.tencent.com/developer/article/1869201)