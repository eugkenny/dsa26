import java.util.Iterator;
import java.util.NoSuchElementException;

public class BinarySearchTreeSet<E extends Comparable<E>> implements Set<E> {
    private Node<E> root;
    private int size;

    public BinarySearchTreeSet() {
        root = null;
        size = 0;
    }

    @Override
    public boolean add(E item) {
        if (contains(item)) {
            return false;
        }

        root = add(root, item);
        size++;

        return true;
    }

    private Node<E> add(Node<E> node, E item) {
        if (node == null) return new Node<>(item);

        int cmp = item.compareTo(node.item);
        if (cmp < 0) node.left = add(node.left, item);
        else if (cmp > 0) node.right = add(node.right, item);

        node.size = 1 + size(node.left) + size(node.right);
        return node;
    }

    @Override
    public boolean contains(E item) {
        return contains(root, item);
    }

    private boolean contains(Node<E> node, E item) {
        if (node == null) return false;

        int cmp = item.compareTo(node.item);
        if (cmp < 0) return contains(node.left, item);
        if (cmp > 0) return contains(node.right, item);

        return true;
    }

    @Override
    public boolean remove(E item) {
        if (!contains(item)) return false;

        root = remove(root, item);
        size--;
        return true;
    }

    private Node<E> remove(Node<E> node, E item) {
        if (node == null) return null;

        int cmp = item.compareTo(node.item);
        if (cmp < 0) node.left = remove(node.left, item);
        else if (cmp > 0) node.right = remove(node.right, item);
        else {
            // No left child.
            if (node.left == null) return node.right;
            // No right child.
            if (node.right == null) return node.left;

            // Two children:
            // replace with inorder successor.
            Node<E> successor = minNode(node.right);
            node.item = successor.item;
            node.right = removeMin(node.right);
        }

        node.size = 1 + size(node.left) + size(node.right);
        return node;
    }

    public E min() {
        if (isEmpty()) {
            throw new NoSuchElementException("Tree is empty");
        }

        return minNode(root).item;
    }

    private Node<E> minNode(Node<E> node) {
        if (node.left == null) return node;

        return minNode(node.left);
    }

    public E max() {
        if (isEmpty()) throw new NoSuchElementException("Tree is empty");

        return maxNode(root).item;
    }

    private Node<E> maxNode(Node<E> node) {
        if (node.right == null) return node;

        return maxNode(node.right);
    }

    private Node<E> removeMin(Node<E> node) {
        if (node.left == null) return node.right;

        node.left = removeMin(node.left);

        node.size = 1 + size(node.left) + size(node.right);
        return node;
    }

    public E floor(E item) {
        Node<E> node = floor(root, item);
        if (node == null) return null;
        return node.item;
    }

    private Node<E> floor(Node<E> node, E item) {
        if (node == null) return null;

        int cmp = item.compareTo(node.item);
        if (cmp == 0) return node;

        if (cmp < 0) return floor(node.left, item);

        Node<E> candidate = floor(node.right, item);
        if (candidate != null) {
            return candidate;
        }
        return node;
    }

    public E ceiling(E item) {
        Node<E> node = ceiling(root, item);
        if (node == null) return null;
        return node.item;
    }

    private Node<E> ceiling(Node<E> node, E item) {
        if (node == null) return null;

        int cmp = item.compareTo(node.item);
        if (cmp == 0) return node;

        if (cmp > 0) return ceiling(node.right, item);

        Node<E> candidate = ceiling(node.left, item);
        if (candidate != null) {
            return candidate;
        }
        return node;
    }

    public int rank(E item) {
        return rank(root, item);
    }

    private int rank(Node<E> node, E item) {
        if (node == null) return 0;

        int cmp = item.compareTo(node.item);
        if (cmp < 0)
            return rank(node.left, item);
        else if (cmp > 0)
            return 1 + size(node.left) + rank(node.right, item);
        else {
            return size(node.left);
        }
    }

    public E select(int rank) {
        if (rank < 0 || rank >= size()) throw new IndexOutOfBoundsException();

        return select(root, rank).item;
    }

    private Node<E> select(Node<E> node, int rank) {
        int leftSize = size(node.left);

        if (rank < leftSize) return select(node.left, rank);
        else if (rank > leftSize) return select(node.right, rank - leftSize - 1);
        else return node;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    private int size(Node<E> node) {
        return node == null ? 0 : node.size;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public Iterator<E> iterator() {
        return iterator(TraversalOrder.IN_ORDER);
    }

    public Iterator<E> iterator(TraversalOrder order) {
        Queue<E> queue = new LinkedQueue<>();

        switch (order) {
            case IN_ORDER:
                inorder(root, queue);
                break;
            case PRE_ORDER:
                preorder(root, queue);
                break;
            case POST_ORDER:
                postorder(root, queue);
                break;
            case LEVEL_ORDER:
                levelorder(root, queue);
                break;
        }
        return new QueueIterator(queue);
    }

    private void inorder(Node<E> node, Queue<E> queue) {
        if (node == null) return;

        inorder(node.left, queue);
        queue.enqueue(node.item);
        inorder(node.right, queue);
    }

    private void preorder(Node<E> node, Queue<E> queue) {
        if (node == null) return;

        queue.enqueue(node.item);
        preorder(node.left, queue);
        preorder(node.right, queue);
    }

    private void postorder(Node<E> node, Queue<E> queue) {
        if (node == null) return;

        postorder(node.left, queue);
        postorder(node.right, queue);
        queue.enqueue(node.item);
    }

    private void levelorder(Node<E> root, Queue<E> result) {
        if (root == null)  return;

        Queue<Node<E>> nodes = new LinkedQueue<>();
        nodes.enqueue(root);

        while (!nodes.isEmpty()) {
            Node<E> node = nodes.dequeue();
            result.enqueue(node.item);

            if (node.left != null)
                nodes.enqueue(node.left);

            if (node.right != null)
                nodes.enqueue(node.right);
        }
    }

    private static class Node<E> {

        private E item;

        private Node<E> left;
        private Node<E> right;

        private int size;

        private Node(E item) {
            this.item = item;
            this.size = 1;
        }
    }

    private class QueueIterator implements Iterator<E> {
        private final Queue<E> queue;

        private QueueIterator(Queue<E> queue) {
            this.queue = queue;
        }

        @Override
        public boolean hasNext() {
            return !queue.isEmpty();
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return queue.dequeue();
        }
    }
}