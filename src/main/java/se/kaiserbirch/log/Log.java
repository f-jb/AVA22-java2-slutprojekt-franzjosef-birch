package se.kaiserbirch.log;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.SubmissionPublisher;

public enum Log implements Publisher<String> {
    LOG;
    private final SubmissionPublisher<String> submissionPublisher= new SubmissionPublisher<>();
    private BufferedWriter writer;

    public void write(String logMessage) {
        try {
            String messageWithTimeStamp = LocalDateTime.now() + " - " + logMessage + "\n";
            /*
            * I think this needs to be synchronized so the log-file and log-view is the same.
            * The risk would otherwise be that the message is viewed, thread paused before writing the file.
            * Another thread jumps in, shows another message and writes it before the first message is written and saved.
             */
            synchronized (this) {
                submissionPublisher.submit(messageWithTimeStamp);
                writer.write(messageWithTimeStamp);
                writer.flush();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public void openWriter() {
        openWriter("log", LocalDate.now() + ".txt");

    }

    public void openWriter(String logDirectoryName, String logFileName) {
        Path logFile = Paths.get(logFileName);
        Path logDirectory = Paths.get(logDirectoryName);
        Path finalPath = logDirectory.resolve(logFile);
        try {
            if (Files.notExists(logDirectory)) {
                Files.createDirectory(logDirectory);
            }
            if (Files.notExists(finalPath)) {
                Files.createFile(finalPath);
            }
            writer = Files.newBufferedWriter(finalPath, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public List<String> read() {
        return read("log", LocalDate.now() + ".txt");
    }

    public List<String> read(String logDirectoryName, String logFileName) {
        Path logFile = Paths.get(logFileName);
        Path logDirectory = Paths.get(logDirectoryName);
        Path finalPath = logDirectory.resolve(logFile);
        List<String> logHistory = new ArrayList<>();
        if (Files.exists(finalPath)) {
            try {
                Files.lines(finalPath).forEach(logHistory::add);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return logHistory;
    }

    @Override
    public void subscribe(Subscriber<? super String> subscriber) {
        this.submissionPublisher.subscribe(subscriber);
    }
}