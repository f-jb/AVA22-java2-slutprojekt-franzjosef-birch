package se.kaiserbirch.model;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ModelController {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<Work> workQueue = new LinkedBlockingQueue<>(20);
        ProducerFactory producerFactory = new ProducerFactory(workQueue);
        Producer producer = producerFactory.getWorkerWithFixedInterval(1);

        ConsumerFactory consumerFactory = new ConsumerFactory(workQueue);
        Consumer consumer = consumerFactory.getWorkerWithFixedInterval(2);


        producer.produce();
        producer.produce();
        producer.produce();
        producer.produce();
        consumer.consume();

        System.out.println(workQueue.size());



    }
}
