package se.kaiserbirch.model;


public class Work {
    private final int id;
    private final Producer producer;

    Work(int id, Producer producer) {
        this.id = id;
        this.producer = producer;
    }

    public int getId() {
        return id;
    }

    public Producer getProducer() {
        return producer;
    }

    /**
     * Returns in the form of "Work $id produced by $producer"
     * Subject to change.
     */
    @Override
    public String toString() {
        return "Work " + id + " produced by producer " + producer.getId();
    }
}
