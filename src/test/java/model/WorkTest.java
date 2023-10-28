package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kaiserbirch.model.ProducerFactory;
import se.kaiserbirch.model.Work;
import se.kaiserbirch.model.WorkFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.jupiter.api.Assertions.*;
import static se.kaiserbirch.model.WorkFactory.WORK_FACTORY;

public class WorkTest {
    @BeforeEach
    void setup(){
        WorkFactory.resetCounter();
    }

    @Test
    void createWorkWithNullProducer_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> WORK_FACTORY.createWorkUnit(null));

    }
    @Test
    void createAnUnitOfWork_workIdIncrements() {
        BlockingQueue<Work> workQueue = new LinkedBlockingQueue<>(20);
        var producerFactory = new ProducerFactory(workQueue);
        var producer = producerFactory.getWorkerWithNoInterval();
        final int expectedId = 15;
        Work work = null;
        for (int i = 0; i < expectedId + 1 ; i++) {
            work = WORK_FACTORY.createWorkUnit(producer);
        }
        assertEquals(expectedId,work.getId());

    }
    @Test
    void createAnUnitOfWorkWithSpecificProducer_producerIsSetInWork() {
        BlockingQueue<Work> workQueue = new LinkedBlockingQueue<>(20);
        var producerFactory = new ProducerFactory(workQueue);
        var firstProducer = producerFactory.getWorkerWithNoInterval();
        var secondProducer = producerFactory.getWorkerWithNoInterval();
        var workZero = WORK_FACTORY.createWorkUnit(firstProducer);
        var workOne = WORK_FACTORY.createWorkUnit(secondProducer);
        assertEquals(workZero.getProducer(),firstProducer);
        assertEquals(workOne.getProducer(),secondProducer);
    }
}
