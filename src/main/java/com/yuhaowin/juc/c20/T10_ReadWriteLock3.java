package com.yuhaowin.juc.c20;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class T10_ReadWriteLock3 {
    public static void main(String[] args) {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        lock.writeLock().lock();
        System.out.println("write");
        lock.readLock().lock();
        System.out.println("read");
    }
}
