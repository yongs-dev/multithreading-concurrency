package com.mark.multithreadingconcurrency.thread;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Slf4j
public class SimpleThreadTest {
    
    @Test
    @DisplayName("new Thread(new Runnable() {@Override} 사용")
    public void newThreadConstructorTest() throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // Code that will run in a new thread
                log.info("Now in thread: {}, current thread priority is {}", Thread.currentThread().getName(), Thread.currentThread().getPriority());
            }
        });

        thread.setName("New Worker Thread");
        thread.setPriority(Thread.MAX_PRIORITY);

        log.info("In thread: {}. before starting a new thread", Thread.currentThread().getName());
        thread.start();
        log.info("In thread: {}. after starting a new thread", Thread.currentThread().getName());

        Thread.sleep(3000);
    }

    @Test
    @DisplayName("UncaughtExceptionHandler 예외 핸들러")
    public void uncaughtExceptionHandler() throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                throw new RuntimeException("Intentional Exception");
            }
        });

        thread.setName("Misbehaving thread");
        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                log.error("A critical error happened in thread {} the error is {}", t.getName(), e.getMessage());
            }
        });
        thread.start();
    }
}
