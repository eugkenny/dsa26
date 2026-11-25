public interface Map<K, V> extends Iterable<K> {

    V put(K key, V value);

    V get(K key);

    boolean containsKey(K key);

    V remove(K key);

    boolean isEmpty();

    int size();

    void clear();
}