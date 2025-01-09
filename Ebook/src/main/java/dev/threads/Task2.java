package dev.threads;

import lombok.SneakyThrows;

//todo: чередование потоков
public class Task2 {
    public static void main(String[] args) {
        MyThread thread1 = new MyThread();
        MyThread thread2 = new MyThread();
        thread1.start();
        thread2.start();
    }

    private static class MyThread extends Thread {
        @Override
        @SneakyThrows
        public void run() {
            for (int i = 0; i < 10; i++) {
                synchronized (Task2.class) {
                    System.out.println(Thread.currentThread().getName());
                    Task2.class.notify();
                    Thread.sleep(200);
                    Task2.class.wait();
                }
            }
        }
    }
}


