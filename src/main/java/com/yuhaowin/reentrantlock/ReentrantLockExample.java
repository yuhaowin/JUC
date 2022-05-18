package com.yuhaowin.reentrantlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockExample {

    private final Lock lock = new ReentrantLock(true);

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockExample example = new ReentrantLockExample();
        example.test1();
        TimeUnit.SECONDS.sleep(2);
        example.test2();
    }

    public void test1() {
        Thread t1 = new Thread(() -> {
            try {
                lock.lock();
                TimeUnit.SECONDS.sleep(999999);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }, "thread-1");
        t1.start();
    }
    
    public void test2() {
        Thread t2 = new Thread(() -> {
            try {
                lock.lock();
                TimeUnit.SECONDS.sleep(999999);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }, "thread-2");
        t2.start();
    }
}
