package se.kaiserbirch.model;

import java.util.concurrent.BlockingQueue;

public abstract class Worker implements Runnable {
    final int id;
    final int interval;
    BlockingQueue<Work> workQueue;
    Worker(int id, BlockingQueue<Work> workQueue, int interval) {
        this.id = id;
        this.workQueue = workQueue;
        this.interval = interval;
    }

    public int getId() {
        return id;
    }

    public int getInterval() {
        return interval;
    }


}
