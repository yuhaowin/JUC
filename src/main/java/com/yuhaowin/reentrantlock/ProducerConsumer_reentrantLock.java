package com.yuhaowin.reentrantlock;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumer_reentrantLock {

    public static void main(String[] args) {

        ProducerConsumer_reentrantLock producerConsumer = new ProducerConsumer_reentrantLock();

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

    private final Lock lock = new ReentrantLock();
    private final Condition conditionConsumer = lock.newCondition();
    private final Condition conditionProducer = lock.newCondition();

    public void add() {
        try {
            lock.lock();
            while (listIcecream.size() >= max) { //这里要使用 while，当我被唤醒后还需要检查一下
                try {
                    System.out.println("柜台冰激凌已满了,请消费===" + Thread.currentThread().getName());
                    conditionProducer.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            listIcecream.add(new Object());
            conditionConsumer.signal();
        } catch (Exception e) {
            //ignore
        } finally {
            lock.unlock();
        }
    }

    public Object take() {
        Object ic = null;
        try {
            lock.lock();
            while (listIcecream.size() <= 0) {
                try {
                    System.out.println("柜台没有冰激凌了,等着吧====" + Thread.currentThread().getName());
                    conditionConsumer.await();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            ic = listIcecream.remove();
            System.out.println("已经消费了,还有 " + listIcecream.size() + "个,请继续生产冰激凌");
            conditionProducer.signal();
        } catch (Exception e) {
            //ignore
        } finally {
            lock.unlock();
        }
        return ic;
    }
}
