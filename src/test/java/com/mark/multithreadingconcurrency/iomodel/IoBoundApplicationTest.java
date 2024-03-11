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
        try (ExecutorService executorService = Executors.newFixedThreadPool(1000)) {
            for (int i = 0; i < NUMBER_OF_TASKS; i++) {
                // 하나의 블록킹 호출 실행
                executorService.submit(this::blockingIoOperation);

                // 블록킹 호출 100번 실행
                // blockingIoOperation sleep 시간을 줄여 위와 동일하게 전체 실행 시간을 통일해도
                // Context Switching 비용이 크므로 위 방식에 비해 리소스 많이 사용
//                executorService.submit(() -> {
//                    for (int j = 0; j < 100; j++) {
//                        blockingIoOperation();
//                    }
//                });
            }
        }
    }

    private void blockingIoOperation() {
        log.info("Executing a blocking task from thread: {}", Thread.currentThread().getName());

        try {
            Thread.sleep(100);
        } catch (InterruptedException ignored) {}
    }
}
