package se.kaiserbirch.model.log;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public enum Log {
    LOG;
    private BufferedWriter writer;

    public void log(String logMessage) {
        try {
            writer.write(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS) + " - " + logMessage + "\n");
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    public void openWriter() {
        openWriter("log", String.valueOf(LocalDate.now()));

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


    public List<String> openReader() {
        return openReader("log", LocalDate.now() + ".txt");
    }

    public List<String> openReader(String logDirectoryName, String logFileName) {
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
}