package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kaiserbirch.model.ProducerFactory;
import se.kaiserbirch.model.Work;
import se.kaiserbirch.model.WorkFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WorkTest {
    @BeforeEach
    void setup(){
        WorkFactory.resetCounter();


    }

    @Test
    void createWorkWithNullProducer_throwsNullPointerException() {
        var workFactory = WorkFactory.getInstance();
        assertThrows(NullPointerException.class, () -> workFactory.getWorkUnit(null));

    }
    @Test
    void createAnUnitOfWork_workIdIncrements() {
        List<Work> workList = new ArrayList<>();
        var producerFactory = new ProducerFactory(workList);
        var producer = producerFactory.getWorkerWithNoInterval();
        final int expectedId = 15;
        var workFactory = WorkFactory.getInstance();
        Work work = null;
        for (int i = 0; i < expectedId + 1 ; i++) {
            work = workFactory.getWorkUnit(producer);
        }
        assertEquals(expectedId,work.getId());

    }
    @Test
    void createAnUnitOfWorkWithSpecificProducer_producerIsSetInWork() {
        List<Work> workList = new ArrayList<>();
        var producerFactory = new ProducerFactory(workList);
        var firstProducer = producerFactory.getWorkerWithNoInterval();
        var secondProducer = producerFactory.getWorkerWithNoInterval();
        var workFactory = WorkFactory.getInstance();
        var workZero = workFactory.getWorkUnit(firstProducer);
        var workOne = workFactory.getWorkUnit(secondProducer);
        assertEquals(workZero.getProducer(),firstProducer);
        assertEquals(workOne.getProducer(),secondProducer);
    }
}
