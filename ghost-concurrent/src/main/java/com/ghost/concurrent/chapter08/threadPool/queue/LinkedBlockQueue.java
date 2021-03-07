package com.ghost.concurrent.chapter08.threadPool.queue;

import com.ghost.concurrent.chapter08.threadPool.ThreadPool;
import com.ghost.concurrent.chapter08.threadPool.denyPolicy.DenyPolicy;

import java.util.LinkedList;

/**
 * @auther NorGhost
 * @date 2021/3/7 21:16
 * @description  有序的阻塞队列
 */
public class LinkedBlockQueue implements BlockedQueue {


    //任务的最大容量
    private final int maxCapacity;

    //拒绝策略
    private final DenyPolicy denyPolicy;

    //存放任务的队列
    private final LinkedList<Runnable> runnableLinkedList = new LinkedList<>();

    private final ThreadPool threadPool;

    public LinkedBlockQueue(int maxCapacity, DenyPolicy denyPolicy, ThreadPool threadPool) {
        this.maxCapacity = maxCapacity;
        this.denyPolicy = denyPolicy;
        this.threadPool = threadPool;
    }

    /**
     * 提交任务到队列中
     * @param runnable
     */
    @Override
    public void offer(Runnable runnable) {

        synchronized (runnableLinkedList){
            //如果大于最大容量，则拒绝任务
            if (runnableLinkedList.size() >= maxCapacity){
                 denyPolicy.reject(runnable,threadPool);
            }else {
                //将任务添加到对尾巴
                runnableLinkedList.addLast(runnable);
                //唤醒阻塞的线程
                runnableLinkedList.notifyAll();
            }
        }


    }

    @Override
    public Runnable task() {
        synchronized (runnableLinkedList){
            //如果任务队列为空，则阻塞
            if (runnableLinkedList.isEmpty()) {

                try {
                    runnableLinkedList.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
        //从任务队列中移除一个任务
        return runnableLinkedList.removeFirst();
    }

    @Override
    public int getTaskSize() {
        synchronized (runnableLinkedList){
            return runnableLinkedList.size();
        }
    }

}
