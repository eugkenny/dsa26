package sort;

public class NaturalMergeSort<E extends Comparable<E>> implements Sorter<E> {

    private static final int MIN_RUN = 16;

    @Override
    public void sort(E[] items) {

        if (items.length < 2) {
            return;
        }

        E[] auxiliary = items.clone();

        // At most one run can begin at each array position.
        int[] runStarts = new int[items.length];
        int[] runEnds = new int[items.length];

        int runCount = findRuns(items, runStarts, runEnds);

        // Repeatedly merge neighbouring runs.
        while (runCount > 1) {
            int newRunCount = 0;

            for (int i = 0; i < runCount; i += 2) {
                // No partner: carry the final run forward.
                if (i + 1 == runCount) {
                    runStarts[newRunCount] = runStarts[i];
                    runEnds[newRunCount] = runEnds[i];
                } else {
                    int lo = runStarts[i];
                    int mid = runEnds[i];
                    int hi = runEnds[i + 1];

                    merge(items, auxiliary, lo, mid, hi);

                    runStarts[newRunCount] = lo;
                    runEnds[newRunCount] = hi;
                }

                newRunCount++;
            }

            runCount = newRunCount;
        }
    }

    private int findRuns(E[] items, int[] runStarts, int[] runEnds) {
        int runCount = 0;
        int start = 0;

        while (start < items.length) {
            int end = findRun(items, start);
            // Extend short runs using Insertion Sort.
            int minimumEnd = Math.min(items.length - 1, start + MIN_RUN - 1);

            if (end < minimumEnd) {
                SortUtils.insertionSort(items, start, minimumEnd);
                end = minimumEnd;
            }

            runStarts[runCount] = start;
            runEnds[runCount] = end;
            runCount++;

            start = end + 1;
        }
        return runCount;
    }

    private int findRun(E[] items, int start) {
        if (start == items.length - 1) {
            return start;
        }

        int end = start + 1;

        if (items[end].compareTo(items[start]) < 0) { // Descending run.
            while (end + 1 < items.length && items[end + 1].compareTo(items[end]) < 0) {
                end++;
            }

            reverse(items, start, end);
        } else { // Ascending run.
            while (end + 1 < items.length && items[end + 1].compareTo(items[end]) >= 0) {
                end++;
            }
        }
        return end;
    }

    private void reverse(E[] items, int lo, int hi) {
        while (lo < hi) {
            swap(items, lo, hi);
            lo++;
            hi--;
        }
    }

    private void merge(E[] items, E[] auxiliary, int low, int mid, int high) {
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

    private void swap(E[] items, int i, int j) {
        E temp = items[i];
        items[i] = items[j];
        items[j] = temp;
    }
}