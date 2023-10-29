package se.kaiserbirch.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import static se.kaiserbirch.model.log.Log.LOG;

public class ModelController implements Serializable {
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
    final int smallestTimeUnitForRandom = 1;
    final int largestTimeUnitForRandom = 10;
    //    ExecutorService producerExecutorService = Executors.newThreadPerTaskExecutor()
    final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    public ModelController(){
        LOG.openWriter();
    }

    public void addANewProducer() {
        Producer producer = producerFactory.getWorkerWithRandomInterval(smallestTimeUnitForRandom, largestTimeUnitForRandom);
        activeProducers.add(producer);
        executorService.execute(producer);
        LOG.log("Producer added. New total is " + activeProducers.size());
        for (Producer activeProducer: activeProducers) {
            LOG.log(activeProducer.toString());
        }
    }

    public void removeFirstProducer() {
        Producer producerToRemove = activeProducers.getFirst();
        producerToRemove.stop();
        activeProducers.remove(producerToRemove);
        LOG.log("Producer removed. New total is " + activeProducers.size());
        for (Producer activeProducer: activeProducers) {
            LOG.log(activeProducer.toString());
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
}