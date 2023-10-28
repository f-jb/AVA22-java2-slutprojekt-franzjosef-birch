package se.kaiserbirch.model;

public enum WorkFactory {
    WORK_FACTORY;
    private static int createdWorkUnits = 0;

    public static void resetCounter() {
        createdWorkUnits = 0;
    }


    public Work createWorkUnit(Producer producer) {
        if (producer == null) {
            throw new NullPointerException();
        }
        return new Work(createdWorkUnits++, producer);
    }
}