package se.kaiserbirch.model;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ConsumerFactory extends WorkerFactory{
    int createdConsumer = 0;

    public ConsumerFactory(List<Work> workList) {
        super(workList);
    }

    @Override
    public Consumer getWorkerWithFixedInterval(int interval) {
        return new Consumer(createdConsumer++,workList,interval);
    }

    @Override
    public Consumer getWorkerWithRandomInterval(int low, int high) {
        int interval = ThreadLocalRandom.current().nextInt(low, high);
        return new Consumer(createdConsumer++, workList, interval);
    }

    public Consumer getWorkerWithNoInterval() {
        return new Consumer(createdConsumer++, workList, 0);
    }
}
