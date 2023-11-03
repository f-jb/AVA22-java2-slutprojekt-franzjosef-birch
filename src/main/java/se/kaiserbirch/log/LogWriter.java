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

public enum LogWriter {
    INSTANCE;
    private BufferedWriter writer;
    private Path pathToLogFile = Paths.get("log", LocalDate.now() + ".txt");

    public void write(String logMessage) {
        if (writer == null) {
            writer = openWriter();
        }
        try {
            String messageWithTimeStamp = LocalDateTime.now() + " - " + logMessage + "\n";
            writer.write(messageWithTimeStamp);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private BufferedWriter openWriter() {
        assert pathToLogFile != null;
        createLogFile(pathToLogFile);
        try {
            return Files.newBufferedWriter(pathToLogFile, StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public List<String> read() {
        List<String> logHistory = new ArrayList<>();
        if (Files.exists(pathToLogFile)) {
            try {
                Files.lines(pathToLogFile).forEach(logHistory::add);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return logHistory;
    }

    private void createLogFile(Path pathToLogFile) {
        try {
            if (Files.notExists(pathToLogFile.getParent())) {
                Files.createDirectories(pathToLogFile.getParent());
            }
            if (Files.notExists(pathToLogFile)) {
                Files.createFile(pathToLogFile);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void setPathToLogFile(Path pathToLogFile) {
        this.pathToLogFile = pathToLogFile;
    }
}