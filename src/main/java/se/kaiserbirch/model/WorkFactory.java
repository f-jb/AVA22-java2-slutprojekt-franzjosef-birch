package se.kaiserbirch.model;

public class WorkFactory {
    static volatile WorkFactory workFactory;
    private int counter = 0;
    private WorkFactory(){};
    public static WorkFactory getInstance() {
        if(workFactory == null){
            synchronized (WorkFactory.class) {
                if(workFactory == null) {
                    workFactory = new WorkFactory();
                }
            }
        }
        return workFactory;
    }
    public Work getWorkUnit(String producer) {
        if(producer == null){
            throw new NullPointerException();
        }
        return new WorkImpl(counter++,producer);

    }

}
