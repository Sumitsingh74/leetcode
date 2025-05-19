class Solution {
    public void dfs(int x, int f, int dep, List<Pair<Integer, Integer>> []g, int [][]fa, int [][]w, int []d) {
        fa[0][x] = f;
        d[x] = dep;
        for (Pair<Integer, Integer> p: g[x]) {
            //Pair<Integer, Integer> p = g[x].get(i);
            int c = p.getKey().intValue(), weight = p.getValue().intValue();
            if (f == c) continue;
            for (int j = 0; j < w[c].length; ++j)
                w[c][j] = w[x][j];
            w[c][weight]++;
            dfs(c, x, dep + 1, g, fa, w, d);
        }
    }
    public int lca(int x, int y, int [][]fa, int []d) {
        int m = fa.length;
        if (d[x] > d[y]) {
            int t = x;
            x = y;
            y = t;
        }
        // adjust x and y to the same depth:
        for (int p = 0; (1 << p) <= d[y] - d[x]; ++p) {
            if (((1 << p) & d[y] - d[x]) != 0) {
                y = fa[p][y];
            }
        }
        // conservatively jump x and y together:
        for (int p = m - 1; p >= 0; --p) {
            if (fa[p][x] != fa[p][y]) {
                x = fa[p][x];
                y = fa[p][y];
            }
        }
        return x == y ? x : fa[0][x];
    }
    public int[] minOperationsQueries(int n, int[][] edges, int[][] queries) {
        int m = 15, C = 27;
        List<Pair<Integer, Integer>> []g = new ArrayList[n];
        for (int i = 0; i < n; ++i)
            g[i] = new ArrayList<Pair<Integer, Integer>>();
        for (int[] e : edges) {
            g[e[0]].add(new Pair<Integer, Integer>(e[1], e[2]));
            g[e[1]].add(new Pair<Integer, Integer>(e[0], e[2]));
        }

        // fa[i][j] means the 2^i -th father of vertex j:
        int [][]fa = new int[m][n];
        // w[i][j] means the count of j from root to vertex i:
        int [][]w = new int[n][C];
        // d[i] means the depth of vertex i:
        int []d = new int[n];
        dfs(0, 0, 0, g, fa, w, d);
        // binary lifting: 
        for (int i = 1; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                fa[i][j] = fa[i-1][fa[i-1][j]];
            }
        }
        
        int []res = new int[queries.length];
        for (int i = 0; i < queries.length; ++i) {
            int x = queries[i][0], y = queries[i][1], l = lca(x, y, fa, d);
            // the total length between x and y:
            int len = d[x] + d[y] - 2 * d[l];
            // the mode of weight between x and y:
            int max_z = 0;
            for (int z = 1; z < C; ++z) {
                int num_z = w[x][z] + w[y][z] - w[l][z] * 2;
                max_z = Math.max(max_z, num_z);
            }
            // the others must be changed:
            res[i] = len - max_z;
        }
        return res;
    }
}