package model.log;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static se.kaiserbirch.log.Log.LOG;

public class LogTest {
    @Test
    void creatingLogFileAndDirIfTheyDoNotExist_logFileAndDirIsCreated() throws IOException {
        Path logDirectory = Paths.get("logTestDir");
        Path logFile = Paths.get("logTest.txt");
        Path finalPath = logDirectory.resolve(logFile);
        assertTrue(Files.notExists(finalPath));
        LOG.openWriter(logDirectory.toString(), logFile.toString());
        assertTrue(Files.exists(finalPath));
        Files.deleteIfExists(finalPath);
        Files.deleteIfExists(logDirectory);
    }

    @Test
    void loadLogFile_logFileIsLoaded() throws IOException {
        String testDir = "logTestDir";
        String testFile = "logTest.txt";
        LOG.openWriter(testDir, testFile);
        LOG.write("Test Message");
        List<String> messageList = LOG.read(testDir, testFile);
        System.out.println(messageList.size());
        Files.deleteIfExists(Paths.get(testDir, testFile));
        Files.deleteIfExists(Paths.get(testDir));
    }
}
