package com.yuhaowin.test;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

/**
 * @author yuhao
 * @version 5.11.0
 * @date 2020年12月07日 15:51:00
 */
public class UUIDTest {

    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String s = generateSequenceID();
                    System.out.println(s);
                }
            }).start();
        }

    }

    public static String generateSequenceID() {
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }

}