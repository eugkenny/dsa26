package sort;

public class QuickSort<E extends Comparable<E>> implements Sorter<E> {

    private static final int INSERTION_SORT_CUTOFF = 10;

    @Override
    public void sort(E[] items) {
        sort(items, 0, items.length - 1);
    }

    private void sort(E[] items, int low, int high) {

        // Base case: zero or one item.
//        if (low >= high) {
//            return;
//        }

        // Use Insertion Sort for small subarrays.
        if (high - low + 1 <= INSERTION_SORT_CUTOFF) {
            SortUtils.insertionSort(items, low, high);
            return;
        }

        int pivotIndex = partition(items, low, high);

        // Sort the sections on either side of the pivot.
        sort(items, low, pivotIndex - 1);
        sort(items, pivotIndex + 1, high);
    }
    private int partition(E[] items, int lo, int hi) {

        int mid = lo + (hi - lo) / 2;

        // Order the first, middle and last items.
        if (items[mid].compareTo(items[lo]) < 0) {
            swap(items, lo, mid);
        }
        if (items[hi].compareTo(items[lo]) < 0) {
            swap(items, lo, hi);
        }
        if (items[hi].compareTo(items[mid]) < 0) {
            swap(items, mid, hi);
        }

        // The middle item is now the median.
        // Move it to the start to use as the pivot.
        swap(items, lo, mid);

        E pivot = items[lo];

        int i = lo;
        int j = hi + 1;

        while (true) {
            // Scan from the left for an item that belongs on the right.
            do {
                i++;
            } while (i != hi && items[i].compareTo(pivot) < 0);
            // Scan from the right for an item that belongs on the left.
            do {
                j--;
            } while (j != lo && items[j].compareTo(pivot) > 0);
            // Stop when the scans meet or cross.
            if (i >= j) {
                break;
            }
            swap(items, i, j);
        }
        // Place the pivot into its final position.
        swap(items, lo, j);

        return j;
    }

    private void swap(E[] items, int i, int j) {
        E temp = items[i];
        items[i] = items[j];
        items[j] = temp;
    }
}