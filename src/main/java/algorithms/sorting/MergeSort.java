package algorithms.sorting;

import algorithms.metrics.MetricsTracker;

public class MergeSort {


    public static void sort(int[] array, MetricsTracker tracker) {
        if (array == null || array.length < 2) return;

        int[] aux = new int[array.length];


        tracker.addAllocations(array.length * 4);

        mergeSort(array, aux, 0, array.length - 1, tracker);
    }


    private static void mergeSort(int[] array, int[] aux, int low, int high, MetricsTracker tracker) {
        if (low >= high) {
            return;
        }


        tracker.enterRecursion();

        int mid = low + (high - low) / 2;


        mergeSort(array, aux, low, mid, tracker);
        mergeSort(array, aux, mid + 1, high, tracker);


        merge(array, aux, low, mid, high, tracker);


        tracker.exitRecursion();
    }


    private static void merge(int[] array, int[] aux, int low, int mid, int high, MetricsTracker tracker) {

        System.arraycopy(array, low, aux, low, high - low + 1);

        int i = low;
        int j = mid + 1;


        for (int k = low; k <= high; k++) {

            if (i > mid) {

                array[k] = aux[j++];
            } else if (j > high) {

                array[k] = aux[i++];
            } else {

                tracker.incrementComparisons();


                if (aux[i] <= aux[j]) {
                    array[k] = aux[i++];
                } else {
                    array[k] = aux[j++];
                }
            }
        }
    }
}