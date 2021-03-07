package com.ghost.concurrent.chapter08.threadPool.queue;

/**
 * @auther NorGhost
 * @date 2021/3/7 20:49
 * @description   任务队列
 * */
public interface BlockedQueue {
    /**
     * 获取提交任务 当有新的任务来时，会会提交任务队列中
     * @param runnable
     */
    void offer(Runnable runnable);

    /**
     * 获取队列中任务
     */
    Runnable task();

    /**
     * 获取当前任务数量
     * @return
     */
    int getTaskSize();
}
