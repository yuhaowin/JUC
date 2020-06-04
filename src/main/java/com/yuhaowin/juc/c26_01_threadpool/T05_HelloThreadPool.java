package com.yuhaowin.juc.c26_01_threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class T05_HelloThreadPool {

    static class Task implements Runnable {
        private int i;

        public Task(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            //System.out.println(Thread.currentThread().getName() + " Task " + i);
//            try {
//                //System.in.read();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }

        @Override
        public String toString() {
            return "Task{" + "i=" + i + '}';
        }
    }

    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                2,
                4,
                60,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(4),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );

        for (int i = 0; i < 10; i++) {
            executor.execute(new Task(i));
        }

        System.out.println(executor.getQueue());

        //executor.execute(new Task(100));

        System.out.println(executor.getQueue());

        executor.shutdown();
    }
}
