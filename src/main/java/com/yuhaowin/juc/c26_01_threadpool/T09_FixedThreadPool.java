package com.yuhaowin.juc.c26_01_threadpool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class T09_FixedThreadPool {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        long start = System.currentTimeMillis();
        getPrime(1, 200000);
        long end = System.currentTimeMillis();
        System.out.println("串行执行的时间：" + (end - start));

        final int cpuCoreNum = Runtime.getRuntime().availableProcessors();

        System.out.println("服务器核心线程数：" + cpuCoreNum);

        // 全是核心线程
        ExecutorService service = Executors.newFixedThreadPool(cpuCoreNum);

        PrimeTask t1 = new PrimeTask(1, 80000);
        PrimeTask t2 = new PrimeTask(80001, 130000);
        PrimeTask t3 = new PrimeTask(130001, 170000);
        PrimeTask t4 = new PrimeTask(170001, 200000);

        Future<List<Integer>> f1 = service.submit(t1);
        Future<List<Integer>> f2 = service.submit(t2);
        Future<List<Integer>> f3 = service.submit(t3);
        Future<List<Integer>> f4 = service.submit(t4);

        start = System.currentTimeMillis();
        f1.get();
        f2.get();
        f3.get();
        f4.get();
        end = System.currentTimeMillis();
        System.out.println("并行执行的时间：" + (end - start));
    }


    static boolean isPrime(int num) {
        for (int i = 2; i <= num / 2; i++) {
            if (num % i == 0) return false;
        }
        return true;
    }

    static List<Integer> getPrime(int start, int end) {
        List<Integer> results = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            if (isPrime(i)) results.add(i);
        }
        return results;
    }

    static class PrimeTask implements Callable<List<Integer>> {
        int startPos, endPos;

        PrimeTask(int s, int e) {
            this.startPos = s;
            this.endPos = e;
        }

        @Override
        public List<Integer> call() {
            return getPrime(startPos, endPos);
        }
    }
}
