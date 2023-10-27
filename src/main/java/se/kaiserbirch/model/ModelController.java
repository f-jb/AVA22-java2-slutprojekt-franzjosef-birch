package se.kaiserbirch.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class ModelController {
    /*
     * LinkedBlockingQueue is ideal for our use-case. It blocks if it isn't possible
     * to perform the operation. Either .put() or .take(). Also it is optionally bounded,
     * perfect for us to monitor the progress.
     *
     */
    final BlockingQueue<Work> workQueue = new LinkedBlockingQueue<>(20);
    final ProducerFactory producerFactory = new ProducerFactory(workQueue);
    final List<Producer> activeProducers = new ArrayList<>();
    final ConsumerFactory consumerFactory = new ConsumerFactory(workQueue);
    final List<Consumer> activeConsumers = new ArrayList<>();
    final int smallestTimeUnitForRandom = 5;
    final int largestTimeUnitForRandom = 15;
    //    ExecutorService producerExecutorService = Executors.newThreadPerTaskExecutor()
    final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public void addANewProducer() {
        Producer producer = producerFactory.getWorkerWithRandomInterval(smallestTimeUnitForRandom, largestTimeUnitForRandom);
        activeProducers.add(producer);
        executorService.execute(producer);
    }

    public void removeFirstProducer() {
        Producer producerToRemove = activeProducers.getFirst();
        producerToRemove.stop();
        activeProducers.remove(producerToRemove);
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
}