package se.kaiserbirch.model;

import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import static java.lang.Thread.sleep;
import static se.kaiserbirch.log.Log.LOG;

public class ModelController implements Serializable {
    /*
     * LinkedBlockingQueue is ideal for our use-case. It blocks if it isn't possible
     * to perform the operation. Either .put() or .take(). Also it is optionally bounded,
     * perfect for us to monitor the progress.
     *
     */
    private final int workQueueCapacity = 20;
    final BlockingQueue<Work> workQueue = new LinkedBlockingQueue<>(workQueueCapacity);
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
        executorService.execute(new Statistics(1));
    }

    public void addANewProducer() {
        Producer producer = producerFactory.getWorkerWithRandomInterval(smallestTimeUnitForRandom, largestTimeUnitForRandom);
        activeProducers.add(producer);
        executorService.execute(producer);
        LOG.write("Producer added. New total is " + activeProducers.size());
        for (Producer activeProducer: activeProducers) {
            LOG.write(activeProducer.toString());
        }
    }

    public void removeFirstProducer() {
        Producer producerToRemove = activeProducers.getFirst();
        producerToRemove.stop();
        activeProducers.remove(producerToRemove);
        LOG.write("Producer removed. New total is " + activeProducers.size());
        for (Producer activeProducer: activeProducers) {
            LOG.write(activeProducer.toString());
        }
    }

    public void addANewConsumer() {
        Consumer consumer = consumerFactory.getWorkerWithRandomInterval(smallestTimeUnitForRandom, largestTimeUnitForRandom);
        activeConsumers.add(consumer);
        executorService.execute(consumer);
    }
private class Statistics implements Runnable{
        boolean active = true;
        int interval;
        Statistics(int interval){
            this.interval = interval;
        }


    @Override
    public void run() {
            /*
                o Genomsnittligt antal enheter (bör sparas var 10:e sekund)

             */
        while (active) {
            try {
                if (workQueue.size() <= workQueueCapacity * 0.1) {
                    LOG.write("Low units of work");
                }
                if (workQueue.size() >= workQueueCapacity * 0.9) {
                    LOG.write("High units of work");
                }
                sleep(Duration.ofSeconds(interval));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }

    }
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