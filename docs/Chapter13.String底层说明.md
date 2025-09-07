# Chapter13. String的底层实现说明

## 13.1 String的基本特性
* String：字符串，使用一对`""`引起来表示
* String声明为`final`的，不可被继承
* String实现了`java.io.Serializable`接口：表示字符串是支持序列化的。
* String实现了`Comparable<String>`接口：表示string可以比较大小
* String在jdk8及以前内部定义了`final char[] value`用于存储字符串数据。JDK9时改为`byte[]`

### 1. String在jdk9中存储结构变更: [JEP 254: Compact Strings](https://openjdk.org/jeps/254)

1. 动机：目前String类的实现将字符存储在一个char数组中，每个字符使用两个字节（16位）。从许多不同的应用中收集到的数据表明，字符串是堆使用的主要组成部分，此外，大多数字符串对象只包含Latin-1字符。这些字符只需要一个字节的存储空间，因此这些字符串对象的内部字符数组中有一半的空间没有被使用。
2. 说明：我们建议将String类的内部表示方法从UTF-16字符数组改为字节数组加编码标志域。新的String类将根据字符串的内容，以ISO-8859-1/Latin-1（每个字符一个字节）或UTF-16（每个字符两个字节）的方式存储字符编码。编码标志将表明使用的是哪种编码。

与字符串相关的类，如AbstractStringBuilder、StringBuilder和StringBuffer将被更新以使用相同的表示方法，HotSpot VM的内在字符串操作也是如此。

这纯粹是一个实现上的变化，对现有的公共接口没有变化。目前没有计划增加任何新的公共API或其他接口。

迄今为止所做的原型设计工作证实了内存占用的预期减少，GC活动的大幅减少，以及在某些角落情况下的轻微性能倒退。

结论：String再也不用char[] 来存储了，改成了byte [] 加上编码标记，节约了一些空间
```java
public final class String implements java.io.Serializable, Comparable<String>, CharSequence {
    @Stable
    private final byte[] value;
}
```

### 2. String的基本特性
String：代表不可变的字符序列。简称：不可变性。

* 当对字符串重新赋值时，需要重写指定内存区域赋值，不能使用原有的value进行赋值。
* 当对现有的字符串进行连接操作时，也需要重新指定内存区域赋值，不能使用原有的value进行赋值。
* 当调用string的replace()方法修改指定字符或字符串时，也需要重新指定内存区域赋值，不能使用原有的value进行赋值。

通过字面量的方式（区别于new）给一个字符串赋值，此时的字符串值声明在字符串常量池中。

字符串常量池是不会存储相同内容的字符串的

String的String Pool是一个固定大小的Hashtable，默认值大小长度是1009。如果放进String Pool的String非常多，就会造成Hash冲突严重，从而导致链表会很长，而链表长了后直接会造成的影响就是当调用String.intern时性能会大幅下降。

使用-XX:StringTablesize可设置StringTable的长度

* 在jdk6中StringTable是固定的，就是1009的长度，所以如果常量池中的字符串过多就会导致效率下降很快。StringTablesize设置没有要求
* 在jdk7中，StringTable的长度默认值是60013，StringTablesize设置没有要求
* 在JDK8中，设置StringTable长度的话，1009是可以设置的最小值



### 3. String底层HashTable结构说明
字符串常量池中是不会存储相同内容的字符串的。
* String的String Pool是一个固定大小的HashTable，默认值大小长度是1009。如果放进String Pool的String非常多，就会造成Hash冲突严重，从而导致链表会很长，而链表长了之后会直接造成的影响就是当前调用`String.intern`时性能会大幅下降。
* 使用`-XX:StringTableSize`可以设置StringTable的长度。
* 在JDK6中StringTable是固定的，就是1009的长度，所以如果常量池中的字符串过多就会导致效率下降很快。StringTableSzie设置没有要求。
* 在JDK7中，StringTable的长度默认值是60013，
* JDK8开始，设置StringTable长度的话，1009是可设置的最小值。
* Example: [StringTest2.java](../JVMPart1/src/main/java/com/ylqi007/chap13stringtable/demo01/StringTest2.java)
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
* Java6及以前，字符串常量池存放在永久代(Perm Generation)
* Java7中Oracle的工程师对字符串池的逻辑做了很大的改变，即将字符串常量池的位置调整到了Java堆中
  * 所有的字符串都保存在堆中(heap)中，和其他普通对象一样，这样可以让你在进行调优应用时仅需要调整堆大小就可以了。
  * 字符串常量池概念原本使用就比较多，但是这个改动使得我们有足够的理由让我们重新考虑Java7中使用`String.intern()`
* Java8元空间，字符串常量在堆(Heap)
  * `Exception in thread "main" java.lang.OutOfMemoryError: Java heap space`


### 为什么要调整StringTable？
官网地址：[Java SE 7 Features and Enhancements (oracle.com)](https://www.oracle.com/java/technologies/javase/jdk7-relnotes.html#jdk7changes)

简介：在JDK 7中，内部字符串不再分配在Java堆的永久代中，而是分配在Java堆的主要部分（称为年轻代和老年代），与应用程序创建的其他对象一起。这种变化将导致更多的数据驻留在主Java堆中，而更少的数据在永久代中，因此可能需要调整堆的大小。大多数应用程序将看到由于这一变化而导致的堆使用的相对较小的差异，但加载许多类或大量使用String.intern()方法的大型应用程序将看到更明显的差异。

1. permSize默认比较小
2. 永久代垃圾回收频率低
3. Example: [StringTest3.java](../JVMPart1/src/main/java/com/ylqi007/chap13stringtable/demo01/StringTest3.java)


## 13.3 String的基本操作
Java语言规范里要求完全相同的字符串字面量，应该包括同样的Unicode字符序列(包含同一份码点序列的常量)，且必须是指向同一个String的类的实例。
* 示例代码: [StringTest4.java](../JVMPart1/src/main/java/com/ylqi007/chap13stringtable/demo02/StringTest4.java)
* 示例代码: [Memory.java](../JVMPart1/src/main/java/com/ylqi007/chap13stringtable/demo02/Memory.java)


## 13.4 字符串拼接操作
1. 常量与常量的拼接结果在常量池，原理是编译期优化。
   * Example: `com.atguigu.java1.StringTest5.test1`
2. 常量池不会存在相同内容的常量。
3. 只要其中一个是变量，结果就在**堆**(非常量池区域)中。变量拼接的原理是`StringBuilder`。
   * Example: [StringTest5.java](../JVMPart1/src/main/java/com/ylqi007/chap13stringtable/demo02/StringTest5.java) 
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


### 1. 面试题: `new String("ab");`会创建几个对象？
拓展: `new String("a") + new String("b")`呢？
* 代码示例：[StringNewTest.java](../JVMPart1/src/main/java/com/ylqi007/chap13stringtable/demo03intern/StringNewTest.java)


### 2. 总结: `intern()`的使用
当调用`intern()`方法时，如果池子里已经包含了一个与这个String对象相等的字符串，正如`equals(Object)`方法所确定的，那么池子里的字符串会被返回。否则，这个String对象被添加到池中，并返回这个String对象的引用。

由此可见，对于任何两个字符串s和t，当且仅当`s.equals(t)`为真时，`s.intern() == t.intern()`为真。

所有字面字符串和以字符串为值的常量表达式都是interned。

返回一个与此字符串内容相同的字符串，但保证是来自一个唯一的字符串池。

* intern是一个native方法，调用的是底层C的方法
* 如果不是用双引号声明的String对象，可以使用String提供的intern方法，它会从字符串常量池中查询当前字符串是否存在，若不存在就会将当前字符串放入常量池中。

* 在JDK1.6中，将这个字符串对象尝试放入串池中。
  * 如果串池中有，则并不会放入。返回已有的串池中的对象的地址。
  * 如果没有，就会把此对象复制一份，放入串池中，并返回串池中的对象地址。
* 从JDK1.7起，将这个字符串对象尝试放入串池中。
  * 如果串池中有，则并不会放入。返回已有的串池中的对象地址。
  * 如果没有，则会把对象的引用地址复制一份，放入串池，并返回串池中的引用地址。


## 13.6 StringTable的垃圾回收测试
* [StringGCTest.java](../JVMPart1/src/main/java/com/ylqi007/chap13stringtable/demo04stringtablegc/StringGCTest.java)


## 13.7 G1中的String去重操作
官网地址：[JEP 192: String Deduplication in G1 (java.net)](https://openjdk.org/jeps/192)

目前，许多大规模的Java应用程序在内存上遇到了瓶颈。测量表明，在这些类型的应用程序中，大约25%的Java堆实时数据集被String'对象所消耗。此外，这些 "String "对象中大约有一半是重复的，其中重复意味着 "string1.equals(string2) "是真的。在堆上有重复的String'对象，从本质上讲，只是一种内存的浪费。这个项目将在G1垃圾收集器中实现自动和持续的`String'重复数据删除，以避免浪费内存，减少内存占用。

* 注意这里说的重复，指的是在堆中的数据，而不是常量池中的，因为常量池中的本身就不会重复

### 1. 背景：对许多Java应用（有大的也有小的）做的测试得出以下结果：
* 堆存活数据集合里面string对象占了25%
* 堆存活数据集合里面重复的string对象有13.5%
* string对象的平均长度是45

许多大规模的Java应用的瓶颈在于内存，测试表明，在这些类型的应用里面，Java堆中存活的数据集合差不多25%是String对象。更进一步，这里面差不多一半string对象是重复的，重复的意思是说： `string1.equals(string2)= true`。堆上存在重复的String对象必然是一种内存的浪费。这个项目将在G1垃圾收集器中实现自动持续对重复的string对象进行去重，这样就能避免浪费内存。

### 2. 实现
1. 当垃圾收集器工作的时候，会访问堆上存活的对象。对每一个访问的对象都会检查是否是候选的要去重的String对象
2. 如果是，把这个对象的一个引用插入到队列中等待后续的处理。一个去重的线程在后台运行，处理这个队列。处理队列的一个元素意味着从队列删除这个元素，然后尝试去重它引用的string对象。
3. 使用一个hashtable来记录所有的被String对象使用的不重复的char数组。当去重的时候，会查这个hashtable，来看堆上是否已经存在一个一模一样的char数组。
4. 如果存在，String对象会被调整引用那个数组，释放对原来的数组的引用，最终会被垃圾收集器回收掉。
5. 如果查找失败，char数组会被插入到hashtable，这样以后的时候就可以共享这个数组了。

### 3. 命令行选项
```shell
# 开启String去重，默认是不开启的，需要手动开启。 
UseStringDeduplication(bool)  
# 打印详细的去重统计信息 
PrintStringDeduplicationStatistics(bool)  
# 达到这个年龄的String对象被认为是去重的候选对象
StringpeDuplicationAgeThreshold(uintx)
```


## Reference
* https://openjdk.org/jeps/254 (String 在 JDK9 中存储结构的改变)
* [美团技术团队: 深入解析String#intern](https://tech.meituan.com/2014/03/06/in-depth-understanding-string-intern.html)
* https://www.yuque.com/u21195183/jvm/xbc47z