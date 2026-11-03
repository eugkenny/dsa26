package sort;

public class SelectionSort<E extends Comparable<E>> implements Sorter<E> {

    @Override
    public void sort(E[] items) {

        for (int i = 0; i < items.length - 1; i++) {

            int minIndex = i;

            for (int j = i + 1; j < items.length; j++) {
                if (items[j].compareTo(items[minIndex]) < 0) {
                    minIndex = j;
                }
            }

            swap(items, i, minIndex);
        }
    }

    private void swap(E[] items, int i, int j) {
        E temp = items[i];
        items[i] = items[j];
        items[j] = temp;
    }
}