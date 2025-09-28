package algorithms.experiment;

import algorithms.metrics.MetricsTracker;
import algorithms.metrics.CsvWriter;
import algorithms.sorting.MergeSort;
import algorithms.sorting.QuickSort;
import algorithms.selection.DeterministicSelect;
import algorithms.geometry.ClosestPair;
import algorithms.geometry.Point;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class ExperimentRunner {

    private static final Random RANDOM = new Random();
    private static final int TRIALS = 5;
    private static final String FILENAME = "results.csv";


    public static void main(String[] args) throws IOException {
        new ExperimentRunner().runAllExperiments();
    }

    private void runAllExperiments() throws IOException {
        List<Integer> sizes = List.of(1000, 2000, 4000, 8000, 16000, 32000, 64000, 102400);

        System.out.println("Starting experiments. Trials per size: " + TRIALS);

        try (CsvWriter writer = new CsvWriter(FILENAME)) {
            writer.writeLine("Algorithm,N,Trial,Comparisons,MaxDepth,Time_ns");

            for (int N : sizes) {
                System.out.printf("--- N = %d ---\n", N);

                runSortExperiments(N, writer);
                runSelectExperiments(N, writer);
                runClosestPairExperiments(N, writer);
            }
        }
        System.out.println("\n--- Experiments finished. Results saved to " + FILENAME + " ---");
    }

    private void runSortExperiments(int N, CsvWriter writer) throws IOException {
        for (int t = 1; t <= TRIALS; t++) {
            final int[] baseArray = RANDOM.ints(N, 0, N * 10).toArray();


            runAlgorithm("MergeSort", N, t, writer, (tracker) -> MergeSort.sort(baseArray.clone(), tracker));


            runAlgorithm("QuickSort", N, t, writer, (tracker) -> QuickSort.sort(baseArray.clone(), tracker));
        }
    }

    private void runSelectExperiments(int N, CsvWriter writer) throws IOException {
        final int k = N / 2;
        for (int t = 1; t <= TRIALS; t++) {
            final int[] baseArray = RANDOM.ints(N, 0, N * 10).toArray();

            runAlgorithm("DeterministicSelect", N, t, writer, (tracker) -> DeterministicSelect.select(baseArray.clone(), k, tracker));
        }
    }

    private void runClosestPairExperiments(int N, CsvWriter writer) throws IOException {
        final Point[] basePoints = generateRandomPoints(N);

        for (int t = 1; t <= TRIALS; t++) {

            runAlgorithm("ClosestPair", N, t, writer, (tracker) -> ClosestPair.findClosestPair(basePoints.clone(), tracker));
        }
    }



    private void runAlgorithm(String name, int N, int trial, CsvWriter writer, RunnableWithMetrics runnable) throws IOException {

        MetricsTracker tracker = new MetricsTracker();

        long startTime = System.nanoTime();
        runnable.run(tracker);
        long endTime = System.nanoTime();
        long time = endTime - startTime;

        long comparisons = name.equals("ClosestPair") ? 0 : tracker.getSnapshot(N, 0).Comparisons;
        int maxDepth = tracker.getSnapshot(N, 0).MaxDepth;

        writer.writeLine(String.format("%s,%d,%d,%d,%d,%d",
                name, N, trial, comparisons, maxDepth, time));
    }


    @FunctionalInterface
    private interface RunnableWithMetrics {
        void run(MetricsTracker tracker);
    }

    private Point[] generateRandomPoints(int N) {
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            points[i] = new Point(RANDOM.nextDouble() * N, RANDOM.nextDouble() * N);
        }
        return points;
    }
}