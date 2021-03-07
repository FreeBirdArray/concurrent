package com.ghost.concurrent.chapter08.threadPool;

import com.ghost.concurrent.chapter08.threadPool.queue.BlockedQueue;

/**
 * @auther NorGhost
 * @date 2021/3/7 21:09
 * @description   执行队列中的任务
 */
public class InternalTask implements Runnable {

    private BlockedQueue blockedQueue;
    private volatile boolean running = true;

    public InternalTask(BlockedQueue blockedQueue) {
        this.blockedQueue = blockedQueue;
    }

    @Override
    public void run() {
        // 如果当前任务为running 并没有被中断 ，则不断的从任务队列在获取任务，并执行run() 方法
      while (running && !Thread.currentThread().isInterrupted()){
          //执行任务
          Runnable task = blockedQueue.task();
          task.run();
      }
    }

    /**
     * 停止当前任务
     */
    public void stop(){
        this.running = false ;
    }
}
