package algorithms.metrics;


public class MetricsTracker {
    private long comparisons = 0;
    private long allocations = 0;
    private int currentDepth = 0;
    private int maxDepth = 0;



    public void incrementComparisons() {
        comparisons++;
    }


    public void addAllocations(long size) {
        allocations += size;
    }


    public void enterRecursion() {
        currentDepth++;
        if (currentDepth > maxDepth) {
            maxDepth = currentDepth;
        }
    }


    public void exitRecursion() {
        currentDepth--;
    }


    public MetricsSnapshot getSnapshot(int n, long timeNs) {
        return new MetricsSnapshot(n, timeNs, comparisons, allocations, maxDepth);
    }

    public void reset() {
        this.comparisons = 0;
        this.allocations = 0;
        this.currentDepth = 0;
        this.maxDepth = 0;
    }


    public static class MetricsSnapshot {
        public final int N;
        public final long TimeNs;
        public final long Comparisons;
        public final long Allocations;
        public final int MaxDepth;

        public MetricsSnapshot(int n, long timeNs, long comparisons, long allocations, int maxDepth) {
            this.N = n;
            this.TimeNs = timeNs;
            this.Comparisons = comparisons;
            this.Allocations = allocations;
            this.MaxDepth = maxDepth;
        }


        @Override
        public String toString() {
            return String.format("%d,%d,%d,%d,%d", N, TimeNs, Comparisons, Allocations, MaxDepth);
        }
    }
}