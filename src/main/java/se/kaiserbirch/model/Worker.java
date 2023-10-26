package se.kaiserbirch.model;

import java.util.List;

public abstract class Worker implements Runnable {
    public int getId() {
        return id;
    }

    public int getInterval() {
        return interval;
    }

    final int id;
    final int interval;
    List<Work> workList;
    Worker(int id, List<Work> workList, int interval){this.id = id;this.workList = workList;this.interval = interval;}


}
