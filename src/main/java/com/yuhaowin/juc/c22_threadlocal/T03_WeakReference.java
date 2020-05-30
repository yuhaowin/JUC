package com.yuhaowin.juc.c22_threadlocal;

import java.io.IOException;
import java.lang.ref.WeakReference;

/**
 * 弱引用遭到 gc 就会回收
 * 一般使用在容器中，弱引用的主要用途是，一旦指向对象
 * 的强引用断开，就无需关心该对象不会被回收。
 * <p>
 * WeakHashMap
 */
public class T03_WeakReference {
    public static void main(String[] args) throws IOException {
        WeakReference<M> m = new WeakReference<>(new M());

        System.out.println(m.get());
        System.gc();
        System.out.println(m.get());

        ThreadLocal<M> tl = new ThreadLocal<>();
        tl.set(new M());
        tl.remove();
        System.gc();
        System.in.read();
    }
}

