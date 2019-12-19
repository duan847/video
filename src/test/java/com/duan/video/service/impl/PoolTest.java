package com.duan.video.service.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PoolTest implements Runnable {

    @Override
    public void run() {
        ExecutorService pool = Executors.newFixedThreadPool(5);
        pool.submit(new TestThread());
        pool.submit(new TestThread());
        pool.submit(new TestThread());
        pool.shutdown();
    }

    class TestThread implements Runnable {

        @Override
        public void run() {

            for (int i = 0; i < 1; i++) {
                System.out.println(Thread.currentThread().getId() + ":" + i);
            }
        }
    }

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(5);
        pool.submit(new PoolTest());
        pool.submit(new PoolTest());
        pool.shutdown();
    }
}


