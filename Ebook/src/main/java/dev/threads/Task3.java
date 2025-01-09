package dev.threads;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import javax.swing.text.StyledEditorKit;
import java.security.SecureRandom;
import java.util.Random;

public class Task3 {
    private static SecureRandom secureRandom = new SecureRandom();

    //todo: два потока производитель потребитель
    //todo: у потоков общий буфер данных(ограниченный)
    //todo: буффер пуст потребитель ждет, буфер полон производитель ждет

    @AllArgsConstructor
    private static class Buffer {
        private Integer secretInformation;
        private Boolean isEmpty;

        //для producer
        @SneakyThrows
        public void submitData(int value) {
            synchronized (this) {
                while (!isEmpty) {
                    wait();
                }
                secretInformation = value;
                isEmpty = false;
                System.out.println("Produce put: " + value);
                notify();
            }
        }

        //для consumer
        @SneakyThrows
        public int takeData() {
            synchronized (this) {
                while (isEmpty) {
                    wait();
                }
                int consumerData = secretInformation;
                isEmpty = true;
                notify();
                return consumerData;
            }
        }
    }


    @SneakyThrows
    public static void main(String[] args) {
        Buffer buffer = new Buffer(0, true);
        Thread t1 = new Thread(new Producer(buffer));
        Thread t2 = new Thread(new Consumer(buffer));

        t1.start();
        t2.start();

        Thread.sleep(1000);
        t1.interrupt();
        t2.interrupt();
    }


    @AllArgsConstructor
    private static class Producer implements Runnable {
        private Buffer buffer;


        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                buffer.submitData(secureRandom.nextInt(10, 100));
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

        }
    }


    @AllArgsConstructor
    private static class Consumer implements Runnable {
        private Buffer buffer;

        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                int info = buffer.takeData();
                System.out.println("Consumer take: " + info);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

}
