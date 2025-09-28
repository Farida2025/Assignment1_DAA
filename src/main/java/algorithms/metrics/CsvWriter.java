package algorithms.metrics;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CsvWriter implements AutoCloseable {
    private final PrintWriter writer;

    public CsvWriter(String filename) throws IOException {

        this.writer = new PrintWriter(new FileWriter(filename, false));
    }

    public void writeLine(String line) throws IOException {
        writer.println(line);
    }


    @Override
    public void close() throws IOException {
        writer.close();
    }
}