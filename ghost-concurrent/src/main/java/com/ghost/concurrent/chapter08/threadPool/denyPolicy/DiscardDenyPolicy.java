package com.ghost.concurrent.chapter08.threadPool.denyPolicy;

import com.ghost.concurrent.chapter08.threadPool.ThreadPool;

/**
 * @auther NorGhost
 * @date 2021/3/7 21:02
 * @description  直接拒绝
 */
public class DiscardDenyPolicy implements DenyPolicy {
    @Override
    public void reject(Runnable runnable, ThreadPool threadPool) {
        //do nothing
    }
}
