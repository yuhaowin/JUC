package com.yuhaowin.test;

import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Function:Ring Queue, it can be used to delay task.
 *
 * @author crossoverJie
 * Date: 2019-09-20 14:46
 * @since JDK 1.8
 */
public final class RingBufferWheel {

    private Logger logger = LoggerFactory.getLogger(RingBufferWheel.class);

    /**
     * default ring buffer size
     */
    private static final int DEFAULT_POOL_SIZE = 64;

    private Object[] ringBuffer;

    private int bufferSize;

    /**
     * current task size
     */
    private volatile int size = 0;

    /**
     * business thread pool
     */
    private ExecutorService executorService;

    /***
     * task stop sign
     */
    private volatile boolean stop = false;

    /**
     * task start sign
     */
    private volatile AtomicBoolean start = new AtomicBoolean(false);

    /**
     * total tick times
     */
    private AtomicInteger tick = new AtomicInteger();

    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    /**
     * Create a new delay task ring buffer by default size
     *
     * @param executorService the business thread pool
     */
    public RingBufferWheel(ExecutorService executorService) {
        this.executorService = executorService;
        this.bufferSize = DEFAULT_POOL_SIZE;
        this.ringBuffer = new Object[bufferSize];
    }

    /**
     * Create a new delay task ring buffer by custom buffer size
     *
     * @param executorService the business thread pool
     * @param bufferSize      custom buffer size
     */
    public RingBufferWheel(ExecutorService executorService, int bufferSize) {
        this(executorService);
        if (!powerOf2(bufferSize)) {
            throw new RuntimeException("bufferSize=[" + bufferSize + "] must be a power of 2");
        }
        this.bufferSize = bufferSize;
        this.ringBuffer = new Object[bufferSize];
    }

    /**
     * Start background thread to consumer wheel timer, it will always run until you call method {@link #stop}
     */
    public void start() {
        if (!start.get()) {
            if (start.compareAndSet(start.get(), true)) {
                logger.debug("delay task is starting");
                Thread job = new Thread(new TriggerJob());
                job.setName("consumer RingBuffer thread");
                job.start();
                start.set(true);
            }
        }
    }

    /**
     * Stop consumer ring buffer thread
     *
     * @param force True will force close consumer thread and discard all pending tasks
     *              otherwise the consumer thread waits for all tasks to completes before closing.
     */
    public void stop(boolean force) {
        if (force) {
            logger.debug("delay task is forced stop");
            stop = true;
            executorService.shutdownNow();
        } else {
            logger.debug("delay task is stopping");
            if (taskSize() > 0) {
                try {
                    lock.lock();
                    condition.await();
                    stop = true;
                } catch (InterruptedException e) {
                    logger.error("InterruptedException", e);
                } finally {
                    lock.unlock();
                }
            }
            executorService.shutdown();
        }
    }

    /**
     * Add a task into the ring buffer(thread safe)
     *
     * @param task business task extends {@link Task}
     */
    public void addTask(Task task) {
        int delaySeconds = task.getDelaySeconds();
        try {
            lock.lock();
            int index = mod(delaySeconds, bufferSize);
            int cycleNum = cycleNum(delaySeconds, bufferSize);
            task.setIndex(index);
            task.setCycleNum(cycleNum);
            Set<Task> tasks = get(index);
            if (tasks != null) {
                tasks.add(task);
            } else {
                Set<Task> sets = new HashSet<>();
                sets.add(task);
                put(delaySeconds, sets);
            }
            size++;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Thread safe
     *
     * @return the size of ring buffer
     */
    public int taskSize() {
        return size;
    }

    private Set<Task> get(int index) {
        return (Set<Task>) ringBuffer[index];
    }

    private void put(int key, Set<Task> tasks) {
        int index = mod(key, bufferSize);
        ringBuffer[index] = tasks;
    }

    private Set<Task> remove(int key) {
        Set<Task> result = new HashSet<>();
        Set<Task> tasks = (Set<Task>) ringBuffer[key];
        if (tasks == null) {
            return result;
        }
        Set<Task> tempTask = new HashSet<>();
        for (Task task : tasks) {
            if (task.getCycleNum() == 0) {
                result.add(task);
                size2Notify();
            } else {
                // decrement 1 cycle number and update origin data
                task.setCycleNum(task.getCycleNum() - 1);
                tempTask.add(task);
            }
        }
        //update origin data
        ringBuffer[key] = tempTask;
        return result;
    }

    private void size2Notify() {
        try {
            lock.lock();
            size--;
            if (size == 0) {
                condition.signal();
            }
        } finally {
            lock.unlock();
        }
    }

    private int mod(int target, int mod) {
        // equals target % mod
        target = target + tick.get();
        return target & (mod - 1);
    }

    private int cycleNum(int target, int mod) {
        //equals target/mod
        return target >> Integer.bitCount(mod - 1);
    }

    private boolean powerOf2(int target) {
        if (target < 0) {
            return false;
        }
        int value = target & (target - 1);
        if (value != 0) {
            return false;
        }
        return true;
    }


    /**
     * An abstract class used to implement business.
     */
    public abstract static class Task extends Thread {
        private int cycleNum;
        private int index;
        // 任务延迟 key 秒后执行
        private int delaySeconds;

        public Task(int delaySeconds) {
            this.delaySeconds = delaySeconds;
        }

        @Override
        public void run() {
        }

        private void setCycleNum(int cycleNum) {
            this.cycleNum = cycleNum;
        }
        private void setIndex(int index) {
            this.index = index;
        }

        public int getDelaySeconds() {
            return delaySeconds;
        }
        public int getCycleNum() {
            return cycleNum;
        }
        public int getIndex() {
            return index;
        }
    }


    private class TriggerJob implements Runnable {
        @Override
        public void run() {
            int index = 0;
            while (!stop) {
                try {
                    Set<Task> tasks = remove(index);
                    for (Task task : tasks) {
                        executorService.submit(task);
                    }
                    if (++index > bufferSize - 1) {
                        index = 0;
                    }
                    //Total tick number of records
                    tick.incrementAndGet();

                    TimeUnit.SECONDS.sleep(1);
                } catch (Exception e) {
                    logger.error("Exception", e);
                }

            }
            logger.debug("delay task is stopped");
        }
    }
}
