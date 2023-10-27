package model;

import org.junit.jupiter.api.Test;
import se.kaiserbirch.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.jupiter.api.Assertions.*;

public class ConsumerTest {
    @Test
    void consumerConsumes_aWorkObjectIsConsumed() throws InterruptedException {
        BlockingQueue<Work> workQueue = new LinkedBlockingQueue<>(20);
        ProducerFactory producerFactory = new ProducerFactory(workQueue);
        Producer producer = producerFactory.getWorkerWithNoInterval();
        producer.produce();

        ConsumerFactory consumerFactory = new ConsumerFactory(workQueue);
        Consumer consumer = consumerFactory.getWorkerWithNoInterval();
        consumer.consume();
        assertTrue(workQueue.isEmpty());
    }
    @Test
    void consumerConsumesMultipleTimes_multipleWorkObjectsConsumed() throws InterruptedException {
        int expectedWorkUnits = 15;
        BlockingQueue<Work> workQueue = new LinkedBlockingQueue<>(20);
        var producerFactory = new ProducerFactory(workQueue);
        var producer = producerFactory.getWorkerWithNoInterval();
        for (int i =0; i < expectedWorkUnits; i++) {
            producer.produce();
        }
        assertFalse(workQueue.isEmpty());

        ConsumerFactory consumerFactory = new ConsumerFactory(workQueue);
        Consumer consumer = consumerFactory.getWorkerWithNoInterval();
        for (int i =0; i < expectedWorkUnits; i++) {
            consumer.consume();
        }
        assertTrue(workQueue.isEmpty());

    }
    @Test
    void getConsumerWithFixedInterval_producesAConsumerWithFixedInterval(){
        BlockingQueue<Work> workQueue = new LinkedBlockingQueue<>(20);
        int interval = 3;
        ConsumerFactory consumerFactory = new ConsumerFactory(workQueue);
        Consumer consumer = consumerFactory.getWorkerWithFixedInterval(interval);

        assertEquals(interval,consumer.getInterval());
    }
}

