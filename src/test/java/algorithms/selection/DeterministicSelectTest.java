package algorithms.selection; // <-- ПАКЕТ ИСПРАВЛЕН

import algorithms.metrics.MetricsTracker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class DeterministicSelectTest {

    private MetricsTracker tracker;
    private static final int N_SMALL = 100;
    private static final int N_HUGE = 10000;

    @BeforeEach
    void setUp() {
        tracker = new MetricsTracker();
    }

    private void assertSelectValue(int[] array, int k) {

        int[] sortedCopy = array.clone();
        Arrays.sort(sortedCopy);
        int expectedValue = sortedCopy[k];


        int actualValue = DeterministicSelect.select(array.clone(), k, tracker);

        assertEquals(expectedValue, actualValue, "Deterministic Select returned wrong k-th value.");
    }

    @Test
    void testFindMinK0() {
        int[] arr = {5, 2, 8, 1, 9, 3};
        assertSelectValue(arr, 0);
    }

    @Test
    void testFindMedianKCentral() {
        int[] arr = {5, 2, 8, 1, 9, 3, 7};
        assertSelectValue(arr, arr.length / 2);
    }

    @Test
    void testRandomArrayKRandom() {
        Random rand = new Random(0);
        int N = N_SMALL;
        int[] arr = rand.ints(N, 0, 1000).toArray();
        int k = rand.nextInt(N);
        assertSelectValue(arr, k);
    }



    @Test
    void testComplexityO_N_Linear() {
        Random rand = new Random(0);
        int N = N_HUGE;
        int[] arr = rand.ints(N, 0, 10000).toArray();
        int k = N / 2;

        DeterministicSelect.select(arr, k, tracker);


        long actualComparisons = tracker.getSnapshot(N, 0).Comparisons;


        double maxExpectedComparisons = 10.0 * N;

        assertTrue(actualComparisons < maxExpectedComparisons,
                "Too many comparisons! Expected O(N) complexity (less than 10*N), got: " + actualComparisons);


        assertTrue(actualComparisons > 2.0 * N,
                "Too few comparisons! Expected minimum 2*N, got: " + actualComparisons);
    }

    @Test
    void testRecursionDepthO_LogN() {
        int N = N_HUGE; // 10000
        Random rand = new Random(0);
        int[] arr = rand.ints(N, 0, 10000).toArray();
        int k = N / 2;

        DeterministicSelect.select(arr, k, tracker);


        int expectedMaxDepth = (int) Math.ceil(Math.log(N) / Math.log(2));

        int actualMaxDepth = tracker.getSnapshot(N, 0).MaxDepth;


        assertTrue(actualMaxDepth < N / 2, "Depth is linear (O(N)), expected O(log N). Got: " + actualMaxDepth);


        assertTrue(actualMaxDepth <= expectedMaxDepth + 10,
                "Incorrect recursion depth. Expected O(log N) approx. " + expectedMaxDepth + ", got: " + actualMaxDepth);
    }
}