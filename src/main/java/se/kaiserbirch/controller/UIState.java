package se.kaiserbirch.controller;

public class UIState {

    public int getAmountOfWorkUnitsInQueue() {
        return amountOfWorkUnitsInQueue;
    }

    public boolean isNoActiveProducers() {
        return noActiveProducers;
    }

    private final int amountOfWorkUnitsInQueue;
    private final boolean noActiveProducers;
    private UIState(Builder builder) {
        this.amountOfWorkUnitsInQueue = builder.amountOfWorkUnitsInQueue;
        this.noActiveProducers = builder.noActiveProducers;
    }
    public static class Builder{
        public Builder setAmountOfWorkUnitsInQueue(int amountOfWorkUnitsInQueue) {
            this.amountOfWorkUnitsInQueue = amountOfWorkUnitsInQueue;
            return this;
        }

        public Builder setNoActiveProducers(boolean noActiveProducers) {
            this.noActiveProducers = noActiveProducers;
            return this;
        }
        public UIState build(){
            return new UIState(this);
        }

        int amountOfWorkUnitsInQueue;
        boolean noActiveProducers;
    }

}
