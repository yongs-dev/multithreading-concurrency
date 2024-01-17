package com.mark.multithreadingconcurrency.basic;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class CoordinationThreadTest {

    // Thread.join() 이용한 Thread 간 대기
    public static void main(String[] args) throws InterruptedException {
        List<Long> numbers = Arrays.asList(10000000000L, 3435L, 35435L, 2324L, 4656L, 23L, 2435L, 5566L);

        List<FactorialThread> threads = numbers.stream()
                .map(FactorialThread::new)
                .toList();

        for (Thread thread : threads) {
            // Daemon Thread 설정 시 main Thread가 종료되면 다 종료됨
            thread.setDaemon(true);
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join(2000);
        }

        for (int i = 0; i < numbers.size(); i++) {
            FactorialThread factorialThread = threads.get(i);

            if (factorialThread.isFinished()) {
                log.info("Factorial of {} is {}", numbers.get(i), factorialThread.getResult());
            } else {
                log.info("The calculation for {} is still is progress", numbers.get(i));
            }
        }
    }

    private static class FactorialThread extends Thread {
        private final long number;

        @Getter
        private BigInteger result = BigInteger.ZERO;

        @Getter
        private boolean isFinished = false;

        public FactorialThread(long number) {
            this.number = number;
        }

        @Override
        public void run() {
            this.result = factorial(number);
            this.isFinished = true;
        }

        public BigInteger factorial(long n) {
            BigInteger tempResult = BigInteger.ONE;

            for (long i = n; i > 0; i--) {
                tempResult = tempResult.multiply(new BigInteger(Long.toString(i)));
            }

            return tempResult;
        }
    }
}
