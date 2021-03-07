package com.ghost.concurrent.chapter02;

/**
 * @auther NorGhost
 * @date 2021/2/25 20:19
 * @description
 */
public class ThreadGroupDemo {


    public static void main(String[] args) {
        //创建线程对象 thread-1
        Thread thread1 = new Thread("thread-1");
        //创建线程组
        ThreadGroup threadGroup = new ThreadGroup("threadGroup-1");
        //创建线程 thread-2，并将线程添加到线程组 threadGroup-1 中
        Thread thread2 = new Thread(threadGroup, "thread-2");
        //获取当前线程所在线程组
        ThreadGroup mainThreadGroup = Thread.currentThread().getThreadGroup();

        System.out.println("main 线程所在线程组为：{"+ mainThreadGroup.getName()+"}");
        System.out.println("thread-1 线程所在线程组为：{"+thread1.getThreadGroup().getName()+"}");
        System.out.println("thread-2 线程所在线程组为：{"+thread2.getThreadGroup().getName()+"}");
        System.out.println(thread1.isDaemon());
        System.out.println(Thread.currentThread().isDaemon());


        /**
         * 结论：
         * 1. 创建线程时如果没有指定 threadGroup 那么该线程会默认添加到和父线程相同的 threadGroup 线程组中
         * 2. 该线程和父线程拥有同样的优先级 ，daemon 值也相同
         */

    }
}
