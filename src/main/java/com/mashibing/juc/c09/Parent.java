package com.mashibing.juc.c09;

public class Parent {
    public synchronized void m() {
        System.out.println("Parent m method is called");
        System.out.println("当前的对象锁是："+this);
    }
}
