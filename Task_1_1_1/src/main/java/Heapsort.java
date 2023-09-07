public class Heapsort {
    public void sort (int arr[]){
        int len = arr.length;

        for (int i = (len / 2) - 1; i >= 0; i--){
            heapify(arr, i, len);
        }

        for (int i = len - 1; i >= 0; i--){
            int tmp = arr[0];
            arr[0] = arr[i];
            arr[i] = tmp;

            heapify(arr, 0, i);
        }
    }

    void heapify(int arr[], int start, int len){
        int largest = start;
        int left = 2 * start + 1;
        int right = 2 * start + 2;

        if (left < len && arr[left] > arr[largest])
            largest = left;

        if (right< len && arr[right] > arr[largest])
            largest = right;

        if (largest != start){
            int tmp = arr[largest];
            arr[largest] = arr[start];
            arr[start] = tmp;

            heapify(arr, largest, len);
        }
    }
    public static void main(String args[]) {
        int arr[] = {1, 4, 5, 73, 3, 24, -1, 2};
        int len = arr.length;

        Heapsort ob = new Heapsort();
        ob.sort(arr);


        for (int i = 0; i < len; i++){
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
}
