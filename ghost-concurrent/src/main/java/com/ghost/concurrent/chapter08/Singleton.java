package com.ghost.concurrent.chapter08;

/**
 * @auther NorGhost
 * @date 2021/3/9 21:51
 * @description
 */
public class Singleton {


    private static int x = 0;
    private static int y ;

    private static Singleton instance = new Singleton();

    private Singleton() {
    x++;
    y++;
    }

    public static Singleton getInstance(){
        return instance;
    }

    public static void main(String[] args) {
        // 调用类的静态方法会导致类的初始化 初始化时为类赋值
        Singleton instance = Singleton.getInstance();
        System.out.println(instance.x);
        System.out.println(instance.y);

    }


}
