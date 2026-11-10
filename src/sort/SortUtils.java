package sort;

class SortUtils {

    static <E extends Comparable<E>> void insertionSort(E[] items, int lo, int hi) {

        for (int i = lo + 1; i <= hi; i++) {

            E key = items[i];
            int j = i - 1;

            while (j >= lo &&
                    items[j].compareTo(key) > 0) {

                items[j + 1] = items[j];
                j--;
            }

            items[j + 1] = key;
        }
    }
}