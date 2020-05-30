package com.yuhaowin.juc.c25;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public class T08_SynchronousQueue { //容量为0

    public static void main(String[] args) throws InterruptedException {

        SynchronousQueue<String> queue = new SynchronousQueue<>();

        new Thread(() -> {
            try {
                while (true) {
                    System.out.println(queue.take());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        //queue.put("aaa"); //阻塞等待消费者消费
        //queue.put("bbb");
        //queue.add("aaa");
        TimeUnit.SECONDS.sleep(2);
        System.out.println(queue.offer("43434"));
        //System.out.println(queue.size());
    }
}
