package com.mashibing.juc.c22_threadlocal;

import java.io.IOException;

/**
 * 普遍的引用 - 强引用
 */
public class T01_NormalReference {
    public static void main(String[] args) throws IOException {
        M m = new M();
        m = null;

        System.gc(); //DisableExplicitGC

        System.in.read();
    }
}
