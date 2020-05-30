package com.yuhaowin.juc.c00;

public class T00_Thread {

    public static void main(String[] args){
//        a();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName());
        },"thread one").start();
        System.out.println("main");
    }



    static void a() {
        b();
        System.out.println("a");
    }
    static void b() {
        System.out.println("b");
    }
}
