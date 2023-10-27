package se.kaiserbirch.model;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ProducerFactory extends WorkerFactory {
    int createdProducers = 0;

    public ProducerFactory(List<Work> workList) {
        super(workList);
    }


    @Override
    public Producer getWorkerWithFixedInterval(int interval) {
        return new Producer(createdProducers++, workList, interval);
    }

    @Override
    public Producer getWorkerWithRandomInterval(int low, int high) {
        int interval = ThreadLocalRandom.current().nextInt(low, high);
        return new Producer(createdProducers++, workList, interval);
    }

    @Override
    public Producer getWorkerWithNoInterval() {
        return new Producer(createdProducers++, workList, 0);
    }
}
