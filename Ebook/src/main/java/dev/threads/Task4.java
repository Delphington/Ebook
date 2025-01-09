package dev.threads;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.time.LocalTime;

//todo: каждый n сек вывод

public class Task4 {
    @SneakyThrows
    public static void main(String[] args) {
        MyThread myThread = new MyThread(4);
        Thread thread = new Thread(myThread);
        thread.start();

        Thread.sleep(10000);
        thread.interrupt();
    }

    @RequiredArgsConstructor
    private static class MyThread implements Runnable {
        private final int second;

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                System.out.println(LocalTime.now());
                try {
                    Thread.sleep(second * 1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

}
