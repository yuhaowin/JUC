package com.yuhaowin.juc.c09;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Son extends Parent {
//    @Override
//    public void m(int j) {
//        super.m(j);
//        for (int i = 0; i < 10; i++) {
//            try {
//                Thread.sleep(500);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("J = " + j + " i = " + i);
//        }
//        System.out.println("Son method is called");
//        System.out.println("当前的对象锁是：" + this);
//    }

    public static void main(String[] args) {
        Parent son = new Son();
        System.out.println(son);
        //son.m();

        ExecutorService service = Executors.newCachedThreadPool();

        service.execute(() -> {
            son.m(1);
        });

        service.execute(() -> {
            son.m(2);
        });
    }
}
