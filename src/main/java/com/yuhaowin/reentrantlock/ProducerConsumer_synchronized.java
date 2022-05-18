package com.yuhaowin.reentrantlock;

import java.util.LinkedList;

public class ProducerConsumer_synchronized {

    public static void main(String[] args) {

        ProducerConsumer_synchronized producerConsumer = new ProducerConsumer_synchronized();

        var producerNum = 3;
        var consumerNum = 3;

        for (int i = 0; i < producerNum; i++) {
            new Thread(() -> {
                while (true) {
                    producerConsumer.add();
                }
            }, "producer-" + i).start();
        }

        for (int i = 0; i < consumerNum; i++) {
            new Thread(() -> {
                while (true) {
                    producerConsumer.take();
                }
            }, "consumer-" + i).start();
        }

        System.out.println("starting...");
    }


    private final LinkedList<Object> listIcecream = new LinkedList<>();

    private final int max = 10;

    public synchronized void add() {
        while (listIcecream.size() >= max) { //这里要使用 while，当我被唤醒后还需要检查一下
            try {
                System.out.println("柜台冰激凌已满了,请消费===" + Thread.currentThread().getName());
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        listIcecream.add(new Object());
        notifyAll();
    }

    public synchronized Object take() {
        while (listIcecream.size() <= 0) {
            try {
                System.out.println("柜台没有冰激凌了,等着吧====" + Thread.currentThread().getName());
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        Object ic = listIcecream.remove();
        System.out.println("已经消费了,还有 " + listIcecream.size() + "个,请继续生产冰激凌");
        notifyAll();
        return ic;
    }
}
