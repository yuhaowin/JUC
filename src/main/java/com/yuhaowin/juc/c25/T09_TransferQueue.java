package com.yuhaowin.juc.c25;

import java.util.concurrent.LinkedTransferQueue;

public class T09_TransferQueue {

    public static void main(String[] args) throws InterruptedException {

        LinkedTransferQueue<String> queue = new LinkedTransferQueue<>();

        new Thread(() -> {
            try {
                System.out.println(queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        queue.transfer("aaa");

        //strs.put("aaa");
    }
}
