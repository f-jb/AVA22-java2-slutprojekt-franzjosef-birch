package model;

import org.junit.jupiter.api.Test;
import se.kaiserbirch.model.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ConsumerTest {
    @Test
    void consumerConsumes_aWorkObjectIsConsumed(){
        List<Work> workList = new ArrayList<>();
        ProducerFactory producerFactory = new ProducerFactory(workList);
        Producer producer = producerFactory.getWorkerWithNoInterval();
        producer.produce();

        ConsumerFactory consumerFactory = new ConsumerFactory(workList);
        Consumer consumer = consumerFactory.getWorkerWithNoInterval();
        consumer.consume();
        assertTrue(workList.isEmpty());
    }
    @Test
    void consumerConsumesMultipleTimes_multipleWorkObjectsConsumed(){
        int expectedWorkUnits = 15;
        List<Work> workList = new ArrayList<>();
        var producerFactory = new ProducerFactory(workList);
        var producer = producerFactory.getWorkerWithNoInterval();
        for (int i =0; i < expectedWorkUnits; i++) {
            producer.produce();
        }
        assertFalse(workList.isEmpty());

        ConsumerFactory consumerFactory = new ConsumerFactory(workList);
        Consumer consumer = consumerFactory.getWorkerWithNoInterval();
        for (int i =0; i < expectedWorkUnits; i++) {
            consumer.consume();
        }
        assertTrue(workList.isEmpty());

    }
    @Test
    void getConsumerWithFixedInterval_producesAConsumerWithFixedInterval(){
        List<Work> workList = new ArrayList<>();
        int interval = 3;
        ConsumerFactory consumerFactory = new ConsumerFactory(workList);
        Consumer consumer = consumerFactory.getWorkerWithFixedInterval(interval);

        assertEquals(interval,consumer.getInterval());
    }
}

