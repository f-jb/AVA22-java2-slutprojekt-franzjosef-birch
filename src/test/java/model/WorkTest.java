package model;

import org.junit.jupiter.api.Test;
import se.kaiserbirch.model.Work;
import se.kaiserbirch.model.WorkFactory;

import static org.junit.jupiter.api.Assertions.*;

public class WorkTest {
    @Test
    void createWorkWithNullProducer_throwsNullProducer() {
        String producer = null;
        var workFactory = WorkFactory.getInstance();
        Exception exception = assertThrows(NullPointerException.class, () -> workFactory.getWorkUnit(null));

    }
    @Test
    void createAnUnitOfWork_workIdIncrements() {
        String producer = "Sally";
        int expectedId = 15;
        var workFactory = WorkFactory.getInstance();
        Work work = workFactory.getWorkUnit(producer);
        for (int i = 0; i < expectedId ; i++) {
            work = workFactory.getWorkUnit(producer);
        }
        assertEquals(work.getId(),expectedId);

    }
    @Test
    void setProducerOfWork() {
        String producer = "Sally";
        var workFactory = WorkFactory.getInstance();
        var workZero = workFactory.getWorkUnit(producer);
        assertEquals(workZero.getProducer(),producer);
    }
}
