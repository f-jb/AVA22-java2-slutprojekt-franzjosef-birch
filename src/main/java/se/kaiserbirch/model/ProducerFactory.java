package se.kaiserbirch.model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

public class ProducerFactory extends WorkerFactory {
    int createdProducers = 0;

    public ProducerFactory(BlockingQueue<Work> workQueue) {
        super(workQueue);
    }


    @Override
    public Producer getWorkerWithFixedInterval(int interval) {
        return new Producer(createdProducers++, workQueue, interval);
    }

    @Override
    public Producer getWorkerWithRandomInterval(int low, int high) {
        int interval = ThreadLocalRandom.current().nextInt(low, high);
        return new Producer(createdProducers++, workQueue, interval);
    }

    @Override
    public Producer getWorkerWithNoInterval() {
        return new Producer(createdProducers++, workQueue, 0);
    }
}
