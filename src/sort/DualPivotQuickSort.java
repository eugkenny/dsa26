package sort;

public class DualPivotQuickSort<E extends Comparable<E>> implements Sorter<E> {

    private static final int INSERTION_SORT_CUTOFF = 17;

    @Override
    public void sort(E[] items) {
        sort(items, 0, items.length - 1);
    }

    private void sort(E[] items, int lo, int hi) {

        // Use Insertion Sort for small subarrays.
        if (hi - lo + 1 <= INSERTION_SORT_CUTOFF) {
            SortUtils.insertionSort(items, lo, hi);
            return;
        }

        // Ensure the left pivot is not greater than the right pivot.
        if (items[lo].compareTo(items[hi]) > 0) {
            swap(items, lo, hi);
        }

        E leftPivot = items[lo];
        E rightPivot = items[hi];

        int left = lo + 1;
        int right = hi - 1;
        int current = left;

        while (current <= right) {
            if (items[current].compareTo(leftPivot) < 0) {
                // Item belongs in the left section.
                swap(items, current, left);
                left++;
                current++;
            }
            else if (items[current].compareTo(rightPivot) > 0) {
                // Find an item from the right that
                // does not belong in the right section.
                while (current < right && items[right].compareTo(rightPivot) > 0) {
                    right--;
                }
                swap(items, current, right);
                right--;

                // The item moved from the right may
                // belong in the left section.
                if (items[current].compareTo(leftPivot) < 0) {
                    swap(items, current, left);
                    left++;
                }
                current++;
            } else {
                // Item belongs between the two pivots.
                current++;
            }
        }

        // Move the pivots into their final positions.
        left--;
        right++;

        swap(items, lo, left);
        swap(items, hi, right);

        // Sort the three resulting sections.
        sort(items, lo, left - 1);

        // If the pivots are equal, everything in the
        // middle section is equal to the pivot.
        if (leftPivot.compareTo(rightPivot) != 0) {
            sort(items, left + 1, right - 1);
        }

        sort(items, right + 1, hi);
    }

    private void swap(E[] items, int i, int j) {
        E temp = items[i];
        items[i] = items[j];
        items[j] = temp;
    }
}