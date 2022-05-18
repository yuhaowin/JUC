package com.yuhaowin.juc.c30;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Comparable {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();

        list.add("zhang");
        list.add("li");
        list.add("wang");

        System.out.println("排序前： " + list);
        Collections.sort(list);
        System.out.println("排序后：" + list);

        
        Collections.sort(list, (str1, str2) -> {

            if (str1.length() > str2.length()) {
                return -1;
            } else if (str1.length() < str2.length()) {
                return 1;
            }
            return 0;

        });

        System.out.println("排序后：" + list);
    }


}
