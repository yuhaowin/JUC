package com.yuhaowin.juc.c25;

import java.util.PriorityQueue;

public class T07_PriorityQueque {
    public static void main(String[] args) {
        PriorityQueue<String> queue = new PriorityQueue<>();

        queue.add("c");
        queue.add("e");
        queue.add("a");
        queue.add("d");
        queue.add("z");

        for (int i = 0; i < 5; i++) {
            System.out.println(queue.poll());
        }

    }
}
