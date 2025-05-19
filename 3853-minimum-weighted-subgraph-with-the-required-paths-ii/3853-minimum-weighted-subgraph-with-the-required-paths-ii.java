class Solution {
    static final int LOG = 17;
    List<int[]>[] graph;
    int[][] parent;
    int[] depth;
    int[] weightFromRoot;

    public int[] minimumWeight(int[][] edges, int[][] queries) {
        int n = 0;
        for (int[] e : edges) n = Math.max(n, Math.max(e[0], e[1]) + 1);
        for (int[] q : queries) n = Math.max(n, Math.max(q[0], Math.max(q[1], q[2])) + 1);

        graph = new ArrayList[n];
        for (int i = 0; i < n; i++) graph[i] = new ArrayList<>();
        for (int[] e : edges) {
            graph[e[0]].add(new int[]{e[1], e[2]});
            graph[e[1]].add(new int[]{e[0], e[2]});
        }

        parent = new int[n][LOG];
        depth = new int[n];
        weightFromRoot = new int[n];
        dfs(0, -1, 0, 0);

        for (int j = 1; j < LOG; j++) {
            for (int i = 0; i < n; i++) {
                if (parent[i][j - 1] != -1)
                    parent[i][j] = parent[parent[i][j - 1]][j - 1];
                else
                    parent[i][j] = -1;
            }
        }

        int[] res = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int a = queries[i][0], b = queries[i][1], c = queries[i][2];
            int ab = dist(a, b), ac = dist(a, c), bc = dist(b, c);
            res[i] = (ab + ac + bc) / 2; 
        }
        return res;
    }

    void dfs(int node, int par, int d, int accWeight) {
        parent[node][0] = par;
        depth[node] = d;
        weightFromRoot[node] = accWeight;
        for (int[] nei : graph[node]) {
            int next = nei[0], w = nei[1];
            if (next != par) {
                dfs(next, node, d + 1, accWeight + w);
            }
        }
    }

    int lca(int u, int v) {
        if (depth[u] < depth[v]) {
            int tmp = u; u = v; v = tmp;
        }
        for (int i = LOG - 1; i >= 0; i--) {
            if (parent[u][i] != -1 && depth[parent[u][i]] >= depth[v])
                u = parent[u][i];
        }
        if (u == v) return u;
        for (int i = LOG - 1; i >= 0; i--) {
            if (parent[u][i] != -1 && parent[u][i] != parent[v][i]) {
                u = parent[u][i];
                v = parent[v][i];
            }
        }
        return parent[u][0];
    }

    int dist(int u, int v) {
        int lcaNode = lca(u, v);
        return weightFromRoot[u] + weightFromRoot[v] - 2 * weightFromRoot[lcaNode];
    }
}