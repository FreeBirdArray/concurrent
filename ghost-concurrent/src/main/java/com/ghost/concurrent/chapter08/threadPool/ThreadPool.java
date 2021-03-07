package com.ghost.concurrent.chapter08.threadPool;

/**
 * @auther NorGhost
 * @date 2021/3/7 20:38
 * @description  线程池接口
 */
public interface ThreadPool {
    /**
     * 执行线程任务
     */
    void execute(Runnable runnable);

    /**
     * 关闭线程池
     */
    void shutdown();

    /**
     * 线程池是否关闭
     */
    boolean isShutdown();

    /**
     * 获取线程池初始大小
     * @return
     */
    int getInitCapacity();

    /**
     * 获取线程池最大线程数
     * @return
     */
    int getMaxCapacity();

    /**
     * 获取线程池中活跃程序数量
     * @return
     */
    int getActiveSize();

    /**
     * 获取核心线程数
     */
    int getCoreSize();

    /**
     * 获取阻塞队列大小
     */
    int getQueueSize();






}
