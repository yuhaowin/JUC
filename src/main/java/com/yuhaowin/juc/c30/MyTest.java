package com.yuhaowin.juc.c30;

import java.util.HashMap;
import java.util.Map;

public class MyTest {

    public void use(int arg){
        arg = arg + 1;
        System.out.println(arg);
    }

    public void use(Map map){
        map.put("age",18);
        System.out.println(map);
    }

    public static void main(String[] args) {
        MyTest test = new MyTest();

        int a = 1;
        System.out.println(a);
        test.use(a);
        System.out.println(a);

        Map map = new HashMap<>();
        map.put("name","yuhao");
        System.out.println(map);
        test.use(map);
        System.out.println(map);
    }
}
