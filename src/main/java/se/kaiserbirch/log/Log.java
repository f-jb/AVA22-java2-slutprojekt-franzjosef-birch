package se.kaiserbirch.log;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

public enum Log implements Flow.Publisher<String> {
    LOG;
    private final SubmissionPublisher<String> submissionPublisher = new SubmissionPublisher<>();
    private final LogBuffer logBuffer = LogBuffer.INSTANCE;
    private final LogWriter logWriter = LogWriter.INSTANCE;

    public void entry(String entry) {
        String entryWithDatestamp = addTimestamp(entry);
        submissionPublisher.submit(entryWithDatestamp);
        System.out.print(entryWithDatestamp);
        logBuffer.addToLogList(entryWithDatestamp);
        logWriter.write(entryWithDatestamp);
    }

    private String addTimestamp(String entry) {
        return LocalDateTime.now() + " - " + entry + '\n';
    }

    public List<String> getLogList() {
        return logBuffer.getLogList();
    }

    @Override
    public void subscribe(Flow.Subscriber<? super String> subscriber) {
        this.submissionPublisher.subscribe(subscriber);
    }
}
