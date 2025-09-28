package algorithms.sorting;

import algorithms.metrics.MetricsTracker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class QuickSortTest {

    private MetricsTracker tracker;
    private static final int N_SMALL = 100;
    private static final int N_LARGE = 1000;

    @BeforeEach
    void setUp() {
        tracker = new MetricsTracker();
    }


    private void assertIsSorted(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            assertTrue(array[i] <= array[i + 1], "Array is not sorted, failure occurred at index " + i);
        }
    }



    @Test
    void testSortEmptyArray() {
        int[] arr = {};
        QuickSort.sort(arr, tracker);
        assertIsSorted(arr);
    }

    @Test
    void testSortAlreadySortedArray() {
        int[] arr = {1, 2, 3, 4, 5, 6, 7};
        QuickSort.sort(arr, tracker);
        assertIsSorted(arr);
    }

    @Test
    void testSortReverseSortedArray() {
        int[] arr = {7, 6, 5, 4, 3, 2, 1};
        QuickSort.sort(arr, tracker);
        assertIsSorted(arr);
    }

    @Test
    void testSortRandomArray() {
        Random rand = new Random(0);
        int[] arr = rand.ints(N_SMALL, 0, 1000).toArray();
        QuickSort.sort(arr, tracker);
        assertIsSorted(arr);
    }



    @Test
    void testComplexityNLogN() {
        Random rand = new Random(0);
        int N = N_LARGE;
        int[] arr = rand.ints(N, 0, 1000).toArray();

        QuickSort.sort(arr, tracker);


        double expectedComparisons = N * (Math.log(N) / Math.log(2));

        long actualComparisons = tracker.getSnapshot(N, 0).Comparisons;


        assertTrue(actualComparisons < 2.5 * expectedComparisons,
                "Too many comparisons! Expected O(N log N), got: " + actualComparisons);


        assertTrue(actualComparisons > 0.5 * expectedComparisons,
                "Too few comparisons! Expected minimum 0.5 * O(N log N), got: " + actualComparisons);
    }

    @Test

    void testRecursionDepthOptimized() {
        int N = 1024; // 2^10
        Random rand = new Random();
        int[] arr = rand.ints(N, 0, 1000).toArray(); // ИСПОЛЬЗУЕМ СЛУЧАЙНЫЙ МАССИВ

        QuickSort.sort(arr, tracker);


        int expectedMaxDepth = (int) Math.ceil(Math.log(N) / Math.log(2));

        int actualMaxDepth = tracker.getSnapshot(N, 0).MaxDepth;

        assertTrue(actualMaxDepth < N / 2,
                "Depth is linear (O(N)), optimization failed. Expected O(log N) depth, got: " + actualMaxDepth);
    }
}