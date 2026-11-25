public interface Set<E> extends Iterable<E> {

    boolean add(E item);

    boolean contains(E item);

    boolean remove(E item);

    boolean isEmpty();

    int size();

    void clear();
}