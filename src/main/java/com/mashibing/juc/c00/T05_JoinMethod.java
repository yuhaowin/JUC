package com.mashibing.juc.c00;

public class T05_JoinMethod {

    public static void main(String[] args) throws InterruptedException {
        Thread mainThread = Thread.currentThread();
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < 200; i++) {
                System.out.println(Thread.currentThread().getName() + ": " + i);
            }
        }, "Thread one");
        thread.start();
        thread.join();
        System.out.println();
        for (int i = 0; i < 50; i++) {
            System.out.println("main : " + i);
        }

    }
}
