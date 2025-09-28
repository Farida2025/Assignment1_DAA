package algorithms.metrics;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CsvWriter {

    private static final String CSV_HEADER =
            "Algorithm,N,Time_ns,Comparisons,Allocations,MaxDepth";

    public static void writeResults(String algorithmName, List<MetricsTracker.MetricsSnapshot> results) {
        String filename = algorithmName.toLowerCase().replace(" ", "_") + "_results.csv";


        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println(CSV_HEADER);


            for (MetricsTracker.MetricsSnapshot snapshot : results) {
                writer.printf("%s,%s%n", algorithmName, snapshot.toString());
            }
            System.out.println("Results for  " + algorithmName + " сохранены в " + filename);
        } catch (IOException e) {
            System.err.println("Error writing to CSV" + e.getMessage());
            e.printStackTrace();
        }
    }
}