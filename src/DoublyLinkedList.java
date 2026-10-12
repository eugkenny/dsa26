import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class DoublyLinkedList<E> implements List<E> {

    private Node<E> head;
    private Node<E> tail;
    private int size;

    private static class Node<E> {

        E item;
        Node<E> previous;
        Node<E> next;

        Node(E item) {
            this.item = item;
            this.previous = null;
            this.next = null;
        }
    }

    public DoublyLinkedList() {
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
            newNode.previous = tail;
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

        Node<E> current = nodeAt(index);
        Node<E> newNode = new Node<>(item);

        newNode.next = current;
        newNode.previous = current.previous;

        if (current.previous == null) {
            head = newNode;
        } else {
            current.previous.next = newNode;
        }

        current.previous = newNode;

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

        Node<E> node = nodeAt(index);
        E removedItem = node.item;

        unlink(node);

        return removedItem;
    }

    @Override
    public boolean remove(E item) {
        Node<E> current = head;

        while (current != null) {
            if (Objects.equals(current.item, item)) {
                unlink(current);
                return true;
            }

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
        Node<E> current = tail;
        int index = size - 1;

        while (current != null) {
            if (Objects.equals(current.item, item)) {
                return index;
            }

            current = current.previous;
            index--;
        }

        return -1;
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
        return new DoublyLinkedListIterator();
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
        if (index < size / 2) {
            Node<E> current = head;

            for (int i = 0; i < index; i++) {
                current = current.next;
            }

            return current;
        } else {
            Node<E> current = tail;

            for (int i = size - 1; i > index; i--) {
                current = current.previous;
            }

            return current;
        }
    }

    private void unlink(Node<E> node) {
        if (node.previous == null) {
            head = node.next;
        } else {
            node.previous.next = node.next;
        }

        if (node.next == null) {
            tail = node.previous;
        } else {
            node.next.previous = node.previous;
        }

        size--;
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

    private class DoublyLinkedListIterator implements Iterator<E> {

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
