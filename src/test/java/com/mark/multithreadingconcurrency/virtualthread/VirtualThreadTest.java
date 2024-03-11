package com.mark.multithreadingconcurrency.virtualthread;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class VirtualThreadTest {

    private static final int NUMBER_OF_VIRTUAL_THREADS = 100;

    /**
     * Inside thread: VirtualThread[#4374]/runnable@ForkJoinPool-1-worker-2
     * Inside thread: VirtualThread[#17101]/runnable@ForkJoinPool-1-worker-8
     *
     * ForkJoinPool 이라는 PlatformThreadPool 사용. worker-8 까지인 이유는 CPU Core 8개이기 때문
     */
    @Test
    public void virtualThreadTest() throws Exception {
        List<Thread> virtualThreads = new ArrayList<>();

        for (int i = 0; i < NUMBER_OF_VIRTUAL_THREADS; i++) {
            virtualThreads.add(Thread.ofVirtual().unstarted(new BlockingTask()));
        }

        // start, join을 다른 루프로 진행하는 경우 모든 스레드가 병렬로 실행되며 그 다음에 메인 스레드가 각 스레드의 종료를 기다림
        // -> 모든 스레드를 병렬로 시작한 후 모든 스레드의 종료를 기다림(Non-Blocking)
        for (Thread virtualThread : virtualThreads) {
            virtualThread.start();
        }

        for (Thread virtualThread : virtualThreads) {
            virtualThread.join();
        }

        // 한 번에 하나의 스레드가 실행되고 그 스레드의 종료를 기다린 후에 다음 스레드가 실행
        // -> 각 스레드의 시작과 종료를 반복적으로 순차적으로 처리(Blocking)
//        for (Thread virtualThread : virtualThreads) {
//            virtualThread.start();
//            virtualThread.join();
//        }
    }

    /**
     * Inside thread: VirtualThread[#34]/runnable@ForkJoinPool-1-worker-1 before blocking call
     * Inside thread: VirtualThread[#35]/runnable@ForkJoinPool-1-worker-2 before blocking call
     * Inside thread: VirtualThread[#34]/runnable@ForkJoinPool-1-worker-1 after blocking call
     * Inside thread: VirtualThread[#35]/runnable@ForkJoinPool-1-worker-4 after blocking call
     *
     * 35번 가상 스레드가 JVM에 의해 플랫폼 스레드 2번 작업자에 마운트 되었지만
     * Sleep 시간이 끝났을 때는 플랫폼 스레드 4번 작업자에 마운트
     */
    private static class BlockingTask implements Runnable {

        @Override
        public void run() {
            log.info("Inside thread: {} before blocking call", Thread.currentThread());

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {}

            log.info("Inside thread: {} after blocking call", Thread.currentThread());
        }
    }
}
