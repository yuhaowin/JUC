package com.yuhaowin.juc.c20;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class T10_ReadWriteLock2 {
    public static void main(String[] args) {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        lock.readLock().lock();
        System.out.println("read");
        lock.writeLock().lock();
        System.out.println("write");
    }
}
