package com.ylqi007.chap05stack;

import java.util.Date;

/**
 * Description:
 *
 * @Author: ylqi007
 * @Create: 4/29/24 11:41
 */
public class Demo05LocalVariablesTest {
    private int count = 0;

    /**
     *   public static void main(java.lang.String[]);
     *     descriptor: ([Ljava/lang/String;)V
     *     flags: (0x0009) ACC_PUBLIC, ACC_STATIC
     *     Code:
     *       stack=2, locals=3, args_size=1 (locals=3, 即局部变量表所需的最的容量是3，是在编译时期就确定下来的)
     *          0: new           #1                  // class com/ylqi007/chap05stack/Demo05LocalVariablesTest
     *          3: dup
     *          4: invokespecial #2                  // Method "<init>":()V
     *          7: astore_1
     *          8: bipush        10
     *         10: istore_2
     *         11: aload_1
     *         12: invokevirtual #3                  // Method test1:()V
     *         15: return
     *       LineNumberTable:
     *         line 15: 0
     *         line 16: 8
     *         line 17: 11
     *         line 18: 15
     *       LocalVariableTable: (局部变量表)
     *         Start  Length  Slot  Name   Signature
     *             0      16     0  args   [Ljava/lang/String;
     *             8       8     1  test   Lcom/ylqi007/chap05stack/Demo05LocalVariablesTest;
     *            11       5     2   num   I
     *     MethodParameters:
     *       Name                           Flags
     *       args
     */
    public static void main(String[] args) {
        Demo05LocalVariablesTest test = new Demo05LocalVariablesTest();
        int num = 10;
        test.test1();
    }

    //练习：
    public static void testStatic(){
        Demo05LocalVariablesTest test = new Demo05LocalVariablesTest();
        Date date = new Date();
        int count = 10;
        System.out.println(count);
        // 因为this变量不存在于当前方法的局部变量表中！！！
//        System.out.println(this.count);
    }

    // 关于Slot的使用的理解
    public Demo05LocalVariablesTest(){
        this.count = 1;
    }

    public void test1() {
        Date date = new Date();
        String name1 = "atguigu.com";
        test2(date, name1); // 在 LocalVariableTable 中没有分配空间
        // String info = test2(date, name1); // 在 LocalVariableTable 中有分配空间
        System.out.println(date + name1);
    }

    public String test2(Date dateP, String name2) {
        dateP = null;   //占据一个slot
        name2 = "songhongkang"; // 占据一个slot
        double weight = 130.5; //占据两个slot，在LocalVariableTable中的index为3，下一个variable的index为5
        char gender = '男';
        return dateP + name2;
    }

    public void test3() {
        this.count++;
    }

    public void test4() {
        int a = 0;
        {
            int b = 0;
            b = a + 1;
        }
        // 变量c使用之前已经销毁的变量b占据的slot的位置
        int c = a + 1;
    }

    /*
    变量的分类：按照数据类型分：① 基本数据类型  ② 引用数据类型
              按照在类中声明的位置分: ① 成员变量：在使用前，都经历过默认初始化赋值
                                       类变量(static)： linking的prepare阶段：给类变量默认赋值  ---> initial阶段：给类变量显式赋值即静态代码块赋值
                                       实例变量(非static)：随着对象的创建，会在堆空间中分配实例变量空间，并进行默认赋值
                                  ② 局部变量：在使用前，必须要进行显式赋值的！否则，编译不通过
     */
    // java: variable num might not have been initialized
    public void test5Temp(){
        int num;
        // System.out.println(num);//错误信息：变量num未进行初始化，Variable 'num' might not have been initialized
    }
}
