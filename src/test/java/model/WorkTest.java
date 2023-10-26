package model;

import org.junit.jupiter.api.Test;
import se.kaiserbirch.model.Work;
import se.kaiserbirch.model.WorkFactory;
import se.kaiserbirch.model.WorkFactory.NullProducerException;

import static org.junit.jupiter.api.Assertions.*;

public class WorkTest {
    @Test
    void throwsWhenNullProducer() {
        String producer = null;
        var workFactory = WorkFactory.getInstance();
        Exception exception = assertThrows(NullProducerException.class, () -> workFactory.getWorkUnit(null));

    }
    @Test
    void incrementsWorkId() throws NullProducerException {
        String producer = "Sally";
        var workFactory = WorkFactory.getInstance();
        Work workZero;
        Work workOne;
        try {
             workZero = workFactory.getWorkUnit(producer);
             workOne = workFactory.getWorkUnit(producer);
            assertEquals(workZero.getId(),0);
            assertEquals(workOne.getId(),1);
        } catch (NullProducerException ignored){

        }
    }
    @Test
    void setProducerOfWork() throws NullProducerException {
        String producer = "Sally";
        var workFactory = WorkFactory.getInstance();
        var workZero = workFactory.getWorkUnit(producer);
        assertEquals(workZero.getProducer(),producer);
    }



}
