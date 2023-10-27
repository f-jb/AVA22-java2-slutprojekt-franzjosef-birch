package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kaiserbirch.model.ProducerFactory;
import se.kaiserbirch.model.Work;
import se.kaiserbirch.model.WorkFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.jupiter.api.Assertions.*;

public class WorkTest {
    @BeforeEach
    void setup(){
        WorkFactory.resetCounter();
    }

    @Test
    void createWorkWithNullProducer_throwsNullPointerException() {
        var workFactory = WorkFactory.getInstance();
        assertThrows(NullPointerException.class, () -> workFactory.createWorkUnit(null));

    }
    @Test
    void createAnUnitOfWork_workIdIncrements() {
        BlockingQueue<Work> workQueue = new LinkedBlockingQueue<>(20);
        var producerFactory = new ProducerFactory(workQueue);
        var producer = producerFactory.getWorkerWithNoInterval();
        final int expectedId = 15;
        var workFactory = WorkFactory.getInstance();
        Work work = null;
        for (int i = 0; i < expectedId + 1 ; i++) {
            work = workFactory.createWorkUnit(producer);
        }
        assertEquals(expectedId,work.getId());

    }
    @Test
    void createAnUnitOfWorkWithSpecificProducer_producerIsSetInWork() {
        BlockingQueue<Work> workQueue = new LinkedBlockingQueue<>(20);
        var producerFactory = new ProducerFactory(workQueue);
        var firstProducer = producerFactory.getWorkerWithNoInterval();
        var secondProducer = producerFactory.getWorkerWithNoInterval();
        var workFactory = WorkFactory.getInstance();
        var workZero = workFactory.createWorkUnit(firstProducer);
        var workOne = workFactory.createWorkUnit(secondProducer);
        assertEquals(workZero.getProducer(),firstProducer);
        assertEquals(workOne.getProducer(),secondProducer);
    }
}
