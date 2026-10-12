public interface Queue<E> extends Iterable<E> {

    void enqueue(E item);

    E dequeue();

    E peek();

    boolean isEmpty();

    int size();
}
