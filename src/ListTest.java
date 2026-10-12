public class ListTest {

    public static void main(String[] args) {

        //List<String> list = new ArrayList<>();
        //List<String> list = new LinkedList<>();
        List<String> list = new DoublyLinkedList<>();

        // Add items to the end.
        list.add("Alice");
        list.add("Bob");
        list.add("Charlie");

        System.out.println(list);
        // [Alice, Bob, Charlie]

        // Insert at a particular position.
        list.add(1, "Diana");

        System.out.println(list);
        // [Alice, Diana, Bob, Charlie]

        // Access items by index.
        System.out.println(list.get(0));
        // Alice

        System.out.println(list.get(2));
        // Bob

        // Replace an item.
        String oldItem = list.set(2, "Eve");

        System.out.println(oldItem);
        // Bob

        System.out.println(list);
        // [Alice, Diana, Eve, Charlie]

        // Search operations.
        System.out.println(list.contains("Diana"));
        // true

        System.out.println(list.indexOf("Charlie"));
        // 3

        System.out.println(list.indexOf("Bob"));
        // -1

        // Add a duplicate value.
        list.add("Diana");

        System.out.println(list);
        // [Alice, Diana, Eve, Charlie, Diana]

        System.out.println(list.indexOf("Diana"));
        // 1

        System.out.println(list.lastIndexOf("Diana"));
        // 4

        // Remove by index.
        String removed = list.remove(2);

        System.out.println(removed);
        // Eve

        System.out.println(list);
        // [Alice, Diana, Charlie, Diana]

        // Remove by value.
        boolean removedItem = list.remove("Diana");

        System.out.println(removedItem);
        // true

        System.out.println(list);
        // [Alice, Charlie, Diana]

        // Iterate over the list.
        for (String name : list) {
            System.out.println(name);
        }

        // Alice
        // Charlie
        // Diana

        System.out.println("Size: " + list.size());
        // Size: 3

        System.out.println("Empty: " + list.isEmpty());
        // Empty: false

        // Remove all items.
        list.clear();

        System.out.println(list);
        // []

        System.out.println("Size: " + list.size());
        // Size: 0

        System.out.println("Empty: " + list.isEmpty());
        // Empty: true
    }
}
