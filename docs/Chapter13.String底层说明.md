# Chapter13. String的底层实现说明

## 13.1 String底层HashTable结构说明
字符串常量池中是不会存储相同内容的字符串的。
* String的String Pool是一个固定大小的HashTable，默认值大小长度是1009。如果放进String Pool的String非常多，就会造成Hash冲突严重，从而导致链表会很长，而链表长了之后会直接造成的影响就是当前调用`String.intern`时性能会大幅下降。
* 使用`-XX:StringTableSize`可以设置StringTable的长度。
* 在JDK6中StringTable是固定的，就是1009的长度，所以如果常量池中的字符串过多就会导致效率下降很快。StringTableSzie设置没有要求。
* 在JDK7中，StringTable的长度默认值是60013，
* JDK8开始，设置StringTable长度的话，1009是可设置的最小值。
* Example: `com.atguigu.java.StringTest2`
```shell
➜  JVMTutorial git:(main) ✗ jps
2352 StringTest2
1589 
601 
2380 Jps
2351 Launcher
➜  JVMTutorial git:(main) ✗ jinfo -flag StringTableSize 2352
-XX:StringTableSize=60013
```

## 13.2 String的内存分配
* 在Java语言中有8种基本数据类型和一种比较特殊的类型String。这些类型为了使它们在运行过程中速度更快、更节省内存，都提供了一种常量池。
* 常量池就是类似一个Java系统级别提供的缓存。8中基本数据类型的常量池都是系统协调的，String类型的常量池比较特殊。它的主要使用方法有两种。
  * 直接使用双引号声明出来的String对象会直接存储在常量池中。比如`String info="atguigu.com";`
  * 如果不是用双引号声明的String对象，可以使用String提供的`intern()`方法。
* Java6及以前，字符串常量池存放在永久代
* Java7中Oracle的工程师对字符串池的逻辑做了很大的改变，即将字符串常量池的位置调整到了Java堆中
  * 所有的字符串都保存在堆中(heap)中，和其他普通对象一样，这样可以让你在进行调优应用时仅需要调整堆大小就可以了。
  * 字符串常量池概念原本使用就比较多，但是这个改动使得我们有足够的理由让我们重新考虑Java7中使用`String.intern()`
* Java8元空间，字符串常量在堆
  * `Exception in thread "main" java.lang.OutOfMemoryError: Java heap space`

### 为什么要调整StringTable？
1. permSize默认比较小
2. 永久代垃圾回收频率低
3. Example: `com.atguigu.java.StringTest3`


## 13.3 String的基本操作
* Java语言规范里要求完全相同的字符串字面量，应该包括同样的Unicode字符序列(包含同一份码点序列的常量)，且必须是指向同一个String的类的实例。


## 13.4 字符串拼接操作
1. 常量与常量的拼接结果在常量池，原理是编译期优化。
   * Example: `com.atguigu.java1.StringTest5.test1`
2. 常量池不会存在相同内容的常量。
3. 只要其中一个是变量，结果就在**堆**中。变量拼接的原理是`StringBuilder`。
   * Example: `com.atguigu.java1.StringTest5.test2`
4. 如果拼接的结果调用`intern()`方法，则主动将常量池中还没有的字符串对象放入池中，并返回此对象地址。


## 13.5 `intern()`的使用
* API: https://docs.oracle.com/javase/8/docs/api/java/lang/String.html

如果不是用双引号声明的String对象，可以使用String提供的`intern()`方法: `intern()`方法会从字符串常量池中查询当前字符串是否存在，若不存在就会将当前的字符串放入常量池中。
* 比如: `String myInfo = new String("I love atguigu").intern();`

也就是说，如果在任意字符串上调用`String.intern()`方法，那么返回结果所指向的那个类实例，必须和直接以常量形式出现的字符串实例完全相同。因此，下列表达式的值必定是`true`:
* `("a" + "b" + "c").intern() == "abc";`

通俗点讲，`Interned String`就是确保字符串在内存中只有一份拷贝，这样可以节约内存空间，加快字符串操作任务的执行速度。注意: 这个值会被存放在字符串内部池(String Intern Pool)

如何保证变量`s`指向的字符串常量池中数据？
1. 方式一: `String s = "shk";` 字面量定义的方式
2. 方式二: 调用`intern()`方法
   * `String s = new String("shk").intern();`
   * `String s = new StringBuilder("shk").toString().intern();`


### 13.6 面试题: `new String("ab");`会创建几个对象？
拓展: `new String("a") + new String("b")`呢？


### 13.7 总结: `intern()`的使用
* 在JDK1.6中，将这个字符串对象尝试放入串池中。
  * 如果串池中有，则并不会放入。返回已有的串池中的对象的地址。
  * 如果没有，就会把此对象复制一份，放入串池中，并返回串池中的对象地址。
* 从JDK1.7起，将这个字符串对象尝试放入串池中。
  * 如果串池中有，则并不会放入。返回已有的串池中的对象地址。
  * 如果没有，则会把对象的引用地址复制一份，放入串池，并返回串池中的引用地址。


## 13.6 StringTable的垃圾回收测试


## 13.7 G1中的String去重操作


