package com.mark.multithreadingconcurrency.threadcommunication;

import com.mark.multithreadingconcurrency.RunUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class SemaphoreTest {

    @Test
    public void semaphoreTest() {
        int numberOfThreads = 200;

        List<Thread> threads = new ArrayList<>();

        Barrier barrier = new Barrier(numberOfThreads);
        for (int i = 0; i < numberOfThreads; i++) {
            threads.add(new Thread(new CoordinatedWorkRunner(barrier)));
        }

        for (Thread thread: threads) {
            thread.start();
        }

        RunUtil.noStopTest();
    }

    public static class Barrier {
        private final int numberOfWorkers;

        // 세마포어를 0으로 초기화해 세마포어를 획득하려는 모든 스레드가 블록
        private final Semaphore semaphore = new Semaphore(0);
        private int counter = 0;
        private final Lock lock = new ReentrantLock();

        public Barrier(int numberOfWorkers) {
            this.numberOfWorkers = numberOfWorkers;
        }

        public void waitForOthers() throws InterruptedException {
            lock.lock();
            boolean isLastWorker = false;
            try {
                counter++;

                if (counter == numberOfWorkers) {
                    isLastWorker = true;
                }
            } finally {
                lock.unlock();
            }

            // barrier에 도달한 마지막 스레드가 numberOfWorkers - 1 세마포어를 릴리스
            if (isLastWorker) {
                semaphore.release(numberOfWorkers - 1);
            } else {
                semaphore.acquire();
            }
        }
    }

    public static class CoordinatedWorkRunner implements Runnable {
        private final Barrier barrier;

        public CoordinatedWorkRunner(Barrier barrier) {
            this.barrier = barrier;
        }

        @Override
        public void run() {
            try {
                task();
            } catch (InterruptedException ignored) {}
        }

        private void task() throws InterruptedException {
            // Performing Part 1
            log.info("{} part 1 of the work is finished", Thread.currentThread().getName());

            barrier.waitForOthers();

            // Performing Part2
            log.info("{} part 2 of the work is finished", Thread.currentThread().getName());
        }
    }

}
