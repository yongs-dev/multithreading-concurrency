package com.mark.multithreadingconcurrency.iomodel;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class IoBoundApplicationTest {

    private final static int NUMBER_OF_TASKS = 10_000;

    @Test
    public void ioBoundApplicationTest() {
        log.info("Running {} Tasks", NUMBER_OF_TASKS);

        long start = System.currentTimeMillis();
        performTasks();

        log.info("Task took {} to complete", System.currentTimeMillis() - start);

    }

    private void performTasks() {
        /**
         * Thread-per-task model에서 Executors.newCachedThreadPool() 사용 시 운영체제가 플랫폼 스레드를 더 많이 할당하는 것을 거부하여 오류 발생(OOM)
         * -> 작업이 큐에 대기 중일 때도 스레드를 유지하기 때문에 만약 작업이 큐에 계속 쌓이면 메모리 많이 소비. newCachedThreadPool은 요청된 작업 수에 따라 무제한 스레드 생성
         * -> 작업량이 예측 가능하고 제한적인 경우 Executors.newFixedThreadPool 사용 권장
         */
//        try(ExecutorService executorService = Executors.newFixedThreadPool(1000)) {
//            for (int i = 0; i < NUMBER_OF_TASKS; i++) {
//                // 하나의 블록킹 호출 실행
////                executorService.submit(this::blockingIoOperation);
//
//                // 블록킹 호출 100번 실행
//                // blockingIoOperation sleep 시간을 줄여 위와 동일하게 전체 실행 시간을 통일해도
//                // Context Switching 비용이 크므로 위 방식에 비해 리소스 많이 사용(느려!)
//                executorService.submit(() -> {
//                    for (int j = 0; j < 100; j++) {
//                        blockingIoOperation();
//                    }
//                });
//            }
//        }

        // 발생하는 모든 작업에 대해 새로운 VirtualThread 생성
        try (ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor()) {
            for (int i = 0; i < NUMBER_OF_TASKS; i++) {
                // 하나의 블록킹 호출 실행
//                executorService.submit(this::blockingIoOperation);

                // 블록킹 호출 100번 실행
                // 가상 스레드를 마운트하고 마운트 해제하는 과정이 Context Switch 비교하여 훨씬 빠름
                executorService.submit(() -> {
                    for (int j = 0; j < 100; j++) {
                        blockingIoOperation();
                    }
                });
            }
        }
    }

    private void blockingIoOperation() {
        log.info("Executing a blocking task from thread: {}", Thread.currentThread());

        try {
            Thread.sleep(10);
        } catch (InterruptedException ignored) {}
    }
}
