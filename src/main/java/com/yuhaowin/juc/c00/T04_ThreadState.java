package com.yuhaowin.juc.c00;

public class T04_ThreadState {

    static class MyThread extends Thread {
        Thread thread;
        public MyThread(Thread thread) {
            this.thread = thread;
        }
        @Override
        public void run() {
            System.out.println("thread state : " + thread.getState());
            System.out.println(this.getState());
            for (int i = 0; i < 2; i++) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(i);
            }
        }
    }

    public static void main(String[] args) {
        Thread main = Thread.currentThread();
        Thread t = new MyThread(main);
        System.out.println(t.getState());
        t.start();
        try {
            Thread.sleep(5000);
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(t.getState());
    }
}
