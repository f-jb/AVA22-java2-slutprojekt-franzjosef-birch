package se.kaiserbirch.model;

import java.util.concurrent.BlockingQueue;

public abstract class WorkerFactory {
    BlockingQueue<Work> workQueue;

    WorkerFactory(BlockingQueue<Work> workQueue) {
        if (workQueue == null) {
            throw new NullPointerException();
        }
        this.workQueue = workQueue;

    }

    abstract public Worker getWorkerWithFixedInterval(int interval);

    abstract public Worker getWorkerWithRandomInterval(int low, int high);

    public abstract Worker getWorkerWithNoInterval();
}
