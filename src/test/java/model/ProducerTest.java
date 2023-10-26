package model;

import org.junit.jupiter.api.Test;
import se.kaiserbirch.model.Producer;
import se.kaiserbirch.model.ProducerFactory;
import se.kaiserbirch.model.Work;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProducerTest {
    @Test
    void producerProducesAWorkUnit_aWorkUnitIsCreated(){
        List<Work> workList = new ArrayList<>();
        var producerFactory = new ProducerFactory(workList);
        var producer = producerFactory.getWorkerWithNoInterval();
        producer.produce();
        assertEquals(1,workList.size());
    }
    @Test
    void producerProducesMultipleWorkUnits_workUnitsAreCreated(){
        int expectedWorkUnits = 15;
        List<Work> workList = new ArrayList<>();
        var producerFactory = new ProducerFactory(workList);
        var producer = producerFactory.getWorkerWithNoInterval();
        for (int i =0; i < expectedWorkUnits; i++) {
            producer.produce();
        }
        assertEquals(expectedWorkUnits,workList.size());
    }
    @Test
    void getProducerWithFixedInterval_producesAProducerWithFixedInterval(){
        List<Work> workList = new ArrayList<>();
        int interval = 3;
        ProducerFactory producerFactory = new ProducerFactory(workList);
        Producer producer = producerFactory.getWorkerWithFixedInterval(interval);
        assertEquals(interval,producer.getInterval());
    }
}
