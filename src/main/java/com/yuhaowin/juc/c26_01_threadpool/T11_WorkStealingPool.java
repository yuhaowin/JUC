package com.yuhaowin.juc.c26_01_threadpool;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 每一个线程都有自己单独的队列
 * <p>
 * ForkJoinPool
 */
public class T11_WorkStealingPool {
    public static void main(String[] args) throws IOException {
        ExecutorService service = Executors.newWorkStealingPool();

        service.execute(new Task(1000));
        service.execute(new Task(2000));
        service.execute(new Task(2000));
        service.execute(new Task(2000));
        service.execute(new Task(2000));

        //由于产生的是精灵线程（守护线程、后台线程），主线程不阻塞的话，看不到输出
        System.in.read();
    }

    static class Task implements Runnable {

        int time;

        Task(int time) {
            this.time = time;
        }

        @Override
        public void run() {

            try {
                TimeUnit.MILLISECONDS.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(time + " " + Thread.currentThread().getName());
        }
    }
}
