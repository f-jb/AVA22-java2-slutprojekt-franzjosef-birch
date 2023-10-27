package se.kaiserbirch.model;

import java.time.Duration;
import java.util.concurrent.BlockingQueue;

import static java.lang.Thread.sleep;

public class Producer extends Worker {
    WorkFactory workFactory = WorkFactory.getInstance();

    Producer(int id, BlockingQueue<Work> workQueue, int interval) {
        super(id, workQueue, interval);
    }


    @Override
    public void run() {
        try {
            sleep(Duration.ofSeconds(interval));
            workQueue.put(workFactory.createWorkUnit(this));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void produce() {
        workQueue.add(workFactory.createWorkUnit(this));

    }

    @Override
    public String toString() {
        return "Producer " + id + " with interval " + interval;
    }
}
