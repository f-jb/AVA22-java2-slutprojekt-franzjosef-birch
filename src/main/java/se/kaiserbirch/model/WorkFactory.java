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
    public Work getWorkUnit(String producer) throws NullProducerException {
        if(producer == null){
            throw new NullProducerException();
        }
        return new WorkImpl(counter++,producer);

    }
    public static class NullProducerException extends Exception{
        public NullProducerException(){
            super();
        }

    }
}
