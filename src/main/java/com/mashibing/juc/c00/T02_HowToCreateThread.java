package com.mashibing.juc.c00;

public class T02_HowToCreateThread {
    static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("Hello MyThread!");
        }
    }

    static class MyRun implements Runnable {
        @Override
        public void run() {
            System.out.println("Hello MyRun!");
            for (int i = 0; i < 50; i++) {
                System.out.println("MyRun：" + i);
            }
        }
    }

    public static void main(String[] args) {
//        new MyThread().start();
//        new Thread(new MyRun()).start();
//        new Thread(()->{
//            System.out.println("Hello Lambda!");
//       rt();

        // test run 方法
        MyRun run = new MyRun();
        //run.run();
        Thread thread = new Thread(run);
        thread.start();
        for (int i = 0; i < 50; i++) {
            System.out.println("main : " + i);
        }
    }

}

//请你告诉我启动线程的三种方式 1：Thread 2: Runnable 3:Executors.newCachedThrad
