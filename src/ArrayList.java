import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class ArrayList<E> implements List<E> {

    private E[] items;
    private int size;

    @SuppressWarnings("unchecked")
    public ArrayList() {
        items = (E[]) new Object[10];
        size = 0;
    }

    @Override
    public void add(E item) {
        if (size == items.length) {
            resize(items.length * 2);
        }

        items[size] = item;
        size++;
    }

    @Override
    public void add(int index, E item) {
        checkPosition(index);

        if (size == items.length) {
            resize(items.length * 2);
        }

        for (int i = size; i > index; i--) {
            items[i] = items[i - 1];
        }

        items[index] = item;
        size++;
    }

    @Override
    public E get(int index) {
        checkIndex(index);

        return items[index];
    }

    @Override
    public E set(int index, E item) {
        checkIndex(index);

        E oldItem = items[index];
        items[index] = item;

        return oldItem;
    }

    @Override
    public E remove(int index) {
        checkIndex(index);

        E removedItem = items[index];

        for (int i = index; i < size - 1; i++) {
            items[i] = items[i + 1];
        }

        size--;
        items[size] = null;

        return removedItem;
    }

    @Override
    public boolean remove(E item) {
        int index = indexOf(item);

        if (index == -1) {
            return false;
        }

        remove(index);
        return true;
    }

    @Override
    public boolean contains(E item) {
        return indexOf(item) != -1;
    }

    @Override
    public int indexOf(E item) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(items[i], item)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public int lastIndexOf(E item) {
        for (int i = size - 1; i >= 0; i--) {
            if (Objects.equals(items[i], item)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            items[i] = null;
        }

        size = 0;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<E> iterator() {
        return new ArrayListIterator();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        result.append("[");

        for (int i = 0; i < size; i++) {
            result.append(items[i]);

            if (i < size - 1) {
                result.append(", ");
            }
        }

        result.append("]");

        return result.toString();
    }


    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(
                    "Index: " + index + ", size: " + size);
        }
    }

    private void checkPosition(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException(
                    "Index: " + index + ", size: " + size);
        }
    }

    private void resize(int newCapacity) {
        @SuppressWarnings("unchecked")
        E[] newItems = (E[]) new Object[newCapacity];

        for (int i = 0; i < size; i++) {
            newItems[i] = items[i];
        }

        items = newItems;
    }

    private class ArrayListIterator implements Iterator<E> {

        private int current = 0;

        @Override
        public boolean hasNext() {
            return current < size;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            E item = items[current];
            current++;

            return item;
        }
    }
}
