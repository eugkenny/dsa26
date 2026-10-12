public class QuickFind {

    private int[] id;

    public QuickFind(int size) {
        id = new int[size];

        // Initially, every element is in its own set.
        for (int i = 0; i < size; i++) {
            id[i] = i;
        }
    }

    // Return the component containing p.
    public int find(int p) {
        return id[p];
    }

    // Join the sets containing p and q.
    public void union(int p, int q) {
        int idP = id[p];
        int idQ = id[q];

        // Change every element in p's component
        // so that it belongs to q's component.
        for (int i = 0; i < id.length; i++) {
            if (id[i] == idP) {
                id[i] = idQ;
            }
        }
    }

    // Check whether p and q belong to the same set.
    public boolean connected(int p, int q) {
        return id[p] == id[q];
    }
}
