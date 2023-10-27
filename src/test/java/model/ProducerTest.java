package model;

import org.junit.jupiter.api.Test;
import se.kaiserbirch.model.Producer;
import se.kaiserbirch.model.ProducerFactory;
import se.kaiserbirch.model.Work;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProducerTest {
    @Test
    void producerProducesAWorkUnit_aWorkUnitIsCreated(){
        BlockingQueue<Work> workQueue = new LinkedBlockingQueue<>(20);
        var producerFactory = new ProducerFactory(workQueue);
        var producer = producerFactory.getWorkerWithNoInterval();
        producer.produce();
        assertEquals(1,workQueue.size());
    }
    @Test
    void producerProducesMultipleWorkUnits_workUnitsAreCreated(){
        BlockingQueue<Work> workQueue = new LinkedBlockingQueue<>(20);
        int expectedWorkUnits = 15;
        var producerFactory = new ProducerFactory(workQueue);
        var producer = producerFactory.getWorkerWithNoInterval();
        for (int i =0; i < expectedWorkUnits; i++) {
            producer.produce();
        }
        assertEquals(expectedWorkUnits,workQueue.size());
    }
    @Test
    void getProducerWithFixedInterval_producesAProducerWithFixedInterval(){
        BlockingQueue<Work> workQueue = new LinkedBlockingQueue<>(20);
        int interval = 3;
        ProducerFactory producerFactory = new ProducerFactory(workQueue);
        Producer producer = producerFactory.getWorkerWithFixedInterval(interval);
        assertEquals(interval,producer.getInterval());
    }
}
