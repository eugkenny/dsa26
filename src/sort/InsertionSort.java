package sort;

public class InsertionSort<E extends Comparable<E>> implements Sorter<E> {

    @Override
    public void sort(E[] items) {

        for (int i = 1; i < items.length; i++) {

            E key = items[i];
            int j = i - 1;

            while (j >= 0 && items[j].compareTo(key) > 0) {
                items[j + 1] = items[j];
                j--;
            }

            items[j + 1] = key;
        }
    }
}