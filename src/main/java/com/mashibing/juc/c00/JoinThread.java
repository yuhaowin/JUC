package com.mashibing.juc.c00;

public class JoinThread extends Thread {
    int count;
    Thread previousThread; //上一个线程
    public JoinThread(Thread previousThread, int count) {
        this.previousThread = previousThread;
        this.count = count;
    }
    @Override
    public void run() {
        try {
            previousThread.join();
            System.out.println("previousThreadName: " + previousThread.getName() + " currentThreadName: " + getName() + " num: " + count);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws InterruptedException {
        Thread previousThread = Thread.currentThread();
        for (int i = 0; i < 10; i++) {
            JoinThread joinThread = new JoinThread(previousThread, i);
            joinThread.start();
            previousThread = joinThread;
        }
        Thread.sleep(1000);
        System.out.println("main");
        Thread.sleep(1000);
    }
}
