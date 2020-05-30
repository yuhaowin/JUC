package com.yuhaowin.juc.c09;

/**
 * 演示可重入锁是什么意思，可重入，就是可以重复获取相同的锁，synchronized和ReentrantLock都是可重入的
 */
public class WhatReentrant {
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (this) {
                    System.out.println("第1次获取锁，这个锁是：" + this);
                    int index = 1;
                    while (true) {
                        synchronized (this) {
                            System.out.println("第" + (++index) + "次获取锁，这个锁是：" + this);
                        }
                        if (index == 5) {
                            break;
                        }
                    }
                }
            }
        }).start();
    }
}

