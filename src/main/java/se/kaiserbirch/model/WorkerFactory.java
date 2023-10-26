package se.kaiserbirch.model;

import java.util.ArrayList;
import java.util.List;

public abstract class WorkerFactory {
    List<Work> workList;
    WorkerFactory(List<Work> workList){
        if(workList == null){
            throw new NullPointerException();
        }
        this.workList = workList;

    }

    abstract public Worker getWorkerWithFixedInterval(int interval);
    abstract public Worker getWorkerWithRandomInterval(int low, int high);

    public abstract Worker getWorkerWithNoInterval();
}
