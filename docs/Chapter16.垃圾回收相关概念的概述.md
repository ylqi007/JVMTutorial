# Chapter16. 垃圾回收相关概念

## 16.1 System.gc()的理解
* 在默认情况下，通过`System.gc()`或者`Runtime.getRuntime().gc()`的调用，会显式触发Full GC，同时堆老年代和新生代进行垃圾回收，尝试释放被丢弃对象占用的内存。
* 然而`System.gc()`调用附带一个免责声明，无法保证对垃圾收集器的调用。
* JVM实现者可以通过`System.gc()`调用来决定JVM的GC行为。而一般情况下，垃圾回收应该是自动进行的，无须手动触发，否则就太过于麻烦了。在一些特殊情况下，如我们正在编写一个性能基准，我们可以在运行之间调用`System.gc()`。

## 16.2 内存溢出与内存泄漏
### 1. 内存溢出的分析(OOM, 堆 heap)
* 内存溢出相对于内存泄漏来说，尽管更容易被理解，但是同样的，内存溢出也是引发程序崩溃的罪魁祸首之一。
* 由于GC一直在发展，所有一般情况下，除非应用程序占用的内存增长速度非常快，造成垃圾回收已经跟不上内存消耗的速度，否则不太统一出现OOM的情况。
* 大多数情况下，GC会进行各种年龄段的垃圾回收，是在不行了就放大招，来一次独占式的Full GC操作，这时候会回收大量的内存，供应用乘车继续使用。
* javadoc中对`OutOfMemoryError`的解释是，没有空闲的内存，并且垃圾收集器也无法提供更多的内存。
  * 首先说没有空闲内存的情况: 说明Java虚拟机的堆内存不够，原因有二:
  * 1. Java虚拟机的堆内存设置不够。比如: 可能存在的内存泄漏问题；也很有可能是堆的大小不合理，比如我们要处理比较客观的数据量，但是没有显示指定JVM堆大小或者指定数值偏小。我们可以通过参数`-Xms,-Xmx`来调整。
  * 2. 代码中创建了大量的大对象，并且长时间不能被垃圾收集器回收(存在被引用)。对于老版本的Oracle JDK，因为永久代的大小是非常有限的，并且JVM对永久代垃圾回收(比如, 常量池回收，卸载不在需要的类型)非常不积极，所以当我们不断田间新类型的时候，永久代出现`OutOfMemoryError`也非常多见，尤其是在运行时存在大量动态类型的生成场合; 类型intern字符串缓存占用太多空间，也会导致OOM问题。堆用的异常信息，会标记出来和永久代相关: `java.lang.OutOfMemoryError: PermGen space`
  * 3. 随着元数据区的引入，方法区内存已经不在那么窘迫，所以相应的OOM有所改观，出现OOM，异常信息则编程了`java.lang.OutOfMemoryError: Metaspace`。直接内存不足，也会导致OOM。
* 这里面隐含一层意思是，在抛出`OutOfMemoryError`之前，通常垃圾收集器会被触发，尽其所能去清理出空间。
  * 例如: 在引用机制分析中，涉及到JVM会尝试回收软**引用指向的对象等**。
  * 在`java.nio.BIts.reverseMemory()`方法中，我们能清除的看到，`System.gc()`会被调用，以清理空间。
* 当然，也不是在任何情况下垃圾收集器都会被触发的。
  * 比如: 我们去分配一个超大对象，类似一个超大数组超过堆的最大值，JVM可以判断出垃圾收集并不能解决这个问题，所以直接抛出`OutOfMemoryError`。

### 2. 内存泄漏(Memory Leak)的分析
严格来说，只有对象不会再被程序用到了，但是GC又不能回收它们的情况，才叫做内存泄漏。

但实际情况很多时候一些不太好的实践(或疏忽)会导致对象的声明周期变得很长甚至导致OOM，也可以叫做宽泛意义上的"内存泄漏"。

尽管内存泄漏并不会立刻引起程序的崩溃，但是一旦发生内存泄漏，程序中的可用内存就会被逐步蚕食，直至耗尽所有内存，最终出现`OutOfMemory`异常，导致程序崩溃。

注意，这里的存储空间并不是指物理内存，而是虚拟机内存大小，这个虚拟内存大小取决于磁盘交换区设定的大小。

举例:
1. 单例模式: 单例的声明周期和应用程序是一样长的，所以单例程序中，如果持有外部对象的引用的话，那么这个外部对象是不能被回收的，则会导致内存泄漏的产生。
2. 一些提供close的资源未关闭导致内存泄漏。数据库链接(`datasource.getConnection()`)，网络链接(`socket`)和IO连接必须手动close，否则是不能被回收的。


## 16.3 Stop The World(STW)
* Stop-the-World，简称STW，指的是GC事件发生过程中，会产生应用程序的停顿。停顿产生时整个应用程序线程都会被暂停，没有任何响应，有点像卡死的的感觉，这个停顿就称为STW。
  * 可达性分析算法中枚举根节点(GC Roots)会导致所有Java执行线程停顿。
    * 分析工作必须在一个能确保一致性的快照中进行。
    * 一致性指整个分析期间整个执行系统看起来就像被冻结在某个时间点上。
    * 如果出现分析过程中对象引用关系还在不断变化，则分析结果的准确性无法保证。
* 被STW中断的应用程序会完成GC之后恢复，频繁中断会让用户感觉像是网速不快造成电影卡带一样，所以我们需要减少STW的发生。
* STW事件与采用哪款GC无关，所有的GC都有这个事件。
* 哪怕是G1也不能完全避免STW情况发生，只能说垃圾回收器越来越优秀，回收效率越来越高，尽可能地缩短了暂停时间。
* STW是JVM在后台自动发起的和自动完成的。在用户不可见的情况下，把用户正常的工作线程全部停掉。
* 开发中不要用`System.gc()`，会导致STW的发生。


## 16.4 垃圾回收的并行与并发
### 1. 并发(Concurrent)
* 在操作系统中，是指一个时间段中有几个程序都处于已启动运行到运行完毕之间，且这几个程序都在同一个处理器上运行。
* 并发并不是真正意义上的"同时运行"，只是CPU把一个时间段划分成几个时间段片段(时间区间)，然后在这几个时间区间之间来回切换，由于CPU处理的速度非常快，只要时间间隔处理得当，即可让用于觉得是多个程序在同时进行。

### 2. 并行(Parallel)
* 当系统有一个以上CPU时，当一个CPU执行一个进程时，另一个CPU可以执行另一个进程，两个进程之间互不抢占CPU资源，可以同时进行，我们称之为并行(Parallel)。
* 其实决定并行的因素并不是CPU的数量，而是CPU的核心数量，比如一个CPU多个核也可以并行。
* 适合科学计算，后台处理等弱交互场景。

### 3. 并发🆚并行
* 并发: 指的是多个事情，在同一时间**段**内同时发生了。
* 并行: 指的是多个事情，在同一时间**点**上同时发生了。

* 并发的多个任务之间是相互抢占资源的。
* 并行的多个任务之间是不相互抢占资源的。

只有在多CPU或者一个CPU多个核的情况中，才会发生并行。否则，看似同时发生的事情，其实都是并发执行。

### 4. 垃圾回收中的并发与并行
并发和并行，在谈论垃圾回收器的上下文中，可以解释如下:
* 并行(Parallel): 指多条垃圾收集线程并行工作，但此时用户线程仍处于等待状态。
  * 如ParNew, Parallel Scavenge, Parallel Old
* 串行(Serial):
  * 相较于并行的概念，单线程执行
  * 如果内存不够，则程序暂停，启动JVM垃圾回收器进行垃圾回收。回收完毕，再启动程序的线程。
* 并发(Concurrent): 指用户线程与垃圾收集线程同时执行(但不一定是并行的，可能会交替执行)，垃圾回收线程在执行时不会停顿用户程序的运行。
  * 用户程序在继续运行，而垃圾收集程序运行于另一个CPU上。
  * 如CMS，G1


## 16.5 安全点与安全区域
### 1. 安全点(Safe Point)
程序执行时并非在所有地方都能停顿下来开始GC，只有在特定的位置才能停顿下来开始GC，这些位置称为"安全点(Safe point)"。

Safe point的选择很重要，如果太少可能导致GC等待的时间太长，如果太频繁可能会导致程序运行时的性能问题。大部分指令的执行时间都非常短暂，通常会根据"是否具有让程序长时间执行的特征"为标准。比如，选择一些执行时间较长的指令作为Safe point，如方法调用、循环跳转和异常跳转等。

如何在GC发生时，检查所有线程都跑到最近的安全点停顿下来了？
* 抢先式中断: (目前没有虚拟机采用了)
  * 首先中断所有线程。如果还有线程不在安全点，就恢复线程，让线程跑到安全点。
* 主动式中断:
  * 设置一个中断标志，各个线程运行到Safe point的时候主动轮询这个标志。如果中断标志为真，则将自己进行中断挂起。

### 2. 安全区域(Safe Region)
Safe point机制保证了程序执行时，在不太长的时间内就会遇到可以进入GC的safe point。但是，程序"不执行"的时候呢？例如线程处于Sleep状态或Blocked状态，这时候线程无法响应JVM的中断请求，"走"到安全点去中断挂起，JVM也不太可能等待线程被唤醒。对于这种情况，就需要安全区域(Safe point)来解决。

安全区域是指在一段代码片段中，对象的引用关系不会发生变化，在这个区域中的任何文职开始GC都是安全的。我们也可以把Safe Region看作是被扩展了的Safe point。

### 3. 实际执行时
1. 当程序运行到Safe Region的代码时，首先标识已经进入Safe Region，如果这段时间内发生GC，JVM会忽略标识为Safe Region状态的线程。
2. 当线程即将离开Safe Region时，会检查JVM是否已经完成GC，如果完成了，则继续执行，否则线程必须等待直到收到可以离开Safe Region的信号为止。


## 16.6 再谈引用: 强引用(不回收)
我们希望能描述这样一类对象: 当内存空间还足够时，则能保留在内存中；如果内存空间在进行垃圾收集之后还是很紧张，则可以抛弃这些对象。

在JDK1.2之后，Java对引用的概念进行了扩充，将引用分为强引用(String Reference)，软引用(Soft Reference)，弱引用(Weak Reference)和虚引用(Phantom Reference)4种，这4种引用强度依次逐渐减弱。

除强引用外，其他三种引用均可以在`java.lang.ref`包中找到它们的身影。

Reference子类中只有终结器引用是包内可见的，其他三种引用类型均为public，可以在应用程序中直接使用。
* 强引用(Strong Reference): 最传统的"引用"的定义，是指在程序代码之中普遍存在的引用赋值，即类似`Object obj = new Object()`这种引用关系。无论任何情况下，只要有强引用关系还存在，垃圾收集器就永远不会回收掉被引用的对象。
* 软引用(Soft Reference): 在系统将要发生内存溢出之前，将会把这些对象列入回收范围之中进行第二次回收。如果这次回收之后还没有足够的内存，才会抛出内存溢出异常。
* 弱引用(Weak Reference): 被弱引用关联的对象只能生存到下一次垃圾收集之前。当垃圾收集器工作时，无论内存空间是否足够，都会回收掉被弱引用关联的对象。
* 虚引用(Phantom Reference): 一个对象是否有虚引用的存在，完全不会对其生存时间构成影响，也无法通过虚引用来获得一个对象的实例。为一个对象设置虚引用关联的唯一目的就是能在这个对象被收集器回收时收到一个系统通知。

## 16.7 再谈引用: 软引用(内存不足即回收)
* 软引用是用来描述一些还有用，但非必需的对象。只被软引用关联着的对象，在系统将要发生内存溢出异常前，会把这些对象列进回收范围之中进行第二次回收，如果这次回收还没有足够的内存，才互译抛出内存溢出异常。
* 软引用通常用来实现内存敏感的缓存。比如: 高速缓存就有用到软引用。如果还有空闲内存，就可以暂时保留缓存，当内存不足时清理掉，这样就保证了使用缓存的同时，不会耗尽内存。
* 垃圾收集器在某个时刻决定回收可达的对象的时候，会清理软引用，并可选地把引用放到一个引用队列(Reference Queue)。
* 类似弱咦你用，只不过Java虚拟机会尽量让软引用的存活时间长一些，迫不得已才清理。

## 16.8 再谈引用: 弱引用(发现即回收)
* 弱引用也是用来描述那些非必需对象，只被弱引用关联的对象只能生存到下一次垃圾收集发生为止。在系统GC时，只要发现弱引用，不管系统堆空间使用是否充足，都会回收掉只被弱引用关联的对象。
* 但是，由于垃圾回收器的线程通常优先级很低，因此，并不一定能很快地发现持有弱引用的对象。在这种情况下，弱引用对象可以存在较长的时间。
* 弱引用和软引用一样，在构造弱引用时，也可以指定一个引用队列，当弱引用对象被回收时，就会加入指定的引用队列，通过这个队列可以跟踪对象的回收情况。
* 软引用、弱引用都非常适合用来保存那些可有可无的缓存数据。如果这么做，当系统内存不足时，这些缓存数据会被回收，不会导致内存溢出。而当内存资源充足时，这些缓存数据又可以存在相当长的时间，从而起到加速系统的作用。

## 16.9 再谈引用: 虚引用(对象回收跟踪)
* 也称为"幽灵引用"或者"幻影引用"，是所有引用类型中最弱的一个。
* 一个对象是否有虚引用的存在，完全不会决定对象的生命周期。如果一个堆系那个仅持有虚引用，那么它和没有引用几乎是一样的，随时都可能被垃圾回收器回收。
* 它不能单独使用，也无法通过虚引用来获取被引用的对象。当试图通过虚引用的`get()`方法获取对象是，总是`null`。
* 为一个对象设置虚引用关联的唯一目的在于跟踪垃圾回收过程。比如: 能在这个对象被回收器回收时收到一个系统通知。

## 16.10 再谈引用: 终结器引用
* 它用以实现对象的`finalize()`方法，也可以称为终结器引用。
* 无需手动编码，其内部配合引用队列使用。
* 在GC时，终结器引用入队。由`Finalizer`线程通过终结器引用找到被引用对象并调用它的`finalize()`方法，第二次GC时才能回收被引用对象。