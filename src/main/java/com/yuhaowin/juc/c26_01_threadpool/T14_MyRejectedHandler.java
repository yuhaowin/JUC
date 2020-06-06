package com.yuhaowin.juc.c26_01_threadpool;

import java.util.concurrent.*;

public class T14_MyRejectedHandler {
    public static void main(String[] args) {
        ExecutorService service = new ThreadPoolExecutor(4, 4,
                0, TimeUnit.SECONDS, new ArrayBlockingQueue<>(6),
                Executors.defaultThreadFactory(),
                new MyHandler());

        for (int i = 0; i < 100; i++) {
            service.execute(new Task("任务：" + i));
        }
    }

    static class Task implements Runnable {

        private String name;

        public Task(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public void run() {
            System.out.println("正在执行：【" + name + "】任务");
        }
    }

    static class MyHandler implements RejectedExecutionHandler {

        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            if (r instanceof Task) {
                Task task = (Task) r;
                System.out.println("任务：" + task.getName() + "被拒绝");
            }
        }
    }
}
