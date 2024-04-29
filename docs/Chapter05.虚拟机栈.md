# Chapter05 虚拟机栈

## 1. 虚拟机栈概述
### 1.1 虚拟机栈出现的背景
由于跨平台性的设计，Java的指令都是根据**栈**来设计的。不同平台CPU架构不同，所以不能设计为基于寄存器的。

优点是跨平台，指令集小，编译器容易实现；缺点是性能下降，实现同样的功能需要更多的指令。

### 1.2 初步印象
不少Java开发人员一提到Java内存结构，就会非常粗粒度地将JVM中的内存区域理解为仅有Java堆(heap)和Java栈(stack)？为什么？

### 1.3 内存中的栈和堆
**栈是运行时的单位，而堆是存储的单位。**即
* 栈解决程序的运行问题，即程序如何执行，或者说如何处理数据。
* 堆解决的是数据存储的问题，即数据怎么放，放在哪儿

### 1.4 虚拟机栈基本内容
Java虚拟机栈是什么？
* Java虚拟机栈(Java Virtual Machine Stack)，早期也叫做Java栈，每个线程在创建时都会创建一个虚拟机栈，其内部保存一个个的**栈帧(Stack Frame)**，对应着一次次的Java方法调用。
* 是线程私有的。

**生命周期:** 生命周期和线程一致。

**作用:** 
* 主管Java程序的运行，它保存方法的局部变量(8种基本数据类型，对象的引用地址)、部分结果，并参与方法的调用和返回。
  * 局部变量 vs 成员变量(i.e 属性)
  * 基本数据变量 vs 引用数据变量(类，数组，接口)

**栈的特点(优点):**
* 栈是一种快速有效的分配存储方式，访问速度仅次于程序计数器(PC寄存器)。
* JVM直接对Java栈的操作只有两个:
  1. 每个方法执行，伴随着进栈(入栈、压栈)
  2. 执行结束后的出栈工作
* 对于栈来说，不存在垃圾回收问题。

#### 面试题: 开发中遇到的异常有哪些？
**栈中可能出现的异常:**
* Java虚拟机规范允许Java栈的大小是动态的或者是固定不变的。
  * 如果采用**固定大小**的Java虚拟机栈，那每一个线程的Java虚拟机栈容量可以在线程创建的时候独立选定。如果线程请求分配的栈容量超过Java虚拟机栈允许的最大容量，Java虚拟机将会抛出一个`java.lang.StackOverflowError`异常。
  * 如果Java虚拟机可以**动态扩展**，并且尝试扩展的时候无法申请到足够的内存，或者创建新的线程时没有足够的内存区创建对象的虚拟机栈，那么Java虚拟机会抛出一个`OutOfMemoryError`异常。

**设置栈内存大小:** 我们可以通过使用参数`-Xss`选项来设置线程的最大堆空间，栈的大小直接决定了函数调用的最大可达深度。
> -Xss size
Sets the thread stack size (in bytes). Append the letter k or K to indicate KB, m or M to indicate MB, and g or G to indicate GB. The default value depends on the platform:
> * Linux/x64 (64-bit): 1024 KB
> * macOS (64-bit): 1024 KB
> * Oracle Solaris/x64 (64-bit): 1024 KB
> * Windows: The default value depends on virtual memory
> 
> The following examples set the thread stack size to 1024 KB in different units:
> ```shell
> -Xss1m
> -Xss1024k
> -Xss1048576
> ```
> This option is similar to -XX:ThreadStackSize.


## 2. 栈的存储单位
### 2.1 栈中存储什么？
* 每个线程都有自己的栈，栈中的数据都是以**栈帧(Stack Frame)**的格式存在。
* 在这个线程上正在执行的每个方法都各自对应一个**栈帧(Stack Frame)**
* 栈帧是一个内存区块，是一个数据集，维系着方法执行过程中的各种数据信息。

### 2.2 栈运行原理
* JVM直接对Java栈的操作只有两个，就是对栈帧的压栈和出栈，遵循**"先进后出"**的原则。
* 在一条活动线程中，一个时间点上，只会有一个活动的栈帧。即只有当前正在执行的方法的栈帧(栈顶栈帧)是有效的，这个栈帧被称为当前栈帧(Current Frame)，与当前栈帧相对应的方法就是当前方法(Current Method)，定义这个方法的类就是当前类(Current Class)。
* 执行引擎运行的所有字节码指令只针对当前栈帧进行操作。
* 如果在该方法中调用了其他方法，对应的新的栈帧会被创建出来，放在栈的顶端，称为新的当前栈帧。
* 不同线程中所包含的栈帧是不允许存在相互引用的，即不可能在一个栈帧中引用另一个线程的栈帧。
* 如果当前方法调用了其他方法，方法返回之际，当前栈帧会传回此方法的执行结果给前一个栈帧，接着，虚拟机会丢弃当前栈帧，使得前一个栈帧重新成为当前栈帧。
* Java方法有两种返回函数的方式，一种是正常的函数返回，使用return指令；另外一种是抛出异常。不管使用哪种方式，都会导致栈帧被弹出。

<img src="JVM.Images.I/第05章_方法与栈桢.jpg">

<img src="JVM.Images.I/第05章_StackFrameTest.png">

### 2.3 栈帧的内部结构
<img src="JVM.Images.I/第05章_栈桢内部结构.jpg">

每个栈帧中存储着:
1. 局部变量表(Local Variables)
2. 操作数栈(Operand Stack) (或表达式栈)
3. 动态链接(Dynamic Linking) (或指向运行时常量池的方法引用)
4. 方法返回地址(Return Address) (或方法正常退出或者异常退出的定义)
5. 一些附加信息


## 5.3 局部变量表
## 5.4 操作数栈
## 5.5 代码追踪
## 5.6 栈顶缓存技术
## 5.7 动态链接
## 5.8 方法的调用: 解析与分派
## 5.9 方法返回地址
## 5.10 一些附加信息
## 5.11 栈的相关面试题

## Reference
* 宋红康
* [JDK 11 Documentation](https://docs.oracle.com/en/java/javase/11/)
* [Tools Reference](https://docs.oracle.com/en/java/javase/11/tools/tools-and-command-reference.html#JSWOR-GUID-55DE52DF-5774-4AAB-B334-E026FBAE6F34)
* [JVM栈帧的内部结构](https://blog.csdn.net/u011069294/article/details/107066447)