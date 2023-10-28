package se.kaiserbirch.model;

import java.util.concurrent.BlockingQueue;

import static java.util.concurrent.TimeUnit.SECONDS;
import static se.kaiserbirch.model.WorkFactory.WORK_FACTORY;

public class Producer extends Worker {
    final WorkFactory workFactory = WORK_FACTORY;

    Producer(int id, BlockingQueue<Work> workQueue, int interval) {
        super(id, workQueue, interval);
    }


    @Override
    public void run() {
        while (active) {
            try {
                SECONDS.sleep(interval);
                workQueue.put(workFactory.createWorkUnit(this));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
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
