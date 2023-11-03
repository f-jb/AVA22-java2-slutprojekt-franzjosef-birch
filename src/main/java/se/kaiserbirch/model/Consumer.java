package se.kaiserbirch.model;

import java.util.concurrent.BlockingQueue;

import static java.util.concurrent.TimeUnit.SECONDS;

public class Consumer extends Worker {


    Consumer(int id, BlockingQueue<Work> workQueue, int interval) {
        super(id, workQueue, interval);
    }

    public void consume() throws InterruptedException {
        workQueue.take();
    }

    @Override
    public void run() {
        while (active) {
            try {
                SECONDS.sleep(interval);
                if (!workQueue.isEmpty()) {
                    workQueue.take();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public String toString() {
        return "ID: " + id + " Interval: " + interval;
    }
}
