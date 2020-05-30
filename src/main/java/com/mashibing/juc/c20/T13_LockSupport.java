package com.mashibing.juc.c20;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

/**
 * unpark 方法可以先于 park 调用
 */
public class T13_LockSupport {

    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(i);
                if (i == 2) {
                    LockSupport.park();
                    LockSupport.park();
                    System.out.println("park");
                }
//                if (i == 8){
//                    LockSupport.park();
//                }
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("after 5 s");

        LockSupport.unpark(thread);
        //LockSupport.unpark(thread);

//        try {
//            TimeUnit.SECONDS.sleep(9);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        LockSupport.unpark(thread);
    }
}
