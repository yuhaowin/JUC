package com.mashibing.juc.c00;

public class DeadLock {
    public static void main(String[] args) {
        Object o1 = new Object();
        Object o2 = new Object();
        new Thread(() -> {
            synchronized (o1){
                System.out.println("A");
                try {
                    Thread.sleep(10000);
                    synchronized (o2){
                        System.out.println("B");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        new Thread(() -> {
            synchronized (o2){
                System.out.println("C");
                try {
                    Thread.sleep(10000);
                    synchronized (o1){
                        System.out.println("D");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
