package com.atguigui.java;

/**
 * 测试对象实例化的过程
 *  ① 加载类元信息 - ② 为对象分配内存 - ③ 处理并发问题  - ④ 属性的默认初始化（零值初始化）
 *  - ⑤ 设置对象头的信息 - ⑥ 属性的显式初始化、代码块中初始化、构造器中初始化
 *
 *
 *  给对象的属性赋值的操作：
 *  ① 属性的默认初始化 - ② 显式初始化 / ③ 代码块中初始化 - ④ 构造器中初始化
 * @author shkstart  shkstart@126.com
 * @create 2020  17:58
 */

public class Customer{
    int id = 1001;  // 分配内存时，占4个字节
    String name;    // name是引用类型，也占4个字节
    Account acct;   // acct是应用类型，也占4个字节

    {
        name = "匿名客户";
    }
    public Customer(){
        acct = new Account();
    }

}
class Account{

}
