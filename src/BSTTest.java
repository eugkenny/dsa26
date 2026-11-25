import java.util.Iterator;

public class BSTTest {

    public static void main(String[] args) {
        testSet();
        System.out.println();
        testMap();
    }

    private static void testSet() {
        System.out.println("=== BinarySearchTree ===");

        BinarySearchTreeSet<Integer> tree = new BinarySearchTreeSet<>();
        int[] values = {8, 3, 10, 1, 6, 14, 4, 7, 13};

        for (int value : values) {
            tree.add(value);
        }

        System.out.println("Size: " + tree.size());
        System.out.print("Inorder:    ");
        for (int value : tree) {
            System.out.print(value + " ");
        }
        System.out.println();

        System.out.print("Preorder:   ");
        print(tree.iterator(TraversalOrder.PRE_ORDER));

        System.out.print("Postorder:  ");
        print(tree.iterator(TraversalOrder.POST_ORDER));

        System.out.print("Levelorder: ");
        print(tree.iterator(TraversalOrder.LEVEL_ORDER));

        System.out.println("Contains 6: " + tree.contains(6));

        System.out.println("Contains 12: " + tree.contains(12));

        System.out.println("Min: " + tree.min());
        System.out.println("Max: " + tree.max());

        tree.remove(3);

        System.out.print("After removing 3: ");
        for (int value : tree) {
            System.out.print(value + " ");
        }
        System.out.println();
    }

    private static void testMap() {

        System.out.println("=== BinarySearchTreeMap ===");

        BinarySearchTreeMap<Integer, String> map = new BinarySearchTreeMap<>();

        map.put(8, "eight");
        map.put(3, "three");
        map.put(10, "ten");
        map.put(1, "one");
        map.put(6, "six");
        map.put(14, "fourteen");
        map.put(4, "four");
        map.put(7, "seven");
        map.put(13, "thirteen");

        System.out.println("Size: " + map.size());

        System.out.println("Value for 6: " + map.get(6));

        System.out.println("Contains key 12: " + map.containsKey(12));

        System.out.print("Keys in order: ");

        for (int key : map) {
            System.out.print(key + " ");
        }
        System.out.println();

        System.out.println("Min key: " + map.minKey());

        System.out.println("Max key: " + map.maxKey());

        System.out.println("Floor of 5: " + map.floor(5));

        System.out.println("Ceiling of 5: " + map.ceiling(5));

        System.out.println("Rank of 7: " + map.rank(7));

        System.out.println("Select rank 4: " + map.select(4));

        System.out.println("Replace key 6: " + map.put(6, "SIX"));

        System.out.println("New value for 6: " + map.get(6));

        System.out.println("Remove key 10: " + map.remove(10));

        System.out.print("After removal: ");

        for (int key : map) {
            System.out.print(key + " ");
        }
        System.out.println();
    }

    private static <E> void print(Iterator<E> iterator) {
        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }

        System.out.println();
    }
}