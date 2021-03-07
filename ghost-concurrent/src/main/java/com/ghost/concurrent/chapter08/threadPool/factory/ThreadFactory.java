package com.ghost.concurrent.chapter08.threadPool.factory;

/**
 * @auther NorGhost
 * @date 2021/3/7 20:53
 * @description  创建线程的工厂
 */
@FunctionalInterface
public interface ThreadFactory {

    /**
     * 创建线程
     * @param runnable
     * @return
     */
    Thread createThread(Runnable runnable);
}
