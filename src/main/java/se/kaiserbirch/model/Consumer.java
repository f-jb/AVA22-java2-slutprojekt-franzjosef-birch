package se.kaiserbirch.model;

import java.util.List;

public class Consumer extends Worker{
    Consumer(int id, List<Work> workList, int interval) {
        super(id, workList, interval);
    }

    public void consume() {
        workList.removeFirst();
    }

    @Override
    public void run() {

    }
}
