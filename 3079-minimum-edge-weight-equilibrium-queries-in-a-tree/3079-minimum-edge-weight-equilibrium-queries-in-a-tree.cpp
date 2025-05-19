class Solution {
public:
    vector<int> minOperationsQueries(int n, vector<vector<int>>& edges, vector<vector<int>>& queries) {
        int m = (int)log2(n) + 1, C = 27;
        vector<vector<pair<int, int>>> g(n);
        for (auto& e : edges) {
            g[e[0]].push_back(make_pair(e[1], e[2]));
            g[e[1]].push_back(make_pair(e[0], e[2]));
        }

        // fa[i][j] means the 2^i -th father of vertex j:
        vector<vector<int>> fa(m, vector<int>(n));
        // w[i][j] means the count of j from root to vertex i:
        vector<vector<int>> w(n);
        // d[i] means the depth of vertex i:
        vector<int> d(n, 0);

        function<void(int, int, int)> dfs = [&](int x, int f, int dep) -> void {
            fa[0][x] = f;
            d[x] = dep;
            for (auto& [c, weight] : g[x]) {
                if (f == c) continue;
                w[c] = w[x];
                w[c][weight]++;
                dfs(c, x, dep + 1);
            }
        };
        w[0] = vector<int>(C, 0);
        dfs(0, 0, 0);
        // binary lifting: 
        for (int i = 1; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                fa[i][j] = fa[i-1][fa[i-1][j]];
            }
        }

        auto lca = [&](int x, int y) -> int {
            if (d[x] > d[y]) swap<int>(x, y);
            // adjust x and y to the same depth:
            for (int p = 0; (1 << p) <= d[y] - d[x]; ++p) 
                if ((1 << p) & d[y] - d[x]) y = fa[p][y];
            // conservatively jump x and y together:
            for (int p = m - 1; p >= 0; --p) {
                if (fa[p][x] != fa[p][y]) {
                    x = fa[p][x];
                    y = fa[p][y];
                }
            }
            return x == y ? x : fa[0][x];
        };
        
        vector<int> res;
        for (auto &q: queries) {
            int x = q[0], y = q[1], l = lca(x, y);
            // the total length between x and y:
            int len = d[x] + d[y] - 2 * d[l];
            // the mode of weight between x and y:
            int max_z = 0;
            for (int z = 1; z < C; ++z) {
                int num_z = w[x][z] + w[y][z] - w[l][z] * 2;
                max_z = max(max_z, num_z);
            }
            // the others must be changed:
            res.push_back(len - max_z);
        }
        return res;
    }
};