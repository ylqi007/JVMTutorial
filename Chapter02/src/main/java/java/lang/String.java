package java.lang;

/**
 * @Author: ylqi007
 * @Create: 4/27/24 19:21
 * @Description: 错误示例，为了演示双亲委派机制的安全机制
 */
public class String {

    static {
        System.out.println("我是自定义的String类的static block");
    }

    //Error: Main method not found in class java.lang.String, please define the main method as:
    //public static void main(String[] args)
    //or a JavaFX application class must extend javafx.application.Application
    // 此处加载的不是自定义的String，而是核心API String根本就没有main()方法
    public static void main(String[] args) {
        System.out.println("Hello String");
    }
}
