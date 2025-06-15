class Solution {
    private static class Edge {
        int to;
        long w;
        Edge(int _to, long _w) { to = _to; w = _w; }
    }

    int n, LOG;
    List<Edge>[] adj;
    int[] depth, parent0;
    long[] dist;              // dist[v] = total weight from root (0) to v
    long[] weightToPar;       // weight of edge (v -> parent0[v])
    int[][] up;               // up[k][v] = 2^k-th ancestor of v
    long[][] weightUp;        // weightUp[k][v] = sum of weights from v up to up[k][v]

    public int[] findMedian(int n, int[][] edges, int[][] queries) {
        this.n = n;
        // log₂(n) rounded up
        LOG = 1;
        while ((1<<LOG) <= n) LOG++;

        // build adjacency
        adj = new ArrayList[n];
        for (int i = 0; i < n; i++) adj[i] = new ArrayList<>();
        for (int[] e : edges) {
            int u = e[0], v = e[1];
            long w = e[2];
            adj[u].add(new Edge(v, w));
            adj[v].add(new Edge(u, w));
        }

        // init arrays
        depth      = new int[n];
        parent0    = new int[n];
        dist       = new long[n];
        weightToPar= new long[n];
        up         = new int[LOG][n];
        weightUp   = new long[LOG][n];

        // dfs from root=0
        parent0[0] = -1;
        depth[0]   = 0;
        dist[0]    = 0;
        weightToPar[0] = 0;
        dfs(0, -1);

        // build binary-lifting tables
        for (int v = 0; v < n; v++) {
            up[0][v]      = parent0[v] < 0 ? -1 : parent0[v];
            weightUp[0][v]= weightToPar[v];
        }
        for (int k = 1; k < LOG; k++) {
            for (int v = 0; v < n; v++) {
                int mid = up[k-1][v];
                if (mid < 0) {
                    up[k][v] = -1;
                    weightUp[k][v] = weightUp[k-1][v];
                } else {
                    up[k][v] = up[k-1][mid];
                    weightUp[k][v] = weightUp[k-1][v] + weightUp[k-1][mid];
                }
            }
        }

        // answer each query in O(log n)
        int Q = queries.length;
        int[] ans = new int[Q];
        for (int i = 0; i < Q; i++) {
            int u = queries[i][0], v = queries[i][1];
            ans[i] = medianOnPath(u, v);
        }
        return ans;
    }

    // standard DFS to set parent0, depth, dist, weightToPar
    private void dfs(int u, int p) {
        for (Edge e : adj[u]) {
            int v = e.to;
            if (v == p) continue;
            parent0[v]     = u;
            depth[v]       = depth[u] + 1;
            dist[v]        = dist[u] + e.w;
            weightToPar[v] = e.w;
            dfs(v, u);
        }
    }

    // lift u up by exactly 'd' levels
    private int liftNode(int u, int d) {
        for (int k = 0; k < LOG && u != -1; k++) {
            if ((d & (1<<k)) != 0) {
                u = up[k][u];
            }
        }
        return u;
    }

    // lowest common ancestor of u,v
    private int lca(int u, int v) {
        if (depth[u] < depth[v]) {
            int tmp = u; u = v; v = tmp;
        }
        u = liftNode(u, depth[u] - depth[v]);
        if (u == v) return u;
        for (int k = LOG-1; k >= 0; k--) {
            if (up[k][u] != up[k][v]) {
                u = up[k][u];
                v = up[k][v];
            }
        }
        return parent0[u];
    }

    // climb from u upward until we've taken >= W total weight;
    // returns the first node X where sum(edge-weights from u→X) ≥ W
    private int climbByWeight(int u, long W) {
        if (W <= 0) return u;
        long sum = 0;
        int cur = u;
        for (int k = LOG-1; k >= 0; k--) {
            if (up[k][cur] != -1 && sum + weightUp[k][cur] < W) {
                sum += weightUp[k][cur];
                cur = up[k][cur];
            }
        }
        // one more step gets us ≥ W
        return parent0[cur];
    }

    // main routine for one query
    private int medianOnPath(int u, int v) {
        int w = lca(u, v);
        long du = dist[u] - dist[w];
        long dv = dist[v] - dist[w];
        long total = du + dv;
        // integer threshold = ceil(total/2)
        long T = (total + 1) / 2;

        if (du >= T) {
            // median lies on the u→lca segment
            return climbByWeight(u, T);
        } else {
            // median lies on the lca→v segment
            long down = T - du;               // weight to go downward from lca
            long targetDist = dist[w] + down; 
            // climb v upward to the highest ancestor whose dist ≥ targetDist
            int cur = v;
            for (int k = LOG-1; k >= 0; k--) {
                int anc = up[k][cur];
                if (anc != -1 && dist[anc] >= targetDist) {
                    cur = anc;
                }
            }
            return cur;
        }
    }
}
