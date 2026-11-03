package sort;

public class ShellSort<E extends Comparable<E>> implements Sorter<E> {

    @Override
    public void sort(E[] items) {

        int n = items.length;

        // Find the largest gap in the 3x + 1 sequence
        // appropriate for this array.
        int gap = 1;

        while (gap < n / 3) {
            gap = 3 * gap + 1;
        }

        // Perform a gapped insertion sort.
        while (gap >= 1) {

            for (int i = gap; i < n; i++) {
                E item = items[i];
                int j = i;

                // Shift larger gap-separated items to the right.
                while (j >= gap && items[j - gap].compareTo(item) > 0) {
                    items[j] = items[j - gap];
                    j -= gap;
                }
                // Insert the item into its correct position.
                items[j] = item;
            }
            // Move to the next smaller gap in the 3x + 1 sequence.
            gap = gap/3;
        }
    }
}