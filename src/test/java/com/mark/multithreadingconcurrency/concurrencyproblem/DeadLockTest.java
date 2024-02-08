package com.mark.multithreadingconcurrency.concurrencyproblem;

import com.mark.multithreadingconcurrency.RunUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.Random;

@Slf4j
public class DeadLockTest {

    @Test
    public void deadLockTest() {
        Intersection intersection = new Intersection();
        Thread trainAThread = new Thread(new TrainA(intersection));
        Thread trainBThread = new Thread(new TrainB(intersection));

        trainAThread.start();
        trainBThread.start();

        RunUtil.noStopTest();
    }

    public static class TrainB implements Runnable {
        private Intersection intersection;
        private Random random = new Random();

        public TrainB(Intersection intersection) {
            this.intersection = intersection;
        }

        public void run() {
            while (true) {
                long sleepingTime = random.nextInt(5);
                try {
                    Thread.sleep(sleepingTime);
                } catch (InterruptedException ignored) {}

                intersection.takeRoadB();
            }
        }
    }

    public static class TrainA implements Runnable {
        private Intersection intersection;
        private Random random = new Random();

        public TrainA(Intersection intersection) {
            this.intersection = intersection;
        }

        public void run() {
            while (true) {
                long sleepingTime = random.nextInt(5);
                try {
                    Thread.sleep(sleepingTime);
                } catch (InterruptedException ignored) {}

                intersection.takeRoadA();
            }
        }
    }
    public static class Intersection {
        private final Object roadA = new Object();
        private final Object roadB = new Object();

        public void takeRoadA() {
            synchronized (roadA) {
                log.info("Road A is locked by thread {}", Thread.currentThread().getName());

                synchronized (roadB) {
                    log.info("Train is passing through road A");
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException ignored) {
                    }
                }
            }
        }

        // takeRoadA 메소드와 동일하게 잠금해 순환 참조 방지로 DeadLock 해소 - 락킹 순서를 유지해 데드락 예방
        public void takeRoadB() {
            synchronized (roadA) {
//            synchronized (roadB) {
                log.info("Road B is locked by thread {}", Thread.currentThread().getName());

                synchronized (roadB) {
//                synchronized (roadA) {
                    log.info("Train is passing through road B");
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException ignored) {}
                }
            }
        }
    }
}
