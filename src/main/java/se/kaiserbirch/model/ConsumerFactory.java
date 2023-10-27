package se.kaiserbirch.model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

public class ConsumerFactory extends WorkerFactory{
    int createdConsumer = 0;

    public ConsumerFactory(BlockingQueue<Work> workQueue) {
        super(workQueue);
    }


    @Override
    public Consumer getWorkerWithFixedInterval(int interval) {
        return new Consumer(createdConsumer++, workQueue,interval);
    }

    @Override
    public Consumer getWorkerWithRandomInterval(int low, int high) {
        int interval = ThreadLocalRandom.current().nextInt(low, high);
        return new Consumer(createdConsumer++, workQueue, interval);
    }

    public Consumer getWorkerWithNoInterval() {
        return new Consumer(createdConsumer++, workQueue, 0);
    }
}
