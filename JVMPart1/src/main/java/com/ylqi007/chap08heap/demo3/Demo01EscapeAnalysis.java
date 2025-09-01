package com.ylqi007.chap08heap.demo3;

/**
 * 逃逸分析
 *
 *  如何快速的判断是否发生了逃逸分析，大家就看“new的对象实体”是否有可能在方法外被调用。
 * @author shkstart
 * @create 2020 下午 4:00
 */
public class Demo01EscapeAnalysis {

    public Demo01EscapeAnalysis obj;

    /*
    方法返回EscapeAnalysis对象，发生逃逸：new 的对象有可能在 method 外部被调用
     */
    public Demo01EscapeAnalysis getInstance(){
        return obj == null? new Demo01EscapeAnalysis() : obj;
    }

    /*
    为成员属性赋值，发生逃逸
     */
    public void setObj(){
        this.obj = new Demo01EscapeAnalysis();
    }
    //思考：如果当前的obj引用声明为static的？仍然会发生逃逸。

    /*
    对象的作用域仅在当前方法中有效，没有发生逃逸
    没有发生逃逸的对象，则可以分配到栈上，随着方法执行的结束，栈空间就被移除了。
     */
    public void useEscapeAnalysis(){
        Demo01EscapeAnalysis e = new Demo01EscapeAnalysis();
    }

    /*
    引用成员变量的值，发生逃逸
     */
    public void useEscapeAnalysis1(){
        Demo01EscapeAnalysis e = getInstance();
        //getInstance().xxx()同样会发生逃逸
    }
}
