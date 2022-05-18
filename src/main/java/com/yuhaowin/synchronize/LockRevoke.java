package com.yuhaowin.synchronize;

import org.openjdk.jol.info.ClassLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


class Demo {
    String userName;
}

public class LockRevoke {

    public static void main(String[] args) throws InterruptedException {
        List<Demo> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(new Demo());
        }

        final Thread t1 = new Thread(() -> {
            for (int i = 0; i < 99; i++) {
                Demo demo = list.get(i);
                synchronized (demo) {
                }
            }
            try {
                TimeUnit.SECONDS.sleep(100000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });

        final Thread t2 = new Thread(() -> {
            synchronized (list.get(99)) {
                System.out.println("第100个对象上锁中，并持续使用该对象" + ClassLayout.parseInstance(list.get(99)).toPrintable());
                try {
                    TimeUnit.SECONDS.sleep(99999);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        final Thread t3 = new Thread(() -> {
            for (int i = 0; i < 40; i++) {
                Demo demo = list.get(i);
                synchronized (demo) {
                    if (i == 18) {
                        System.out.println("发送第19次锁升级，list.get(18)应该是轻量级锁" + ClassLayout.parseInstance(list.get(18)).toPrintable());
                    }
                    if (i == 19) {
                        System.out.println("发送第20次锁升级，会发生批量重偏向；纪元+1;后续偏向锁都会偏向当前线程；list.get(19)应该是轻量级锁" + ClassLayout.parseInstance(list.get(19)).toPrintable());
                        System.out.println("因为第100对象仍然在使用，需要修改起纪元" + ClassLayout.parseInstance(list.get(99)).toPrintable());
                    }
                    if (i == 29) {
                        System.out.println("在批量重偏向之后；因为第一次偏向锁已经失效了，所以这里不是轻量级而是偏向该线程的偏向锁" + ClassLayout.parseInstance(list.get(29)).toPrintable());
                    }
                    if (i == 39) {
                        System.out.println("发送第40次锁升级，发生批量锁撤销；这里应该是轻量级锁后续都是轻量级" + ClassLayout.parseInstance(list.get(39)).toPrintable());
                    }
                }
            }
        });

        t1.start();
        t2.start();
        TimeUnit.SECONDS.sleep(5);
        System.out.println("第一次上锁后list.get(0)应该偏向锁：" + ClassLayout.parseInstance(list.get(0)).toPrintable());
        System.out.println("第一次上锁后list.get(19)应该偏向锁：" + ClassLayout.parseInstance(list.get(19)).toPrintable());
        System.out.println("第一次上锁后list.get(29)应该偏向锁：" + ClassLayout.parseInstance(list.get(29)).toPrintable());
        System.out.println("第一次上锁后list.get(39)应该偏向锁：" + ClassLayout.parseInstance(list.get(39)).toPrintable());
        System.out.println("第一次上锁后list.get(99)应该偏向锁：" + ClassLayout.parseInstance(list.get(99)).toPrintable());
        t3.start();
    }
}