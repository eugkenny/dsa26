import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayQueue<E> implements Queue<E> {

    private E[] items;
    private int front;
    private int rear;
    private int size;

    @SuppressWarnings("unchecked")
    public ArrayQueue() {
        items = (E[]) new Object[10];
        front = 0;
        rear = 0;
        size = 0;
    }

    @Override
    public void enqueue(E item) {
        if (size == items.length) {
            resize(items.length * 2);
        }

        items[rear] = item;
        rear = (rear + 1) % items.length;
        size++;
    }

    @Override
    public E dequeue() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }

        E item = items[front];
        items[front] = null;

        front = (front + 1) % items.length;
        size--;

        return item;
    }

    @Override
    public E peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }

        return items[front];
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
        return new QueueIterator();
    }

    private class QueueIterator implements Iterator<E> {

        private int index = 0;

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            E item = items[(front + index) % items.length];
            index++;

            return item;
        }
    }

    private void resize(int newCapacity) {
        @SuppressWarnings("unchecked")
        E[] newItems = (E[]) new Object[newCapacity];

        for (int i = 0; i < size; i++) {
            newItems[i] = items[(front + i) % items.length];
        }

        items = newItems;
        front = 0;
        rear = size;
    }
}
