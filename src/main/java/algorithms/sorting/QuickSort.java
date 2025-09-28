package algorithms.sorting;

import algorithms.metrics.MetricsTracker;
import java.util.Random;

public class QuickSort {

    private static final Random RANDOM = new Random();


    public static void sort(int[] array, MetricsTracker tracker) {
        if (array == null || array.length < 2) return;


        quickSort(array, 0, array.length - 1, tracker);
    }


    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }



    private static void quickSort(int[] array, int low, int high, MetricsTracker tracker) {

        while (low < high) {

            int pivotIndex = partition(array, low, high, tracker);

            int leftSize = pivotIndex - low;
            int rightSize = high - pivotIndex;

            if (leftSize < rightSize) {



                tracker.enterRecursion();
                quickSort(array, low, pivotIndex - 1, tracker);
                tracker.exitRecursion();


                low = pivotIndex + 1;
            } else {



                tracker.enterRecursion();
                quickSort(array, pivotIndex + 1, high, tracker);
                tracker.exitRecursion();

                high = pivotIndex - 1;
            }
        }
    }


    private static int partition(int[] array, int low, int high, MetricsTracker tracker) {


        int randomIndex = low + RANDOM.nextInt(high - low + 1);
        swap(array, randomIndex, high);

        int pivot = array[high];
        int i = low;

        for (int j = low; j < high; j++) {


            tracker.incrementComparisons();

            if (array[j] <= pivot) {
                swap(array, i, j);
                i++;
            }
        }


        swap(array, i, high);

        return i;
    }
}