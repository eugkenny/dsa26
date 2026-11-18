import java.util.NoSuchElementException;

public class BinaryHeap<E extends Comparable<E>> implements PriorityQueue<E> {

    private static final int INITIAL_CAPACITY = 8;

    private E[] heap;
    private int size;
    private final HeapOrder order;

    public BinaryHeap() {
        this(HeapOrder.MIN);
    }

    @SuppressWarnings("unchecked")
    public BinaryHeap(HeapOrder order) {
        heap = (E[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
        this.order = order;
    }

    @Override
    public void add(E item) {
        if (size == heap.length) {
            resize(heap.length * 2);
        }

        heap[size] = item;
        swim(size);
        size++;
    }

    @Override
    public E peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Priority queue is empty");
        }
        return heap[0];
    }

    @Override
    public E remove() {
        if (isEmpty()) {
            throw new NoSuchElementException("Priority queue is empty");
        }
        E item = heap[0];
        size--;

        heap[0] = heap[size];
        heap[size] = null;

        if (!isEmpty()) {
            sink(0);
        }
        return item;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    private void swim(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (!higherPriority(heap[index], heap[parent])) {
                break;
            }

            swap(index, parent);
            index = parent;
        }
    }

    private void sink(int index) {
        while (2 * index + 1 < size) {
            int child = 2 * index + 1;
            int right = 2 * index + 2;

            // Choose the child with higher priority.
            if (right < size && higherPriority(heap[right], heap[child])) {
                child = right;
            }

            if (!higherPriority(heap[child], heap[index])) {
                break;
            }

            swap(index, child);
            index = child;
        }
    }

    private boolean higherPriority(E a, E b) {
        int comparison = a.compareTo(b);

        if (order == HeapOrder.MIN) {
            return comparison < 0;
        }

        return comparison > 0;
    }

    private void swap(int i, int j) {
        E temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    @SuppressWarnings("unchecked")
    private void resize(int newCapacity) {
        E[] newHeap = (E[]) new Comparable[newCapacity];

        for (int i = 0; i < size; i++) {
            newHeap[i] = heap[i];
        }

        heap = newHeap;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        result.append("[");
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                result.append(", ");
            }
            result.append(heap[i]);
        }
        result.append("]");

        return result.toString();
    }
}