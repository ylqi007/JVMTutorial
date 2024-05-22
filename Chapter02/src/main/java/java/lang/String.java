package java.lang;

/**
 * Description:
 *
 * @Author: ylqi007
 * @Create: 4/27/24 19:21
 */
public class String1 {

    static {
        System.out.println("我是自定义的String类的stitic block");
    }

    //Error: Main method not found in class java.lang.String, please define the main method as:
    //public static void main(String[] args)
    //or a JavaFX application class must extend javafx.application.Application
    // 此处加载的不是自定义的String，而是核心API String根本就没有main()方法
    public static void main(String[] args) {
        System.out.println("Hello String");
    }
}
