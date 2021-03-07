package com.ghost.concurrent.chapter08.threadPool.factory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @auther NorGhost
 * @date 2021/3/7 21:56
 * @description  默认线程工厂
 */
public class DefaultThreadFactory implements ThreadFactory {
    private static final AtomicInteger GROUP_COUNTER = new AtomicInteger(1);
    private static final ThreadGroup group = new ThreadGroup("MyThreadPool-"
            +GROUP_COUNTER.getAndDecrement());
    private static final AtomicInteger COUNTER = new AtomicInteger(0);
    @Override
    public Thread createThread(Runnable runnable) {
        return new Thread(group,runnable,"thread-pool-"+COUNTER.getAndDecrement());
    }
}
