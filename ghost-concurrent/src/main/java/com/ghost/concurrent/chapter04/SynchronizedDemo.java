package com.ghost.concurrent.chapter04;

import java.util.concurrent.TimeUnit;

/**
 * @auther NorGhost
 * @date 2021/3/3 20:49
 * @description
 */
public class SynchronizedDemo {


    public void accessResource(){
        synchronized(this){

            try {
                TimeUnit.MINUTES.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
       final  SynchronizedDemo synchronizedDemo = new SynchronizedDemo();

        for (int i = 0; i < 5; i++) {
            new Thread(synchronizedDemo::accessResource).start();
        }



    }
}
