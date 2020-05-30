package com.yuhaowin.test;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RingBufferWheelTest {

    private static Logger logger = LoggerFactory.getLogger(RingBufferWheelTest.class);

    public static void main(String[] args) throws Exception {

        test5();
    }

    private static void test5() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        RingBufferWheel wheel = new RingBufferWheel(executorService, 512);
        wheel.start();

        for (int i = 0; i < 5; i++) {
            RingBufferWheel.Task task = new Job("myjob " + i,(int) (Math.random()*10 + 2));
            wheel.addTask(task);
        }
        System.out.println("task size = " + wheel.taskSize());
//        wheel.stop(false);

        TimeUnit.SECONDS.sleep(15);

        RingBufferWheel.Task task = new Job("myjob",(int) (Math.random()*10 + 2));
        wheel.addTask(task);
        System.out.println("task size = " + wheel.taskSize());
    }


    private static class Job extends RingBufferWheel.Task {
        private String name;

        public Job(String name,int s) {
            super(s);
            this.name = name;
        }

        @Override
        public void run() {
            System.out.println("Job = " + name + "  " + this.getDelaySeconds() +" 秒后被执行。");
        }
    }
}
