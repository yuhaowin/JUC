package com.yuhaowin.juc.c30;

import java.util.*;

public class Test {

    private static List<String> data = new ArrayList<>();

    static {
        for (int i = 0; i < 20_00000; i++) {
            data.add(UUID.randomUUID().toString());
        }
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        Set<String> set = new HashSet<>();




        for (String s : data) {
            list.add(s);
        }
        list.add("yuhao");

        long millis = System.currentTimeMillis();

        System.out.println(list.contains("yuhao"));

        System.out.println(System.currentTimeMillis() - millis);




        for (String s : data) {
            set.add(s);
        }
        set.add("yuhao");

        long millis1 = System.currentTimeMillis();

        System.out.println(set.contains("yuhao"));

        System.out.println(System.currentTimeMillis() - millis1);

    }
}
