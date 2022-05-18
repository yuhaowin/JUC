package com.yuhaowin.volatiles;

public class Volatile {

    private Integer notVolatileAge = 1;

    private volatile Integer volatileAge = 1;

    public static volatile int counter = 1;

    public Integer getVolatileAge() {
        return volatileAge;
    }

    public Integer getNotVolatileAge() {
        return notVolatileAge;
    }

    public static void main(String[] args) {
        counter = 2;
        System.out.println(counter);
    }
}
