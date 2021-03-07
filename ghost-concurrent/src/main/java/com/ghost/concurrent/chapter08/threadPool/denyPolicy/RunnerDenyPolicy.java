package com.ghost.concurrent.chapter08.threadPool.denyPolicy;

import com.ghost.concurrent.chapter08.threadPool.ThreadPool;

/**
 * @auther NorGhost
 * @date 2021/3/7 21:04
 * @description 直接执行,不会加入到线程池中
 */
public class RunnerDenyPolicy implements DenyPolicy{
    @Override
    public void reject(Runnable runnable, ThreadPool threadPool) {
        if (!threadPool.isShutdown()) {
            runnable.run();
        }
    }
}
