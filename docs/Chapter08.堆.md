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
* Java堆区用于存储Java对象实例，那么堆的大小在JVM启动时就已经设定好了，大家可以通过选项`-Xmx`和`-Xms`来进行设置。
  * `-Xms`用于表示堆区的起始内存，等价于`-XX:InitialHeapSize`
  * `-Xmx`用于表示堆区的最大内存，等价于`-XX:MaxHeapSize`
  * `-Xmn`用于设置年轻代内存，一般不用，而是用`-XX:NewRatio`
* 一旦堆区中的内存大小超过`-Xmx`所指定的最大内存时，将会抛出`OutOfMemoryError`异常。
* 通常会将`-Xms`和`-Xmx`两个参数配置相同的值，其目的是为了能够在Java垃圾回收机制清理完堆区后，不需要再重新分隔计算堆区的大小，从而提高性能。
* 默认情况下: 
  * 初始内存大小: 物理内存大小/64
  * 最大内存大小: 物理内存大小/4

<img src="JVM.Images.I/第08章_OOMTest.png">

## 8.3 年轻代与老年代
* 存储在JVM中的Java对象可以划分为两类：
  * 一类是生命周期比较短的瞬时对象，这类对象的创建和消亡都非常迅速。
  * 另一类对象的生命周期却非常长，在某些极端的情况下还能够与JVM的生命周期保持一致。
* Java堆区进一步细分的话，可以划分为年轻代(YoungGen)和老年代(OldGen)。
* 其中年轻代又可以划分为Eden空间、Survivor0空间和Survivor1空间(有时也叫做from区和to区)
* <img src="JVM.Images.I/第08章_堆空间细节.jpg">
* 1. 伊甸区(Eden): 存放大部分新创建对象。
  2. 幸存区(Survivor): 存放Minor GC之后，Eden区和幸存区(Survivor)本身没有被回收的对象。
  3. 老年区: 存放Minor GC之后且年龄计算器达到15依然存活的对象、Major GC和Full GC之后仍然存活的对象。
* 在HotSpot中，Eden空间和另外两个Survivor空间缺省所占比例是8:1:1
* 当然，开发人员可以通过选项`-XX:SurvivorRatio`调整这个比例。比如`-XX:SurvivorRatio=8`
* **几乎**所有的Java对象都是在Eden区中被new出来的。
* 绝大部分的Java对象的销毁都在新生代进行了。
  * IBM公司的专门研究表明，新生代中80%的对象都是"朝生夕死"的。
* 可以使用选项`-Xmn`设置新生代最大内存大小
  * 这个参数一般使用默认值就可以了

**下面这些参数开发中一般不会调:**
1. 配置新生代与老年代在堆结构中的占比:
   * 默认`-XX:NewRatio=2`，表示新生代占1，老年代占2，新生代占整个堆的1/3
   * 可以修改`-XX:NewRatio=4`，表示新生代占1，老年代占4，新生代占整个堆的1/5
   * <img src="JVM.Images.I/第08章_YoungOldRatio_1.png">
2. 配置Eden空间和另外两个Survivor空间所占比例
   * 默认是`-XX:SurvivorRatio=8`
   * <img src="JVM.Images.I/第08章_SurvivorRatio_0.png">
   * <img src="JVM.Images.I/第08章_SurvivorRatio_1.png">

可以通过CLI查看`NewRatio`
```shell
➜  JVMTutorial git:(main) ✗ jps
92657 Jps
22930 Main
1332 
23481 
92303 EdenSurvivorTest
92302 Launcher
➜  JVMTutorial git:(main) ✗ jinfo -flag NewRatio 92303
-XX:NewRatio=2
➜  JVMTutorial git:(main) ✗ jinfo -flag SurvivorRatio 92303 
-XX:SurvivorRatio=8
➜  JVMTutorial git:(main) ✗ jstat -gc 92303
    S0C         S1C         S0U         S1U          EC           EU           OC           OU          MC         MU       CCSC      CCSU     YGC     YGCT     FGC    FGCT     CGC    CGCT       GCT   
    25600.0     25600.0         0.0         0.0     153600.0     110603.7     409600.0          0.0     4480.0      779.6     384.0      76.0      0     0.000     0     0.000     -         -     0.000
➜  JVMTutorial git:(main) ✗ 
```


## 8.4 图解对象分配过程
### 8.4.1 对象分配过程: 概述
为新对象分配内存是一件非常严谨和复杂的任务，JVM的设计者们不仅仅需要考虑内存如何分配、在哪里分配等问题，并且由于内存分配算法与内存回收算法密切相关，所以还需要考虑GC执行完后回收是否在内存空间中产生内存碎片。

1. new的对象先放在伊甸园区。此区有大小限制。
2. 当伊甸园区空间填满时，程序有需要创建对象，JVM的垃圾回收器将对伊甸园区进行垃圾回收(Minor GC)，将Eden区中不在被其他对象所引用的对象进行销毁。再加载新的对象放到Eden区。
   * <img src="JVM.Images.I/第08章_新生代对象分配与回收过程.jpg">
3. 然后将Eden区中的剩余对象移动到Survivor0区。
4. 如果再次触发垃圾回收，此时上次幸存下来的放到Survivor0区的，如果没有被回收，就会放到Survivor1区。
5. 如果再次经历垃圾回收，此时会重新放回Survivor0区，接着再去Survivor1区。
6. 什么时候去老年区呢？可以设置次数，默认是15次。
   * 可以设置参数: `-XX:MaxTenuringThreshold=N`进行设置
7. 在老年去，相对悠闲。当老年区内存不足时，再次触发GC: Major GC，进行养老区的内存清理。
8. 若养老区执行了Major GC之后发现，依然无法进行对象的保存，就会产生OOM异常。
   * `java.lang.OutOfMemoryError: Java heap space`

**总结:**
* 针对幸存者区s0, s1区的总结: 复制之后有交换，谁空谁时to。
* 关于垃圾回收: 频繁在新生区收集，很少在养老区收集，几乎不在永久区/元空间收集。

### 8.4.2 对象分配的特殊情况
<img src="JVM.Images.I/第08章_Java对象内存分配.png">

<img src="JVM.Images.I/第08章_对象分配过程_JVisualVM.png">

### 8.4.3 常用的调优工具
* JDK命令行
* Eclipse: Memory Analyzer Tool
* Jconsole
* VisualVM
* Jprofiler
* Java Flight Recorder
* GCViewer
* GC Easy

<img src="JVM.Images.I/第08章_常用调优工具_Jprofiler.png">

## 8.5 Minor GC, Major GC, Full GC
JVM在进行GC时，并非每次都在对三个内存(新生代、老年代; 方法区)区域一起回收的，大部分时间回收的都是新生代。

针对HotSpot VM的实现，它里面的GC按照回收区域又分为:
* 部分收集: 不是完整收集整个Java堆的垃圾收集。其中又分为
  * 新生代收集(Minor GC/Young GC): 只是新生代(Eden, S0, S1)的垃圾回收
  * 老年代收集(Major GC/Old GC): 只是老年代的垃圾回收
    * 目前，只有CMS GC会有单独收集老年代的行为
    * 注意: 很多时候Major GC会和Full GC混淆使用，需要具体分辨是老年代回收还是整堆回收。
  * 混合收集(Mixed GC): 收集整个新生代以及部分老年代的垃圾收集。
    * 目前，只有G1 GC会有这种行为。
* 整堆收集(Full GC): 收集整个Java堆(新生代+老年代)和方法区的垃圾收集。

### 8.5.1 Minor GC
年轻代GC(Minor GC)触发机制:
* 当年轻代空间不足时，就会触发Minor GC，这里的年轻代满指的是Eden代满，Survivor满并不会触发GC。(每次Minor GC会清理年轻代的内存。) 
* 因为Java对象大多都具备朝生夕死的特性，所以Minor GC非常频繁，一般回收速度也会比较快。这一定义既清晰又易于理解。
* Minor GC会引发STW，暂停其他用户的线程，等垃圾回收结束，用户线程才会恢复运行。

### 8.5.2 Major GC
老年代GC(Major GC/Full GC)触发机制:
* 指发生在老年代的GC，对象从老年代消失时，我们说"Major GC"或"Full GC"发生了。
* 出现Major GC，经常会伴随至少依次的Minor GC(但非绝对的，在Parallel Scavenge收集器的收集策略里就有直接进行Major GC的策略选择过程)
  * 也就是在老年代空间不足时，会先尝试触发Minor GC。如果之后空间还不足，则触发Major GC
* Major GC的速度一般比Minor GC慢10倍以上，STW的时间更长。
* 如果Major GC后，内存还不足，就报OOM了。

### 8.5.3 Full GC
Full GC触发机制: 触发Full GC执行的情况有如下五种:
1. 调用`System.gc()`时，系统建议执行Full GC，但是不必然执行。
2. 老年代空间不足
3. 方法区空间不足
4. 通过Minor GC后进入老年代的平均大小大于老年代的可用内存
5. 由Eden区, survivor space0 (from space)区向survivor space1 (to space)区复制时，对象大小大于 to space可用的内存，则把对象转存到老年代，且老年代的可用内存大小小于该对象。

说明: full gc是开发或调优中尽量要避免的。这样暂停时间会短一些。

### 8.5.4 GC举例与日志分析
OOM通常都伴随着Full GC
<img src="JVM.Images.I/第08章_GCTest.png">

Minor GC example: `[GC (Allocation Failure) [PSYoungGen: 2048K->512K(2560K)] 2048K->772K(9728K), 0.0007769 secs] [Times: user=0.00 sys=0.01, real=0.01 secs]`
* 2048K: 指的是Minor GC之前，新生代占用情况
* 512K: 指的是Minor GC之后，新生代的情况。由于包含了survivor区，所以并不为0
* 2560K: 新生代总空间大小。

## 8.6 堆空间分代思想
为什么需要把Java堆分代？不分代就不能正常工作吗？
* 经研究，不同对象的生命周期不同。70%-90%的对象是临时对象。
  * 新生代: 有Eden，两块大小相同的Survivor(又被称为from/to, s0/s1)构成，to总为空。
  * 老年代: 存放新生代中经历多次GC仍然存活的对象。
* 其实不分代完全可以，分代的唯一理由就是优化GC性能。如果没有分代，那所有的对象都在一起，就如同把一个学校的人都关在一个教室。GC的时候要找到哪些对象没用，这样就会对所有区域进行扫描。而很多时候对象都是朝生夕死的，如果分代的话，把新创建的对象放到某一地方，当GC的时候先把这块存储"朝生夕死"的对象进行回收，这样就会腾出很大的空间。


## 8.7 内存分配策略(或对象提升(Promotion)规则)
如果对象在Eden出生并经过第一次Minor GC后仍然存活，并且能被Survivor容纳的话，将会被移动到Survivor空间中，并将对象年龄设置为1。对象在Survivor区中每熬过一次Minor GC，年龄就增加1岁，当它的年龄增加到了一定程度(默认为15岁，其实每个JVM、每个GC都有不同)时，就会被晋升到老年代中。

对象晋升老年代的年龄阈值，可以通过选项`-XX:MaxTenuringThreshold`来设置。

**针对不同年龄段的对象分配原则如下所示:**
* 优先分配到Eden
* 大对象直接分配到老年代
  * 尽量避免程序中出现过多的大对象
* 长期存活的对象分配到老年代
* 动态对象年龄判断
  * 如果Survivor区中相同年龄的所有对象大小总和大于Survivor空间的一半，年龄大于或等于该年龄的对象可以直接进入老年代，无需等到MaxTenuringThreshold中要求的年龄。
* 空间分配担保
  * `-XX:HandlePromotionFailure`


## 8.8 为对象分配内存: TLAB
**为什么有TLAB(Thread Local Allocation Buffer):**
* 堆区是线程共享区域，任何线程都可以访问到堆区中的共享数据。
* 由于对象实例的创建在JVM中非常频繁，因此在并发环境下从堆区中划分内存空间是线程不安全的。
* 为了避免多个线程操作同一地址，需要使用加锁等机制，进而影响分配速度。

**什么是TLAB?:**
* 从内存模型而不是垃圾回收的角度，对Eden区域继续进行划分，JVM为每个线程分配了一个私有的缓存区域，它包含在Eden空间中。
* 多线程同时分配内存时，使用TLAB可以避免一系列的非线程安全问题。同时还能够提升内存分配的吞吐量，因此我们可以将这种内存分配方式称为**快速分配策略**。
* 所有OpenJDK衍生出来的JVM都提供了TLAB的设计。

**TLAB的再说明:**
* 尽管不是所有的对象实例都能够在TLAB中成功分配内存，但JVM确实是将TLAB作为内存分配的首选。
* 在程序中，开发人员可以通过选项`-XX:UseTLAB`设置是否开启TLAB空间。
* 默认情况下，TLAB空间的内存非常小，仅占整个Eden空间的1%，当然我们可以通过选项`-XX:TLABWasteTargetPercent`设置TLAB空间所占用Eden空间的百分比大小。
* 一旦对象在TLAB空间分配内存失败时，JVM就会尝试着通过使用加锁机制确保数据操作的原子性，从而直接在Eden空间中分配内存。

```shell
➜  JVMTutorial git:(main) ✗ jps
37136 Launcher
37137 TLABArgsTest
37219 Jps
1332 
27544 Main
23481 
➜  JVMTutorial git:(main) ✗ jinfo -flag UseTLAB 37137
-XX:+UseTLAB
```

<img src="JVM.Images.I/第08章_对象分配过程.jpg">

## 8.9 小结堆空间的参数设置
## 8.10 堆是分配对象的唯一选择吗？


## Reference
* 宋红康
* [Java HotSpot VM](https://www.oracle.com/java/technologies/javase/vmoptions-jsp.html)
* [Java Platform, Standard Edition Tools Reference - index](https://docs.oracle.com/javase/8/docs/technotes/tools/unix/index.html)
* [Java Platform, Standard Edition Tools Reference - Java](https://docs.oracle.com/javase/8/docs/technotes/tools/unix/java.html) JVM参数
* [第十二篇 JVM之运行时数据区<8>: Java堆](https://www.cnblogs.com/zhexuejun/p/15705428.html)
* [What Is a TLAB or Thread-Local Allocation Buffer in Java?](https://www.baeldung.com/java-jvm-tlab)
* [终于搞明白Java8内存结构](https://cloud.tencent.com/developer/article/1869201)