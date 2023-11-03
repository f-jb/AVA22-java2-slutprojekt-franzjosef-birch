package se.kaiserbirch.controller;

public class UIState {
    private final String logEntry;
    private final Updated updated;
    private final int amountOfWorkUnitsInQueue;
    private final boolean noActiveProducers;

    private UIState(Builder builder) {
        this.amountOfWorkUnitsInQueue = builder.amountOfWorkUnitsInQueue;
        this.noActiveProducers = builder.noActiveProducers;
        this.logEntry = builder.logEntry;
        this.updated = builder.updated;
    }

    public String getLogEntry() {
        return logEntry;
    }

    public Updated getUpdated() {
        return updated;
    }

    public int getAmountOfWorkUnitsInQueue() {
        return amountOfWorkUnitsInQueue;
    }

    public boolean isNoActiveProducers() {
        return noActiveProducers;
    }

    public enum Updated {
        LOG_ENTRY,
        WORK_UNITS
    }

    public static class Builder {
        int amountOfWorkUnitsInQueue;
        boolean noActiveProducers;
        String logEntry;
        Updated updated;

        public Builder setUpdate(Updated updated) {
            this.updated = updated;
            return this;
        }

        public Builder setLogEntry(String logEntry) {
            this.logEntry = logEntry;
            return this;
        }

        public Builder setAmountOfWorkUnitsInQueue(int amountOfWorkUnitsInQueue) {
            this.amountOfWorkUnitsInQueue = amountOfWorkUnitsInQueue;
            return this;
        }

        public Builder setNoActiveProducers(boolean noActiveProducers) {
            this.noActiveProducers = noActiveProducers;
            return this;
        }

        public UIState build() {
            return new UIState(this);
        }
    }

}
