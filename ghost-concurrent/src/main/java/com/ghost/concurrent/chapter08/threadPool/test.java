package com.ghost.concurrent.chapter08.threadPool;

import java.util.concurrent.TimeUnit;

/**
 * @auther NorGhost
 * @date 2021/3/7 22:47
 * @description
 */
public class test {

    public static void main(String[] args) throws InterruptedException {
        final BasicThreadPool basicThreadPool =
                new BasicThreadPool(2,6,4,1000);
        for (int i = 0; i < 20 ; i++) {
            basicThreadPool.execute(()->{
                        try {
                            TimeUnit.SECONDS.sleep(10);
                            System.out.println(Thread.currentThread().getName()+"is running and done.");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
            );
            for (;;){
                System.out.println("ActiveCount: "+basicThreadPool.getActiveSize());
                System.out.println("queueSize: "+basicThreadPool.getQueueSize());
                System.out.println("coreSize: "+basicThreadPool.getCoreSize());
                System.out.println("maxCapacity: "+basicThreadPool.getMaxCapacity());
                System.out.println("===============================================");
                TimeUnit.SECONDS.sleep(5);
            }
        }




    }
}
