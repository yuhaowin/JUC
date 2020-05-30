package com.mashibing.juc.c18_00_atomic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * 解决同样的问题的更高效的方法，使用AtomXXX类
 * AtomXXX类本身方法都是原子性的，但不能保证多个方法连续调用是原子性的
 */
public class T01_AtomicInteger {

    AtomicInteger count = new AtomicInteger(0);

    void m() {
        for (int i = 0; i < 10000; i++)
            count.incrementAndGet(); //count1++
    }

    public static void main(String[] args) throws InterruptedException {

        T01_AtomicInteger t = new T01_AtomicInteger();

        List<Thread> threads = new ArrayList();

        for (int i = 0; i < 10; i++) {
            threads.add(new Thread(t::m, "thread-" + i));
        }

        for (Thread thread : threads) thread.start();
        for (Thread thread : threads) thread.join();

        System.out.println(t.count);
    }
}
