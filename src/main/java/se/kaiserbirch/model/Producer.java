package se.kaiserbirch.model;

import java.time.Duration;
import java.util.List;

import static java.lang.Thread.sleep;

public class Producer extends Worker {
    WorkFactory workFactory = WorkFactory.getInstance();

    Producer(int id, List<Work> workList, int interval) {
        super(id, workList, interval);
    }


    @Override
    public void run() {
        try {
            sleep(Duration.ofSeconds(interval));
            workList.add(workFactory.getWorkUnit(this));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void produce() {
        workList.add(workFactory.getWorkUnit(this));

    }

    @Override
    public String toString() {
        return "Producer " + id + " with interval " + interval;
    }
}
