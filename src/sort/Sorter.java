package sort;

public interface Sorter<E extends Comparable<E>> {

    // Sort the supplied array into ascending order.
    void sort(E[] items);
}