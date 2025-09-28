package algorithms.selection;

import algorithms.metrics.MetricsTracker;

public class DeterministicSelect {


    public static int select(int[] array, int k, MetricsTracker tracker) {
        if (array == null || array.length == 0 || k < 0 || k >= array.length) {
            throw new IllegalArgumentException("Invalid array or index k.");
        }
        return deterministicSelect(array, 0, array.length - 1, k, tracker);
    }


    private static void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }


    private static void insertionSort(int[] arr, int low, int high, MetricsTracker tracker) {
        for (int i = low + 1; i <= high; i++) {
            int current = arr[i];
            int j = i - 1;


            while (j >= low) {
                tracker.incrementComparisons();
                if (arr[j] > current) {
                    arr[j + 1] = arr[j];
                    j--;
                } else {
                    break;
                }
            }
            arr[j + 1] = current;
        }
    }


    private static int deterministicSelect(int[] array, int low, int high, int k, MetricsTracker tracker) {
        if (low == high) {
            return array[low];
        }


        tracker.enterRecursion();


        int pivot = findMedianOfMedians(array, low, high, tracker);


        int pivotIndex = partitionAround(array, low, high, pivot, tracker);


        int result;
        if (k == pivotIndex) {
            result = array[pivotIndex];
        } else if (k < pivotIndex) {

            result = deterministicSelect(array, low, pivotIndex - 1, k, tracker);
        } else {

            result = deterministicSelect(array, pivotIndex + 1, high, k, tracker);
        }

        tracker.exitRecursion();
        return result;
    }

    private static int findMedianOfMedians(int[] array, int low, int high, MetricsTracker tracker) {
        int n = high - low + 1;
        if (n <= 5) {
            insertionSort(array, low, high, tracker);
            return array[low + n / 2];
        }


        int numMedians = (n + 4) / 5;
        int[] medians = new int[numMedians];
        int medianIndex = 0;


        for (int i = low; i <= high; i += 5) {
            int groupHigh = Math.min(i + 4, high);
            insertionSort(array, i, groupHigh, tracker);


            medians[medianIndex++] = array[i + (groupHigh - i) / 2];
        }


        return deterministicSelect(medians, 0, numMedians - 1, medians.length / 2, tracker);
    }

    private static int partitionAround(int[] array, int low, int high, int pivotValue, MetricsTracker tracker) {

        int pivotLocation = -1;
        for (int i = low; i <= high; i++) {
            if (array[i] == pivotValue) {
                pivotLocation = i;
                break;
            }
        }
        swap(array, pivotLocation, high);

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