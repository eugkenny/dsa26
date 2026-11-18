public interface PriorityQueue<E extends Comparable<E>> {

    // Add an item to the priority queue.
    void add(E item);

    // Return the highest-priority item
    // without removing it.
    E peek();

    // Remove and return the highest-priority item.
    E remove();

    // Return true if the queue contains no items.
    boolean isEmpty();

    // Return the number of items currently stored.
    int size();
}