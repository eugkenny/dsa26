public class QuickUnion {

    private int[] parent;

    public QuickUnion(int size) {
        parent = new int[size];

        // Initially, every element is in its own set.
        for (int i = 0; i < size; i++) {
            parent[i] = i;
        }
    }

    // Find the root of the set containing p.
    public int find(int p) {
        while (p != parent[p]) {
            p = parent[p];
        }

        return p;
    }

    // Join the sets containing p and q.
    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);

        parent[rootP] = rootQ;
    }

    // Check whether p and q belong to the same set.
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }
}
