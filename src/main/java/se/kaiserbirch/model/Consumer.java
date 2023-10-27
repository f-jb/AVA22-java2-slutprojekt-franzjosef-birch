package se.kaiserbirch.model;

import java.time.Duration;
import java.util.concurrent.BlockingQueue;

import static java.lang.Thread.sleep;

public class Consumer extends Worker{


    Consumer(int id, BlockingQueue<Work> workQueue, int interval) {
        super(id, workQueue, interval);
    }

    public void consume() throws InterruptedException {
        workQueue.take();
    }

    @Override
    public void run() {
        try {
            sleep(Duration.ofSeconds(interval));
            workQueue.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
