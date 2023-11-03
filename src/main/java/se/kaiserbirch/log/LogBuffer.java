package se.kaiserbirch.log;

import java.util.ArrayList;
import java.util.List;

public enum LogBuffer {
    INSTANCE;
    private final List<String> logList = new ArrayList<>();


    public List<String> getLogList() {
        return logList;
    }

    public void addToLogList(String entry) {
        this.logList.addFirst(entry);
    }
}
