package com.mashibing.juc.c09;

public class Son extends Parent {
    @Override
    public synchronized void m() {
        super.m();
        System.out.println("Son method is called");
        System.out.println("当前的对象锁是："+this);
    }
    public static void main(String[] args) {
        Parent son = new Son();
        son.m();
    }
}
