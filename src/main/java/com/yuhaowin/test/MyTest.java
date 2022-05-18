package com.yuhaowin.test;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class MyTest {

    private static final Logger logger = LoggerFactory.getLogger(MyTest.class);

    public static void main(String[] args) {
        MyRun run = new MyRun();
        for (int i = 0; i < 1000; i++) {
            Thread thread = new Thread(run);
            thread.start();
        }
    }

    static class MyRun implements Runnable {
        String key = "yuhao";
        User user = new User();
        @Override
        public void run() {
            if (LocalBloomFilterUtils.checkNotExist(key, EtlBizTypeEnum.PER_KEY_ERROR_SNAPSHOT)) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (LocalBloomFilterUtils.addSnapshot(key, EtlBizTypeEnum.PER_KEY_ERROR_SNAPSHOT, null)) {
                    user.setName(UUID.randomUUID().toString());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    user.setFullName(user.getName());
                    System.out.println(user.toString());
                    System.out.println("当前的工作线程是：" + Thread.currentThread().getName());
                }
            }
        }
    }
}


