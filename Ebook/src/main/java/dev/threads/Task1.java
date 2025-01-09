package dev.threads;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

public class Task1 {

    //todo: все состояния
    @SneakyThrows
    public static void main(String[] args) {
        MyThread myThread = new MyThread(new Object());
        Thread thread1 = new Thread(new MyThread2());
        Thread thread2 = new Thread(myThread);

        System.out.println("status: " +  thread1.getState()  + "|| NEW");
        thread1.start();
        thread2.start();
        Thread.sleep(500);
        System.out.println("status: "+ thread1.getState() + "|| WAITING_TIME");
        System.out.println("status: "+ thread2.getState() + "|| WAITING");
        Object lock = myThread.getLock();
        synchronized (lock) {
            lock.notify();
        }

        System.out.println("status: "+ thread2.getState()  + "|| BLOCKED");
        Thread.sleep(1000);
        thread2.join();
        System.out.println("status: "+ thread2.getState()  + "|| TERMINATED");
    }

    private static class MyThread2 implements Runnable {
        @SneakyThrows
        @Override
        public void run() {
            System.out.println("status: " +  Thread.currentThread().getState() + "|| RUNNABLE");
            Thread.sleep(1000);
        }
    }


    @Getter
    @RequiredArgsConstructor
    private static class MyThread implements Runnable {

        private final Object lock;

        @SneakyThrows
        @Override
        public void run() {
            synchronized (lock) {
                lock.wait();
            }
        }
    }
}
