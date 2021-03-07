package com.ghost.concurrent.chapter08.threadPool;

import com.ghost.concurrent.chapter08.threadPool.denyPolicy.DenyPolicy;
import com.ghost.concurrent.chapter08.threadPool.denyPolicy.DiscardDenyPolicy;
import com.ghost.concurrent.chapter08.threadPool.factory.DefaultThreadFactory;
import com.ghost.concurrent.chapter08.threadPool.factory.ThreadFactory;
import com.ghost.concurrent.chapter08.threadPool.queue.BlockedQueue;
import com.ghost.concurrent.chapter08.threadPool.queue.LinkedBlockQueue;

import java.sql.Time;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.TimeUnit;

/**
 * @auther NorGhost
 * @date 2021/3/7 21:33
 * @description
 */
public class BasicThreadPool extends Thread implements ThreadPool {

    // 线程池初始容量
    private final int initCapacity;

    // 线程池最大线程容量
    private final int maxCapacity;

    // 线程池核心线程数
    private final int coreSize;

    // 当前活跃线程数
    private int activeSize;

    // 创建线程所需的工厂
    private final ThreadFactory threadFactory;

    // 任务队列
    private final BlockedQueue blockedQueue;

    // 线程是否shutdown
    private volatile boolean isShutdown = false ;

    // 工作线程队列
    private final Queue<ThreadTask> threadTaskQueue  = new ArrayDeque<>();

    // 拒绝策略
    private final static DenyPolicy DEFAULT_DENY_POLICY = new DiscardDenyPolicy();

    private final static ThreadFactory DEFAULT_THREAD_FACTORY = new DefaultThreadFactory();

    // 线程存活时长
    private final long keepAliveTime ;

    // 时间单位
    private final TimeUnit timeUnit;
    public BasicThreadPool(int initCapacity, int maxCapacity, int coreSize,int queueSize){

        this(initCapacity,maxCapacity,coreSize,DEFAULT_THREAD_FACTORY,
                DEFAULT_DENY_POLICY,queueSize,10,TimeUnit.SECONDS);
    }

    public BasicThreadPool(int initCapacity, int maxCapacity, int coreSize,
                           ThreadFactory threadFactory, DenyPolicy denyPolicy,
                           int queueSize, long keepAliveTime, TimeUnit timeUnit) {
        this.initCapacity = initCapacity;
        this.maxCapacity = maxCapacity;
        this.coreSize = coreSize;
        this.threadFactory = threadFactory;
        this.blockedQueue = new LinkedBlockQueue(queueSize,denyPolicy,this);
        this.keepAliveTime = keepAliveTime;
        this.timeUnit = timeUnit;
        this.init();
    }
    // 创建默认容量数量的线程
    private void init(){
        start();
        for (int i = 0; i < initCapacity; i++) {
            newThread();
        }
    }
    // 线程值自动维护
    private void newThread(){
        InternalTask internalTask = new InternalTask(blockedQueue);
        Thread thread = this.threadFactory.createThread(internalTask);
        ThreadTask threadTask = new ThreadTask(thread, internalTask);
        threadTaskQueue.offer(threadTask);
        this.activeSize++;
        thread.start();
    }

    private void removeThread(){
        ThreadTask remove = threadTaskQueue.remove();
        remove.internalTask.stop();
        this.activeSize--;

    }

    /**
     * 主要用于回收 扩容等
     */
    @Override
    public void run(){

        while (!isShutdown && !isInterrupted()){
            try {
                timeUnit.sleep(keepAliveTime);
            } catch (InterruptedException e) {
                isShutdown = true ;
                break;
            }

            synchronized (this){

                if (isShutdown) {
                    break;
                }

                //当前队列有任务尚未处理并且activeSize<coreCapacity 则继续扩容
                if (blockedQueue.getTaskSize()>0 && activeSize < coreSize) {
                    for (int i = initCapacity; initCapacity < coreSize; i++) {
                        newThread();
                    }
                    continue;
                }
                //当前队列有任务尚未处理并且activeSize<maxCapacity 则继续扩容
                if (blockedQueue.getTaskSize()>0 && activeSize < maxCapacity) {
                    for (int i = initCapacity; initCapacity < maxCapacity; i++) {
                        newThread();
                    }
                    continue;
                }

                //当前队列没有任务尚未处理并且activeSize>coreCapacity 则继续扩容
                if (blockedQueue.getTaskSize()>0 && activeSize > coreSize) {
                    for (int i = activeSize; initCapacity < coreSize; i--) {
                        removeThread();
                    }
                }
            }


        }
    }

    //提交任务
    @Override
    public void execute(Runnable runnable) {
        if (this.isShutdown) {
            throw new IllegalStateException("The threadPool is destroy");
        }
        this.blockedQueue.offer(runnable);
    }


    /**
     * 线程池销毁
     */
    @Override
    public void shutdown() {
       synchronized (this){
           if (isShutdown) return;
           isShutdown = true ;
           threadTaskQueue.forEach(threadTask -> {
                        threadTask.internalTask.stop();
                        threadTask.thread.isInterrupted();
                   }
           );
           this.interrupt();
       }





    }

    @Override
    public boolean isShutdown() {
        return this.isShutdown;
    }

    @Override
    public int getInitCapacity() {
        return this.initCapacity;
    }

    @Override
    public int getMaxCapacity() {
        return this.maxCapacity;
    }

    @Override
    public int getActiveSize() {
        synchronized (this){
            return this.activeSize;
        }
    }

    @Override
    public int getCoreSize() {
        return this.coreSize;
    }

    @Override
    public int getQueueSize() {
        return blockedQueue.getTaskSize();
    }


    private static class ThreadTask {
        Thread thread;
        InternalTask internalTask;
        public ThreadTask(Thread thread ,InternalTask task) {
            this.thread = thread;
            this.internalTask = task;
        }
    }




}

