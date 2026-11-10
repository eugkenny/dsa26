package sort;

public class MergeSort<E extends Comparable<E>> implements Sorter<E> {

    private static final int INSERTION_SORT_CUTOFF = 7;

    @Override
    public void sort(E[] items) {

        // Create an auxiliary array used during merging.
        @SuppressWarnings("unchecked")
        E[] auxiliary = (E[]) new Comparable[items.length];
        sort(items, auxiliary, 0, items.length - 1);
    }

    private void sort(E[] items, E[] auxiliary, int low, int high) {

        // Base case: zero or one item.
//        if (low >= high) {
//            return;
//        }

        if (high - low + 1 <= INSERTION_SORT_CUTOFF) {
            SortUtils.insertionSort(items, low, high);
            return;
        }

        int mid = low + (high - low) / 2;

        // Sort the two halves.
        sort(items, auxiliary, low, mid);
        sort(items, auxiliary, mid + 1, high);

        // Merge the sorted halves.
        merge(items, auxiliary, low, mid, high);
    }

    private void merge(E[] items, E[] auxiliary, int low, int mid, int high) {
        // Copy the section being merged.
        for (int i = low; i <= high; i++) {
            auxiliary[i] = items[i];
        }

        int left = low;
        int right = mid + 1;

        for (int i = low; i <= high; i++) {
            if (left > mid) {
                // Left half is exhausted.
                items[i] = auxiliary[right];
                right++;
            } else if (right > high) {
                // Right half is exhausted.
                items[i] = auxiliary[left];
                left++;
            } else if (auxiliary[right].compareTo(auxiliary[left]) < 0) {
                items[i] = auxiliary[right];
                right++;
            } else {
                items[i] = auxiliary[left];
                left++;
            }
        }
    }
}