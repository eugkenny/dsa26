import java.util.Iterator;
import java.util.NoSuchElementException;

public class BinarySearchTreeMap<K extends Comparable<K>, V> implements Map<K, V> {

    private Node<K, V> root;
    private int size;

    public BinarySearchTreeMap() {
        root = null;
        size = 0;
    }

    @Override
    public V get(K key) {
        Node<K, V> node = find(root, key);
        if (node == null) return null;

        return node.value;
    }

    // Lookup
    private Node<K, V> find(Node<K, V> node, K key) {
        if (node == null) return null;

        int cmp = key.compareTo(node.key);
        if (cmp < 0) return find(node.left, key);
        if (cmp > 0) return find(node.right, key);
        return node;
    }

    @Override
    public boolean containsKey(K key) {
        return find(root, key) != null;
    }

    @Override
    public V put(K key, V value) {
        if (value == null) throw new IllegalArgumentException("Null values are not supported");

        Node<K, V> existing = find(root, key);
        if (existing != null) {
            V oldValue = existing.value;
            existing.value = value;
            return oldValue;
        }

        root = put(root, key, value);
        size++;

        return null;
    }

    private Node<K, V> put(Node<K, V> node, K key, V value) {
        if (node == null) return new Node<>(key, value);

        int cmp = key.compareTo(node.key);

        if (cmp < 0) node.left = put(node.left, key, value);
        else node.right = put(node.right, key, value);
        updateSize(node);
        return node;
    }

    @Override
    public V remove(K key) {
        Node<K, V> existing = find(root, key);
        if (existing == null) return null;

        V oldValue = existing.value;
        root = remove(root, key);
        size--;

        return oldValue;
    }

    private Node<K, V> remove(Node<K, V> node, K key) {
        if (node == null) return null;

        int comparison = key.compareTo(node.key);
        if (comparison < 0) node.left = remove(node.left, key);
        else if (comparison > 0) node.right = remove(node.right, key);
        else {
            // No left child.
            if (node.left == null) return node.right;
            // No right child.
            if (node.right == null) return node.left;

            /*
             * Two children: replace with inorder successor.
             */
            Node<K, V> successor = minNode(node.right);
            node.key = successor.key;
            node.value = successor.value;
            node.right = removeMin(node.right);
        }
        updateSize(node);
        return node;
    }

    private Node<K, V> removeMin(Node<K, V> node) {
        if (node.left == null) return node.right;

        node.left = removeMin(node.left);
        updateSize(node);
        return node;
    }

    public K minKey() {
        if (isEmpty()) throw new NoSuchElementException("Map is empty");
        return minNode(root).key;
    }

    private Node<K, V> minNode(Node<K, V> node) {
        if (node.left == null) return node;
        return minNode(node.left);
    }

    public K maxKey() {
        if (isEmpty()) throw new NoSuchElementException("Map is empty");
        return maxNode(root).key;
    }

    private Node<K, V> maxNode(Node<K, V> node) {
        if (node.right == null) return node;
        return maxNode(node.right);
    }

    public K floor(K key) {
        Node<K, V> node = floor(root, key);

        if (node == null) return null;
        return node.key;
    }

    private Node<K, V> floor(Node<K, V> node, K key) {
        if (node == null) return null;

        int cmp = key.compareTo(node.key);
        if (cmp == 0) return node;
        if (cmp < 0) return floor(node.left, key);

        Node<K, V> candidate = floor(node.right, key);
        if (candidate != null) return candidate;
        return node;
    }

    public K ceiling(K key) {
        Node<K, V> node = ceiling(root, key);
        if (node == null) return null;
        return node.key;
    }

    private Node<K, V> ceiling(Node<K, V> node, K key) {
        if (node == null) return null;

        int cmp = key.compareTo(node.key);
        if (cmp == 0) return node;
        if (cmp > 0) return ceiling(node.right, key);

        Node<K, V> candidate = ceiling(node.left, key);
        if (candidate != null) return candidate;
        return node;
    }

    public int rank(K key) {
        return rank(root, key);
    }

    private int rank(Node<K, V> node, K key) {
        if (node == null) return 0;

        int cmp = key.compareTo(node.key);
        if (cmp < 0) return rank(node.left, key);
        else if (cmp > 0) return 1 + nodeSize(node.left) + rank(node.right, key);
        else return nodeSize(node.left);
    }

    public K select(int rank) {
        if (rank < 0 || rank >= size) throw new IndexOutOfBoundsException("Invalid rank: " + rank);
        return select(root, rank).key;
    }

    private Node<K, V> select(Node<K, V> node, int rank) {
        int leftSize = nodeSize(node.left);

        if (rank < leftSize) return select(node.left, rank);
        else if (rank > leftSize) return select(node.right, rank - leftSize - 1);
        else return node;
    }

    private int nodeSize(Node<K, V> node) {
        if (node == null) return 0;
        return node.size;
    }

    private void updateSize(Node<K, V> node) {
        node.size = 1 + nodeSize(node.left) + nodeSize(node.right);
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
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public Iterator<K> iterator() {
        return iterator(TraversalOrder.IN_ORDER);
    }

    public Iterator<K> iterator(TraversalOrder order) {
        Queue<K> queue = new LinkedQueue<>();

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

    private void inorder(Node<K, V> node, Queue<K> queue) {
        if (node == null) return;

        inorder(node.left, queue);
        queue.enqueue(node.key);
        inorder(node.right, queue);
    }

    private void preorder(Node<K, V> node, Queue<K> queue) {
        if (node == null) return;

        queue.enqueue(node.key);
        preorder(node.left, queue);
        preorder(node.right, queue);
    }

    private void postorder(Node<K, V> node, Queue<K> queue) {
        if (node == null) return;

        postorder(node.left, queue);
        postorder(node.right, queue);
        queue.enqueue(node.key);
    }

    private void levelorder(Node<K, V> root, Queue<K> result) {
        if (root == null) return;

        Queue<Node<K, V>> nodes = new LinkedQueue<>();
        nodes.enqueue(root);

        while (!nodes.isEmpty()) {
            Node<K, V> node = nodes.dequeue();
            result.enqueue(node.key);

            if (node.left != null) nodes.enqueue(node.left);
            if (node.right != null) nodes.enqueue(node.right);
        }
    }

    private static class Node<K, V> {
        private K key;
        private V value;

        private Node<K, V> left;
        private Node<K, V> right;

        // Number of nodes in this subtree.
        private int size;

        private Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.size = 1;
        }
    }

    private class QueueIterator implements Iterator<K> {
        private final Queue<K> queue;

        private QueueIterator(Queue<K> queue) {
            this.queue = queue;
        }

        @Override
        public boolean hasNext() {
            return !queue.isEmpty();
        }

        @Override
        public K next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            return queue.dequeue();
        }
    }
}