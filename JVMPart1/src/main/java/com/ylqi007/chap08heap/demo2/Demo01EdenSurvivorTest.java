package com.ylqi007.chap08heap.demo2;

/**
 * -Xms600m -Xmx600m
 *
 * -XX:NewRatio ： 设置新生代与老年代的比例。默认值是2.
 * -XX:SurvivorRatio ：设置新生代中Eden区与Survivor区的比例。默认值是8，Eden:Survivor0:Survivor1 = 8:1:1
 * -XX:-UseAdaptiveSizePolicy ：关闭自适应的内存分配策略  （暂时用不到）
 * -Xmn:设置新生代的空间的大小。 （一般不设置）
 *
 * @author shkstart  shkstart@126.com
 * @create 2020  17:23
 *
 * @Author ylqi007
 * @Create 2025.09.01 11:10AM
 *
 * ~ jinfo -flag SurvivorRatio 97101
 * -XX:SurvivorRatio=8
 *
 * SurvivorRatio 默认值是8，但实际运行中不是8:1:1，而是6:1:1
 * 需要显式设置 -XX:SurvivorRatio=8，才能在VisualVM 中看到 8:1:1 的比例
 */
public class Demo01EdenSurvivorTest {
    public static void main(String[] args) {
        System.out.println("我只是来打个酱油~");
        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
