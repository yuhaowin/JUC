package com.yuhaowin.juc.c22_threadlocal;

public class M {
    @Override
    protected void finalize() {
        // 重新该方法是为了观察实例有没有被垃圾回收器回收
        System.out.println("finalize");
    }
}
