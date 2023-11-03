package se.kaiserbirch.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static java.util.concurrent.TimeUnit.SECONDS;
import static se.kaiserbirch.log.Log.LOG;

public class ModelController implements Serializable {
    final List<Producer> activeProducers = new ArrayList<>();
    final List<Consumer> activeConsumers = new ArrayList<>();
    final int smallestTimeUnitForRandom = 1;
    final int largestTimeUnitForRandom = 10;
    final ExecutorService executorService = Executors.newThreadPerTaskExecutor(Executors.defaultThreadFactory());
    private final int workQueueCapacity = 100;
    /*
     * LinkedBlockingQueue is ideal for our use-case. It blocks if it isn't possible
     * to perform the operation. Either .put() or .take(). Also it is optionally bounded,
     * perfect for us to monitor the progress.
     */
    final BlockingQueue<Work> workQueue = new LinkedBlockingQueue<>(workQueueCapacity);
    final ProducerFactory producerFactory = new ProducerFactory(workQueue);
    final ConsumerFactory consumerFactory = new ConsumerFactory(workQueue);
    public ModelController() {
        executorService.execute(new Warnings(2));
        executorService.execute(new AverageProduction(10));
        int amountOfConsumer = ThreadLocalRandom.current().nextInt(3, 15);
        for (int i = 0; i < amountOfConsumer; i++) {
            addANewConsumer();
        }

    }

    public int getAmountOfUnitsInWorkQueue() {
        return workQueue.size();
    }

    public void addANewProducer() {
        Producer producer = producerFactory.getWorkerWithRandomInterval(smallestTimeUnitForRandom, largestTimeUnitForRandom);
        activeProducers.add(producer);
        executorService.execute(producer);
        LOG.entry("Producer added. New total is " + activeProducers.size());
        for (Producer activeProducer : activeProducers) {
            LOG.entry(activeProducer.toString());
        }
    }

    public void removeFirstProducer() {
        if (!activeProducers.isEmpty()) {
            Producer producerToRemove = activeProducers.getFirst();
            producerToRemove.stop();
            activeProducers.remove(producerToRemove);
            LOG.entry("Producer removed. New total is " + activeProducers.size());
            for (Producer activeProducer : activeProducers) {
                LOG.entry(activeProducer.toString());
            }
        }
    }

    public void addANewConsumer() {
        Consumer consumer = consumerFactory.getWorkerWithRandomInterval(smallestTimeUnitForRandom, largestTimeUnitForRandom);
        activeConsumers.add(consumer);
        executorService.execute(consumer);
    }

    @Override
    public String toString() {
        return "ModelController{" +
                "workQueue=" + workQueue.size() +
                ", activeProducers=" + activeProducers.size() +
                ", activeConsumers=" + activeConsumers.size() +
                ", smallestTimeUnitForRandom=" + smallestTimeUnitForRandom +
                ", largestTimeUnitForRandom=" + largestTimeUnitForRandom +
                '}';
    }

    private class Warnings implements Runnable {
        boolean active = true;
        int interval;

        Warnings(int interval) {
            this.interval = interval;
        }

        @Override
        public void run() {
            while (active) {
                try {
                    if (workQueue.size() <= workQueueCapacity * 0.1) {
                        LOG.entry("Low units of work");
                    }
                    if (workQueue.size() >= workQueueCapacity * 0.9) {
                        LOG.entry("High units of work");
                    }
                    SECONDS.sleep(interval);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

            }

        }
    }

    private class AverageProduction implements Runnable {
        int interval;
        boolean active = true;

        AverageProduction(int interval) {
            this.interval = interval;
        }

        @Override
        public void run() {
            while (active) {
                try {
                    float averageProduction = 0;
                    for (Producer producer : activeProducers) {
                        averageProduction += (float) 1 / producer.getInterval();
                    }
                    LOG.entry("Average production is " + averageProduction);
                    SECONDS.sleep(interval);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}