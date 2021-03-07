package com.ghost.concurrent.chapter08.threadPool.denyPolicy;

import com.ghost.concurrent.chapter08.threadPool.ThreadPool;

/**
 * @auther NorGhost
 * @date 2021/3/7 21:02
 * @description  抛出异常
 */
public class AbortDenyPolicy implements DenyPolicy{
    @Override
    public void reject(Runnable runnable, ThreadPool threadPool) {
        throw new RuntimeException("The runnable"+runnable+"will abort.");
    }
}
