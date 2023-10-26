package se.kaiserbirch.model;

public abstract class Work {
    private final int id;
    private final String producer;
    Work(int id, String producer){
        this.id = id;
        this.producer = producer;
    }

    public int getId() {
        return id;
    }
    public String getProducer() {
        return producer;
    }
}
