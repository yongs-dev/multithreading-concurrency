package com.mark.multithreadingconcurrency.concurrencyproblem;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

// CriticalSection
// Synchronized keyword, Lock Object
@Slf4j
public class AtomicOperationTest {
    
    @Test
    public void criticalSectionTest() throws Exception {
        InventoryCounter inventoryCounter = new InventoryCounter();
        IncrementThread incrementThread = new IncrementThread(inventoryCounter);
        DecrementThread decrementThread = new DecrementThread(inventoryCounter);

        incrementThread.start();
        decrementThread.start();

        incrementThread.join();
        decrementThread.join();

        log.info("We currently have {} items", inventoryCounter.getItems());
    }

    public static class DecrementThread extends Thread {
        private final InventoryCounter inventoryCounter;

        public DecrementThread(InventoryCounter inventoryCounter) {
            this.inventoryCounter = inventoryCounter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                inventoryCounter.decrement();
            }
        }
    }

    public static class IncrementThread extends Thread {
        private final InventoryCounter inventoryCounter;

        public IncrementThread(InventoryCounter inventoryCounter) {
            this.inventoryCounter = inventoryCounter;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10000; i++) {
                inventoryCounter.increment();
            }
        }
    }

    public static class InventoryCounter {
        private int items = 0;

        final Object lock = new Object();

        public void increment() {
            synchronized (this.lock) {
                items++;
            }
        }

        public void decrement() {
            synchronized (this.lock) {
                items--;
            }
        }

        public int getItems() {
            synchronized (this.lock) {
                return items;
            }
        }
    }

    // Other Solution: add synchronized keyword to increment and checkForDataRace Method
    // synchronized는 Race Condition에 대한 해법. Data Race는 volatile 키워드 사용
    @Test
    public void DataRaceTest() {
        SharedClass sharedClass = new SharedClass();
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                sharedClass.increment();
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                sharedClass.checkForDataRace();
            }
        });

        thread1.start();
        thread2.start();
    }

    public static class SharedClass {
        private volatile int x = 0;
        private volatile int y = 0;

        public void increment() {
            x++;
            y++;
        }

        public void checkForDataRace() {
            if (y > x) {
                log.info("y > x - Data Race is detected");
            }
        }
    }
}
