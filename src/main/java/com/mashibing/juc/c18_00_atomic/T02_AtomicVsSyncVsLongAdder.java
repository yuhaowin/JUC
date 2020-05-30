package com.mashibing.juc.c18_00_atomic;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

public class T02_AtomicVsSyncVsLongAdder {
    static long count1 = 0L;
    static AtomicLong count2 = new AtomicLong(0L);
    static LongAdder count3 = new LongAdder();

    public static void main(String[] args) throws Exception {
        Thread[] threads = new Thread[1000];

        //------------------------------------------------------------
        Object lock = new Object();
        for (int i = 0; i < threads.length; i++) {
            threads[i] =
                    new Thread(() -> {
                        for (int k = 0; k < 100000; k++)
                            synchronized (lock) {
                                count1++;
                            }
                    });
        }

        long start = System.currentTimeMillis();

        for (Thread t : threads) t.start();

        for (Thread t : threads) t.join();

        long end = System.currentTimeMillis();

        System.out.println("Sync: " + count1 + " time " + (end - start));

        //------------------------------------------------------------
        for (int i = 0; i < threads.length; i++) {
            threads[i] =
                    new Thread(() -> {
                        for (int k = 0; k < 100000; k++) count2.incrementAndGet();
                    });
        }

        start = System.currentTimeMillis();

        for (Thread t : threads) t.start();

        for (Thread t : threads) t.join();

        end = System.currentTimeMillis();

        System.out.println("Atomic: " + count2.get() + " time " + (end - start));

        //--------------------------------------------------------------
        for (int i = 0; i < threads.length; i++) {
            threads[i] =
                    new Thread(() -> {
                        for (int k = 0; k < 100000; k++) count3.increment();
                    });
        }

        start = System.currentTimeMillis();

        for (Thread t : threads) t.start();

        for (Thread t : threads) t.join();

        end = System.currentTimeMillis();

        System.out.println("LongAdder: " + count3.longValue() + " time " + (end - start));
    }
}
