package se.KTH.seminar3.integration;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileLogger {

    private PrintWriter logStream;

    /**
     * Initializes the logger and writes an initial message to the log file.
     */
    public FileLogger() {
        try {
            this.logStream = new PrintWriter(new FileWriter("log.txt", true), true);

            this.logStream.println("This is a log message");
        } catch (IOException e) {
            System.out.println("An error occurred while trying to write to the file");
        }
    }

    /**
     * Logs a message to the file.
     *
     * @param message The message to be logged.
     */
    public void log(String message) {
        this.logStream.println(message);
        this.logStream.flush();
    }

}
