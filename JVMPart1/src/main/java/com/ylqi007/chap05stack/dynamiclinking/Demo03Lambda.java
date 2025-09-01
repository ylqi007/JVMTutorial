package com.ylqi007.chap05stack.dynamiclinking;

/**
 * 体会invokedynamic指令
 * @author shkstart
 * @create 2020 下午 3:09
 */
@FunctionalInterface
interface Func {
    public boolean func(String str);
}

public class Demo03Lambda {
    public void lambda(Func func) {
        return;
    }

    public static void main(String[] args) {
        Demo03Lambda lambda = new Demo03Lambda();

        Func func = s -> {
            return true;
        };

        lambda.lambda(func);

        lambda.lambda(s -> {
            return true;
        });
    }
}

