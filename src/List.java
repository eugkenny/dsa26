import java.util.Iterator;

public interface List<E> extends Iterable<E> {

    // Add an item to the end of the list.
    void add(E item);

    // Insert an item at the specified index.
    // Valid positions are 0 through size().
    void add(int index, E item);

    // Return the item at the specified index.
    E get(int index);

    // Replace the item at the specified index.
    // Return the item that was previously stored there.
    E set(int index, E item);

    // Remove and return the item at the specified index.
    E remove(int index);

    // Remove the first occurrence of the specified item.
    // Return true if an item was removed, otherwise false.
    boolean remove(E item);

    // Return true if the list contains the specified item.
    boolean contains(E item);

    // Return the index of the first occurrence of the item,
    // or -1 if the item is not present.
    int indexOf(E item);

    // Return the index of the last occurrence of the item,
    // or -1 if the item is not present.
    int lastIndexOf(E item);

    // Remove all items from the list.
    void clear();

    // Return true if the list contains no items.
    boolean isEmpty();

    // Return the number of items currently in the list.
    int size();
}
