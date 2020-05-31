package com.yuhaowin.juc.c26_01_threadpool;

import java.util.concurrent.*;

/**
 * 认识future
 * 异步
 */
public class T04_Future {
    public static void main(String[] args) throws InterruptedException, ExecutionException {

        //FutureTask 既是一个Runnable 又是一个Future，因此本身是可以接收异步返回结果的。
        FutureTask<Integer> task = new FutureTask<>(() -> {
            TimeUnit.MILLISECONDS.sleep(500);
            return 1000;
        });

        new Thread(task).start();

        System.out.println(task.get()); //阻塞


        //------------------------------------------------------------------------------------

        ExecutorService service = Executors.newCachedThreadPool();

        Future future = service.submit(
                () -> {
                    System.out.println(1);
                    return 1;
                }
        );

        System.out.println(future.get());
        System.out.println(future.isDone());
    }
}
