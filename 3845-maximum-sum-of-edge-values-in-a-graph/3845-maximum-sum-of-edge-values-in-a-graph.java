import java.util.*;

class Solution {
    long ans = 0;

    private List<List<Integer>> create(int[][] edges, int n) {
        List<List<Integer>> g = new ArrayList<>();
        for (int i = 0; i < n; i++)
            g.add(new ArrayList<>());
        for (int[] e : edges) {
            g.get(e[0]).add(e[1]);
            g.get(e[1]).add(e[0]);
        }
        return g;
    }

    private int[] f(List<List<Integer>> g, int curr, int[] vis, int pa) {
        vis[curr] = 1;
        int[] result = new int[] { 0, 1 }; // [hasCycle, size]
        for (int a : g.get(curr)) {
            if (a == pa)
                continue;
            if (vis[a] == 1) {
                result[0] = 1;
            } else {
                int[] child = f(g, a, vis, curr);
                result[0] |= child[0];
                result[1] += child[1];
            }
        }
        return result;
    }

    public long maxScore(int n, int[][] edges) {
        List<List<Integer>> g = create(edges, n);
        List<int[]> cycles = new ArrayList<>();
        List<int[]> trees = new ArrayList<>();
        int[] vis = new int[n];

        for (int i = 0; i < n; i++) {
            if (vis[i] == 0) {
                int[] result = f(g, i, vis, -1);
                if (result[0] == 1)
                    cycles.add(new int[] { i, result[1] });
                else
                    trees.add(new int[] { i, result[1] });
            }
        }

        Collections.sort(cycles,(a,b)->Integer.compare(b[1],a[1]));
         Collections.sort(trees,(a,b)->Integer.compare(b[1],a[1]));
        long currentValue = n;
        int[] values = new int[n];

        for (int[] arr : cycles) {
            assignCycle(g, arr[0], currentValue, values);
            currentValue -= arr[1];
        }

        for (int[] arr : trees) {
            Arrays.fill(vis, 0);
            int leaf = findLeaf(g, arr[0], vis);
            Arrays.fill(vis, 0);
            int[] temp = assignTree(g, leaf, currentValue, vis, values, new int[] { 1 });
            currentValue = temp[0];
        }
    //  System.out.println(Arrays.toString(values));
        // After all values are assigned
        for (int[] edge : edges) {
            ans += 1L * values[edge[0]] * values[edge[1]];
        }

        return ans;
    }

    private int findLeaf(List<List<Integer>> g, int curr, int[] vis) {
        vis[curr] = 1;
        if (g.get(curr).size() <= 1)
            return curr;
        for (int a : g.get(curr)) {
            if (vis[a] == 0) {
                int leaf = findLeaf(g, a, vis);
                if (leaf != -1)
                    return leaf;
            }
        }
        return -1;
    }

    private int[] assignTree(List<List<Integer>> g, int curr, long currentValue, int[] vis, int[] values, int[] depth) {
        vis[curr] = 1;
        List<Integer> adj = g.get(curr);
        boolean isLeaf = true;
        int[] val = null;
        for (int a : adj) {
            if (vis[a] == 1)
                continue;
            isLeaf = false;
            depth[0]++;
            val = assignTree(g, a, currentValue, vis, values, depth);
            depth[0]--;
        }
        if (isLeaf) {
            values[curr] = (int) currentValue - depth[0] + 1;
            if (values[curr] == currentValue) {
                return new int[] { (int) values[curr] - 1, 0 };
            }

            return new int[] { (int) values[curr] + 2, 2 };
        }
        if (val[0] == currentValue || val[0] == currentValue + 1) {
            values[curr] = (int) currentValue;
            if (val[0] == currentValue + 1) {
                return new int[] { (int) currentValue - 2, -2 };
            }
            return new int[] { (int) currentValue - 1, -2 };
        }

        values[curr] = val[0];
        return new int[] { val[0] + val[1], val[1] };
    }

    private void assignCycle(List<List<Integer>> g, int start, long currentValue, int[] values) {
        Queue<Integer> q = new LinkedList<>();
        int[] vis = new int[g.size()];
        q.add(start);
        vis[start] = 1;
        values[start] = (int) currentValue--;
        while (!q.isEmpty()) {
            int u = q.poll();
            for (int v : g.get(u)) {
                if (vis[v] == 0) {
                    vis[v] = 1;
                    values[v] = (int) currentValue--;
                    q.add(v);
                }
            }
        }
    }
}