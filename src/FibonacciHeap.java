import java.util.NoSuchElementException;

public class FibonacciHeap<E extends Comparable<E>> implements PriorityQueue<E> {

    private Node<E> min;
    private int size;

    /*
     * A Node is exposed as a handle so that clients
     * can later perform decreaseKey().
     */
    public static class Node<E> {

        private E item;

        private Node<E> parent;
        private Node<E> child;

        private Node<E> left;
        private Node<E> right;

        private int degree;
        private boolean marked;

        private Node(E item) {

            this.item = item;

            left = this;
            right = this;
        }

        public E getItem() {
            return item;
        }
    }

    public FibonacciHeap() {
        min = null;
        size = 0;
    }

    /*
     * Standard PriorityQueue operation.
     */
    @Override
    public void add(E item) {
        insert(item);
    }

    /*
     * Alternative insertion operation that returns
     * a handle to the new node.
     *
     * The handle is required for decreaseKey().
     */
    public Node<E> insert(E item) {

        Node<E> node = new Node<>(item);

        addToRootList(node);

        size++;

        return node;
    }

    @Override
    public E peek() {

        if (isEmpty()) {
            throw new NoSuchElementException(
                    "Priority queue is empty");
        }

        return min.item;
    }

    @Override
    public E remove() {

        if (isEmpty()) {
            throw new NoSuchElementException(
                    "Priority queue is empty");
        }

        Node<E> removed = min;

        /*
         * Move all children of the minimum node
         * into the root list.
         */
        if (removed.child != null) {

            Node<E> child = removed.child;
            int childCount = removed.degree;

            for (int i = 0; i < childCount; i++) {

                Node<E> next = child.right;

                removeFromList(child);

                child.parent = null;
                child.marked = false;

                addToRootList(child);

                child = next;
            }
        }

        /*
         * Remove the minimum node itself.
         */
        if (removed.right == removed) {

            min = null;

        } else {

            Node<E> nextRoot = removed.right;

            removeFromList(removed);

            min = nextRoot;

            consolidate();
        }

        size--;

        return removed.item;
    }

    /*
     * Reduce the key of an existing node.
     *
     * Fibonacci heaps support this operation in
     * O(1) amortized time.
     */
    public void decreaseKey(
            Node<E> node,
            E newItem) {

        if (newItem.compareTo(node.item) > 0) {
            throw new IllegalArgumentException(
                    "New key must not be greater");
        }

        node.item = newItem;

        Node<E> parent = node.parent;

        /*
         * If heap order has been violated,
         * cut the node from its parent.
         */
        if (parent != null &&
                node.item.compareTo(parent.item) < 0) {

            cut(node, parent);
            cascadingCut(parent);
        }

        if (min == null ||
                node.item.compareTo(min.item) < 0) {

            min = node;
        }
    }

    /*
     * Merge another Fibonacci heap into this one.
     *
     * The other heap is empty afterwards.
     */
    public void merge(FibonacciHeap<E> other) {

        if (other == null || other.isEmpty()) {
            return;
        }

        if (isEmpty()) {

            min = other.min;
            size = other.size;

        } else {

            joinRootLists(min, other.min);

            if (other.min.item.compareTo(min.item) < 0) {
                min = other.min;
            }

            size += other.size;
        }

        other.min = null;
        other.size = 0;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    /*
     * Consolidate trees having the same degree.
     */
    private void consolidate() {

        int rootCount = countRoots();

        Node<E>[] roots =
                createNodeArray(rootCount);

        Node<E> current = min;

        for (int i = 0; i < rootCount; i++) {
            roots[i] = current;
            current = current.right;
        }

        Node<E>[] degreeTable =
                createNodeArray(8);

        for (Node<E> root : roots) {

            /*
             * A root may already have been linked
             * beneath another root.
             */
            if (root.parent != null) {
                continue;
            }

            Node<E> x = root;
            int degree = x.degree;

            while (true) {

                if (degree >= degreeTable.length) {
                    degreeTable =
                            grow(degreeTable);
                }

                Node<E> y =
                        degreeTable[degree];

                if (y == null) {
                    break;
                }

                /*
                 * x must contain the smaller root.
                 */
                if (y.item.compareTo(x.item) < 0) {

                    Node<E> temp = x;
                    x = y;
                    y = temp;
                }

                link(y, x);

                degreeTable[degree] = null;

                degree++;
            }

            degreeTable[degree] = x;
        }

        /*
         * Rebuild the root list from the
         * consolidated trees.
         */
        min = null;

        for (Node<E> node : degreeTable) {

            if (node != null) {

                node.left = node;
                node.right = node;

                addToRootList(node);
            }
        }
    }

    /*
     * Make child a child of parent.
     */
    private void link(
            Node<E> child,
            Node<E> parent) {

        removeFromList(child);

        child.parent = parent;
        child.marked = false;

        if (parent.child == null) {

            parent.child = child;

        } else {

            insertAfter(
                    parent.child,
                    child);
        }

        parent.degree++;
    }

    /*
     * Cut a node from its parent and move it
     * into the root list.
     */
    private void cut(
            Node<E> node,
            Node<E> parent) {

        if (parent.child == node) {

            if (node.right == node) {
                parent.child = null;
            } else {
                parent.child = node.right;
            }
        }

        removeFromList(node);

        parent.degree--;

        node.parent = null;
        node.marked = false;

        addToRootList(node);
    }

    /*
     * Perform cascading cuts.
     */
    private void cascadingCut(Node<E> node) {

        Node<E> parent = node.parent;

        if (parent == null) {
            return;
        }

        if (!node.marked) {

            node.marked = true;

        } else {

            cut(node, parent);
            cascadingCut(parent);
        }
    }

    /*
     * Add a node to the circular root list.
     */
    private void addToRootList(Node<E> node) {

        node.parent = null;

        if (min == null) {

            node.left = node;
            node.right = node;

            min = node;

        } else {

            insertAfter(min, node);

            if (node.item.compareTo(min.item) < 0) {
                min = node;
            }
        }
    }

    /*
     * Insert node immediately after position in
     * a circular doubly linked list.
     */
    private void insertAfter(
            Node<E> position,
            Node<E> node) {

        node.left = position;
        node.right = position.right;

        position.right.left = node;
        position.right = node;
    }

    /*
     * Remove a node from its circular list.
     */
    private void removeFromList(Node<E> node) {

        node.left.right = node.right;
        node.right.left = node.left;

        node.left = node;
        node.right = node;
    }

    /*
     * Join two circular root lists.
     */
    private void joinRootLists(
            Node<E> first,
            Node<E> second) {

        Node<E> firstRight = first.right;
        Node<E> secondLeft = second.left;

        first.right = second;
        second.left = first;

        secondLeft.right = firstRight;
        firstRight.left = secondLeft;
    }

    private int countRoots() {

        if (min == null) {
            return 0;
        }

        int count = 1;

        Node<E> current = min.right;

        while (current != min) {
            count++;
            current = current.right;
        }

        return count;
    }

    @SuppressWarnings("unchecked")
    private Node<E>[] createNodeArray(
            int capacity) {

        return (Node<E>[]) new Node[capacity];
    }

    private Node<E>[] grow(
            Node<E>[] oldArray) {

        Node<E>[] newArray =
                createNodeArray(
                        oldArray.length * 2);

        for (int i = 0;
             i < oldArray.length;
             i++) {

            newArray[i] = oldArray[i];
        }

        return newArray;
    }
}