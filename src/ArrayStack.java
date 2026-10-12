import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayStack<E> implements Stack<E> {

    private E[] items;
    private int size;

    @SuppressWarnings("unchecked")
    public ArrayStack() {
        items = (E[]) new Object[10];
        size = 0;
    }

    @Override
    public void push(E item) {
        if (size == items.length) {
            resize(items.length * 2);
        }

        items[size] = item;
        size++;
    }

    @Override
    public E pop() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }

        size--;

        E item = items[size];
        items[size] = null;

        return item;
    }

    @Override
    public E peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Stack is empty");
        }

        return items[size - 1];
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
        return new StackIterator();
    }

    private class StackIterator implements Iterator<E> {

        private int current = size - 1;

        @Override
        public boolean hasNext() {
            return current >= 0;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            E item = items[current];
            current--;

            return item;
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
}
