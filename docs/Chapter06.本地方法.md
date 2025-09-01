# Chapter06 本地方法

## 1. 本地方法？
### 1.1 什么是本地方法？
简单讲，一个Native Method就是一个Java调用非Java代码的接口。
一个Native Method是这样一个Java方法: 该方法的实现是由非Java语言实现，比如C。
这个特种并非Java所特有，很多其他的编程语言都由这一机制，比如在C++中，你可以用`extend "C"`告知C++编译器去调用一个C的函数。

> A native method is a Java method whose implementation is provided by non-java code.

在定义一个Native Method时，并不提供实现体(有些像定义一个Java interface)，因为其实现体是由非Java语言在外面实现的。

本地接口的作用是融合不同编程语言为Java所用，它的初衷是融合C/C++程序。


* `java.lang.Object.getClass`: `public final native Class<?> getClass();`
  * 看似没有方法体，实际上是有的，不过方法体的具体实现是C/C++
* `java.lang.Thread.start0`: `private native void start0();`
* `java.lang.Thread.setPriority0`: `private native void setPriority0(int newPriority);`

* 标识符`native`可以与所有其他的java标识符连用，但是abstract除外。


### 1.2 为什么要使用Native Method？
Java使用起来非常方便，然而有些层次的任务用Java实现起来并不容易，或者我们对程序的效率很在一的时候，问题就来了。

* 与Java环境交互: 有时Java应用需要与Java外面的环境交互，这是本地方法存在的主要原因。可以想想Java需要与一些底层系统，如操作系统或某些硬件交换信息的情况。本地方法正是这样一种交流机制: 它为我们提供了一个非常间接的接口，而且我们无法去了解Java应用之外的繁琐的细节。
* 与操作系统交互: JVM支持着Java语言本身和运行时库，它是Java程序赖以生存的平台，它由一个解释器(解释字节码)和一些连接到本地呆木的库组成。然而不管怎样，它毕竟不是一个完整的系统，它经常依赖于一些底层系统的支持。这些底层系统常常是强大的操作系统。通过使用本地方法，我们得以用Java实现jre的与底层系统的交互，设置JVM的一部分操作就是C写的。还有，如果我们要使用一些Java语言本身提供封装的操作系统的特性时，我们也需要使用本地方法。
* Sun's Java: **Sun的解释器是用C实现的，这使得它能像一些普通的C一样与外部交互。**jre大部分是Java实现的，它也通过一些本地方法与外界交互。例如: 类`java.lang.Thread`的`setPriority()`方法是用Java实现的，但是它实现调用的是该类里的本地方法`setPriority0()`，这个本地方法是用C实现的，并被植入JVM内部。在Windows95的平台上，这个本地方法最终将调用Win32 `SetPriority()` API。这是一个本地方法的具体实现由JVM提供，更多的情况是本地方法由外部的动态链接库(external dynamic link library)提供，然后被JVM调用。

### 1.3 现状
目前该方法使用的越来越少了，除非是与硬件有关的应用，比如通过Java程序驱动打印机或者Java系统管理生产设备，在企业级应用中已经比较少见了。因为现在的异构领域的通信已经很发到了，比如可以使用Socket通信，也可以使用Web Service等等。


## 2. 本地方法栈
* Java虚拟机用于管理Java方法的调用，而本地方法栈用于管理本地方法的调用。
* 本地方法栈也是线程私有的。
* 允许被实现成固定或者可动态扩展的内存大小。(在内存溢出方法是相同的)
  * 如果线程请求分配的栈容量超过本地方法栈允许的最大容量，Java虚拟机将会抛出一个`StackOverflowError`异常。
  * 如果本地方法栈可以动态扩展，并且在尝试扩展的时候无法申请到足够的内存，或者在创建新的线程时没有足够的内存去创建对象的本地方法栈，那么Java虚拟机将会抛出一个`OutOfMemoryError`异常。
* 本地方法时使用C语言实现的。
* 它的具体做法是Native Method Stack中登记为native方法，在Execution Engine执行时加载本地方法库。
* 当某一个线程调用一个本地方法时，它就进入了一个全新的并且不再受虚拟机限制的世界。它和虚拟机拥有同样的权限:
  * 本地方法可以通过本地方法接口来访问虚拟机内部的运行时数据区。
  * 它甚至可以直接使用本地处理器中的寄存器。
  * 直接从本地内存的堆中分配任意数量的内存。
* 并不是所有的JVM都支持本地方法。因为Java虚拟机规范并没有明确要求本地方法栈的使用语言、具体实现方式、数据结构等。如果JVM产品不打算支持native方法，也可以无需实现本地方法栈。
* 在HotSpot JVM中，直接将本地方法栈和虚拟机栈合二为一。


## Reference
* [4. 虚拟机栈](https://www.yuque.com/u21195183/jvm/ar6bqp)