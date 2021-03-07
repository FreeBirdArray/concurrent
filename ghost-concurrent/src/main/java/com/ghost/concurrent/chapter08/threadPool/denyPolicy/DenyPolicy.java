package com.ghost.concurrent.chapter08.threadPool.denyPolicy;

import com.ghost.concurrent.chapter08.threadPool.ThreadPool;

/**
 * @auther NorGhost
 * @date 2021/3/7 20:56
 * @description   拒绝策略
 */

@FunctionalInterface
public interface DenyPolicy {
    /**
     * 拒绝任务
     * @param runnable
     * @param threadPool
     */
    void reject(Runnable runnable, ThreadPool threadPool);

}
