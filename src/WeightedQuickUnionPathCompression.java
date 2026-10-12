public class WeightedQuickUnionPathCompression {

    private int[] parent;
    private int[] size;

    public WeightedQuickUnionPathCompression(int numberOfElements) {
        parent = new int[numberOfElements];
        size = new int[numberOfElements];

        for (int i = 0; i < numberOfElements; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    // Find the root of the set containing p.
    // Compress the path while searching.
    public int find(int p) {
        while (p != parent[p]) {
            parent[p] = parent[parent[p]];
            p = parent[p];
        }

        return p;
    }

    // Join the sets containing p and q.
    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);

        // Already in the same set.
        if (rootP == rootQ) {
            return;
        }

        // Attach the smaller tree to the larger tree.
        if (size[rootP] < size[rootQ]) {
            parent[rootP] = rootQ;
            size[rootQ] += size[rootP];
        } else {
            parent[rootQ] = rootP;
            size[rootP] += size[rootQ];
        }
    }

    // Check whether p and q belong to the same set.
    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }
}
