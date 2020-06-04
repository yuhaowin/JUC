package com.yuhaowin.juc.c26_01_threadpool;

import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public class T12_ForkJoinPool {
    static int[] nums = new int[1000000];
    static final int MAX_NUM = 50000;
    static Random r = new Random();

    static {
        for (int i = 0; i < nums.length; i++) {
            nums[i] = r.nextInt(100);
        }
        System.out.println("单线程计算结果：" + Arrays.stream(nums).sum()); //stream api
    }


    static class AddAction extends RecursiveAction {
        int start, end;

        AddAction(int s, int e) {
            start = s;
            end = e;
        }

        @Override
        protected void compute() {
            if (end - start <= MAX_NUM) {
                long sum = 0L;
                for (int i = start; i < end; i++) sum += nums[i];
                System.out.println("from:" + start + " to:" + end + " = " + sum);
            } else {

                int middle = start + (end - start) / 2;

                AddAction subAction1 = new AddAction(start, middle);
                AddAction subAction2 = new AddAction(middle, end);
                subAction1.fork();
                subAction2.fork();
            }
        }
    }


    static class AddTask extends RecursiveTask<Long> {
        private static final long serialVersionUID = 1L;
        int start, end;

        AddTask(int s, int e) {
            start = s;
            end = e;
        }

        @Override
        protected Long compute() {

            if (end - start <= MAX_NUM) {
                long sum = 0L;
                for (int i = start; i < end; i++) sum += nums[i];
                return sum;
            }

            int middle = start + (end - start) / 2;

            AddTask subTask1 = new AddTask(start, middle);
            AddTask subTask2 = new AddTask(middle, end);
            subTask1.fork();
            subTask2.fork();

            return subTask1.join() + subTask2.join();
        }

    }

    public static void main(String[] args) throws IOException {
        ForkJoinPool pool = new ForkJoinPool();
        pool.execute(new AddAction(0, nums.length));

        AddTask task = new AddTask(0, nums.length);
        pool.execute(task);
        System.out.println("ForkJoinPool 计算结果：" + task.join());

        System.in.read();
    }
}
