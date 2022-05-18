package com.yuhaowin.synchronize;


/**
 * javap -v Synchronized
 * https://wiki.openjdk.java.net/display/HotSpot/Synchronization
 * https://www.oracle.com/technetwork/java/biasedlocking-oopsla2006-wp-149958.pdf
 * https://www.oracle.com/technetwork/java/javase/tech/biasedlocking-oopsla2006-preso-150106.pdf
 * https://www.jianshu.com/p/e47ad923dee5
 */
public class Synchronized {

    /**
     * ACC_SYNCHRONIZED
     * 如果有这个标志位，就会先尝试获取monitor，获取成功才能执行方法，
     * 方法执行完成后再释放monitor。在方法执行期间，其他线程都无法获取同一个monitor。
     */
    public synchronized void method() {

    }

    /**
     * monitorenter
     * monitorexit
     * monitorexit
     * 两个 monitorexit 保证正常退出和异常退出都可以 unlock
     */
    public void methodSynchronizedThis() {
        synchronized (this) {

        }
    }

    /**
     * 偏向锁
     * 1、线程获取一个可以偏向但还未偏向的锁时，通过 cas 把当前 thread id 设置到 mark word thread id field 上
     * 如果成功，就偏向这个线程，这个线程就是这个偏向锁的 owner
     * 如果失败，说明有别的线程是这个偏向锁的 owner，偏向锁需要被撤销，如果这个偏向 owner 没有实际持有锁的话，恢复为可偏但是未偏状态，重新 cas
     * 如果实际持有锁，需要在到达安全点是挂起偏向锁的线程，升级为light lock，然后继续执行，这个过程会 stop the world
     *
     * 轻量级锁
     * 当前线程栈正中创建 lock record 空间，存放 mark word 的拷贝
     * cas 把 mark word 修改为指向Lock Record的指针 && lock record owner 指向 mark word
     * 如果成功，加锁成功
     * 如果失败，检查当前对象都是否指向自己的堆栈，如果是获取成功，否则表示已经有其他线程持有锁，把 mark word 修改为重量级锁，表明将要进入重量级锁
     *
     * 释放锁，把 lock record 中的 mark word -> 对象头上，如果成功说明轻量级锁还有效
     * 如果失败表明已经升级重量级锁了，需要唤醒其他的线程来使用这把锁
     */
}
