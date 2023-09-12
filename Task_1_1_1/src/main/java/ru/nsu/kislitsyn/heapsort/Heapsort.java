package ru.nsu.kislitsyn.heapsort;

/**
* The only class in this file.
* <p>
* Contains of the sort method.
* </p>
*/
public class Heapsort {
    /**
    *
    * @param arr is the array we are sorting.
    * @return we return a sorted array.
    * <p>
    * sort is the main function of the class since it makes the sort itself.
    * </p>
    */
    public static int[] sort(int[] arr) {
        int len = arr.length;

        for (int i = (len / 2) - 1; i >= 0; i--) {
            heapify(arr, i, len);
        }

        for (int i = len - 1; i >= 0; i--) {
            int tmp = arr[0];
            arr[0] = arr[i];
            arr[i] = tmp;

            heapify(arr, 0, i);
        }

        return arr;
    }

    /**
    * @param arr   is the array we are sorting.
    * @param start is the index where we need to start making heap out of array.
    * @param len   is length of the part of array which is not converted to heap yet.
    *              <p>
    *              This function makes heap out of array by changing children
    *              with parents recursively through the whole array.
    *              </p>
    */
    static void heapify(int[] arr, int start, int len) {
        int largest = start;
        int left = 2 * start + 1;
        int right = 2 * start + 2;

        if (left < len && arr[left] > arr[largest]) {
            largest = left;
        }

        if (right < len && arr[right] > arr[largest]) {
            largest = right;
        }

        if (largest != start) {
            int tmp = arr[largest];
            arr[largest] = arr[start];
            arr[start] = tmp;

            heapify(arr, largest, len);
        }
    }
}
