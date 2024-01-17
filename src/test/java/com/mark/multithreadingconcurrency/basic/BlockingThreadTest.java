package com.mark.multithreadingconcurrency.basic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

@Slf4j
public class BlockingThreadTest {

    @Test
    @DisplayName("interrupt 호출 시 Interrupt Exception 발생")
    public void interruptTest() {
        Thread thread = new Thread(new BlockingTask());
        thread.start();
        thread.interrupt();
    }

    private static class BlockingTask implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(500000);
            } catch (InterruptedException ignored) {
                log.error("Exiting blocking thread");
            }
        }
    }

    public static void main(String[] args) {
        Thread thread = new Thread(new LongComputationTask(new BigInteger("9999999"), new BigInteger("9999999999999999")));
        // Daemon Thread 설정 시 main Thread가 종료되면 다 종료됨
        thread.setDaemon(true);
        thread.start();
        thread.interrupt();
    }

    @Test
    @DisplayName("Interrupt 호출 시 Thread.currentThread().isInterrupted() 제어")
    public void longComputationTest() {
        Thread thread = new Thread(new LongComputationTask(new BigInteger("9999999"), new BigInteger("9999999999999999")));
        thread.start();
        thread.interrupt();
    }

    private static class LongComputationTask implements Runnable {

        private BigInteger base;
        private BigInteger power;

        public LongComputationTask(BigInteger base, BigInteger power) {
            this.base = base;
            this.power = power;
        }

        @Override
        public void run() {
            log.info("{} ^ {} = {}", base, power, pow(base, power));
        }

        private BigInteger pow(BigInteger base, BigInteger power) {
            BigInteger result = BigInteger.ONE;

            for (BigInteger i = BigInteger.ZERO ; i.compareTo(power) != 0 ; i = i.add(BigInteger.ONE)) {
                // setDaemon true 설정 시 주석
                if (Thread.currentThread().isInterrupted()) {
                    log.info("Prematurely interrupted computation");
                    return BigInteger.ZERO;
                }

                result = result.multiply(base);
            }

            return result;
        }
    }
}
