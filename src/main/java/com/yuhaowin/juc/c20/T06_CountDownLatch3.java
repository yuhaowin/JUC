package com.yuhaowin.juc.c20;

import java.util.concurrent.CountDownLatch;

public class T06_CountDownLatch3 {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(10);
        for (int i = 0; i < 10; ++i)  // create and start threads
            new Thread(new Worker(startSignal, doneSignal)).start();
        doSomethingElse();
        startSignal.countDown();      // let all threads proceed
        doSomethingElse();
        doneSignal.await();           // wait for all to finish
        System.out.println("finish");
    }
    private static void doSomethingElse(){
        System.out.println("doSomethingElse method");
    }
}

class Worker implements Runnable {
    private final CountDownLatch startSignal;
    private final CountDownLatch doneSignal;
    Worker(CountDownLatch startSignal, CountDownLatch doneSignal) {
        this.startSignal = startSignal;
        this.doneSignal = doneSignal;
    }

    public void run() {
        try {
            startSignal.await();
            doWork();
            doneSignal.countDown();
        } catch (InterruptedException ex) {
        }
    }

    void doWork() {
        System.out.println(Thread.currentThread().getName());
    }
}
