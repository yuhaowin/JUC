package com.mashibing.juc.c12_volatile;

public class Test {

    public  int i = 0;

    public void print() {
        while (true) {
            if (i == 50 || i == 300 || i == 500)
                System.out.println(i);
            if (i > 5000){
                System.out.println(i);
            }
        }
    }

    public void add() {
        while (true) {
            try {
                Thread.sleep(1);
                i++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Test test = new Test();

        new Thread(() -> {
            test.print();
        }).start();

        new Thread(() -> {
            test.add();
        }).start();
    }
}
