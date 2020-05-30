package com.mashibing.juc.c18_01_unsafe;

import sun.misc.Unsafe;

public class HelloUnsafe {
    static class M {
        int i =0;
        private M() {}
    }

   public static void main(String[] args) throws InstantiationException {
        Unsafe unsafe = Unsafe.getUnsafe();
        M m = (M)unsafe.allocateInstance(M.class);
        m.i = 9;
        System.out.println(m.i);
    }
}


