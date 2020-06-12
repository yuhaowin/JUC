package com.yuhaowin.juc.c09;

public class Parent {
    public  void m(int flag) {
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("flag = " + flag +" i = " +i);
        }
        System.out.println("Parent m method is called");
        System.out.println("当前的对象锁是：" + this);
    }
}
