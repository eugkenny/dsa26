import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class LinkedList<E> implements List<E> {

    private Node<E> head;
    private Node<E> tail;
    private int size;

    private static class Node<E> {

        E item;
        Node<E> next;

        Node(E item) {
            this.item = item;
            this.next = null;
        }
    }

    public LinkedList() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public void add(E item) {
        Node<E> newNode = new Node<>(item);

        if (isEmpty()) {
            head = newNode;
            tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }

        size++;
    }

    @Override
    public void add(int index, E item) {
        checkPosition(index);

        if (index == size) {
            add(item);
            return;
        }

        Node<E> newNode = new Node<>(item);

        if (index == 0) {
            newNode.next = head;
            head = newNode;
        } else {
            Node<E> previous = nodeAt(index - 1);

            newNode.next = previous.next;
            previous.next = newNode;
        }

        size++;
    }

    @Override
    public E get(int index) {
        checkIndex(index);

        return nodeAt(index).item;
    }

    @Override
    public E set(int index, E item) {
        checkIndex(index);

        Node<E> node = nodeAt(index);

        E oldItem = node.item;
        node.item = item;

        return oldItem;
    }

    @Override
    public E remove(int index) {
        checkIndex(index);

        E removedItem;

        if (index == 0) {
            removedItem = head.item;
            head = head.next;

            if (head == null) {
                tail = null;
            }
        } else {
            Node<E> previous = nodeAt(index - 1);
            Node<E> removedNode = previous.next;

            removedItem = removedNode.item;
            previous.next = removedNode.next;

            if (removedNode == tail) {
                tail = previous;
            }
        }

        size--;

        return removedItem;
    }

    @Override
    public boolean remove(E item) {
        Node<E> current = head;
        Node<E> previous = null;

        while (current != null) {
            if (Objects.equals(current.item, item)) {

                if (previous == null) {
                    head = current.next;
                } else {
                    previous.next = current.next;
                }

                if (current == tail) {
                    tail = previous;
                }

                size--;
                return true;
            }

            previous = current;
            current = current.next;
        }

        return false;
    }

    @Override
    public boolean contains(E item) {
        return indexOf(item) != -1;
    }

    @Override
    public int indexOf(E item) {
        Node<E> current = head;
        int index = 0;

        while (current != null) {
            if (Objects.equals(current.item, item)) {
                return index;
            }

            current = current.next;
            index++;
        }

        return -1;
    }

    @Override
    public int lastIndexOf(E item) {
        Node<E> current = head;
        int index = 0;
        int lastIndex = -1;

        while (current != null) {
            if (Objects.equals(current.item, item)) {
                lastIndex = index;
            }

            current = current.next;
            index++;
        }

        return lastIndex;
    }

    @Override
    public void clear() {
        head = null;
        tail = null;
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
        return new LinkedListIterator();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        result.append("[");

        Node<E> current = head;

        while (current != null) {
            result.append(current.item);

            if (current.next != null) {
                result.append(", ");
            }

            current = current.next;
        }

        result.append("]");

        return result.toString();
    }

    private Node<E> nodeAt(int index) {
        Node<E> current = head;

        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        return current;
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

    private class LinkedListIterator implements Iterator<E> {

        private Node<E> current = head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            E item = current.item;
            current = current.next;

            return item;
        }
    }
}
