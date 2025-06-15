int n, l;
vector<vector<array<int, 2>>> adj;

int timer;
vector<int> tin, tout;
vector<vector<int>> up;

void dfs(int v, int p)
{
    tin[v] = ++timer;
    up[v][0] = p;
    for (int i = 1; i <= l; ++i)
        up[v][i] = up[up[v][i-1]][i-1];

    for (auto& [u, w] : adj[v]) {
        if (u != p)
            dfs(u, v);
    }

    tout[v] = ++timer;
}

bool is_ancestor(int u, int v)
{
    return tin[u] <= tin[v] && tout[u] >= tout[v];
}

int lca(int u, int v)
{
    if (is_ancestor(u, v))
        return u;
    if (is_ancestor(v, u))
        return v;
    for (int i = l; i >= 0; --i) {
        if (!is_ancestor(up[u][i], v))
            u = up[u][i];
    }
    return up[u][0];
}

void preprocess(int root) {
    tin.resize(n);
    tout.resize(n);
    timer = 0;
    l = ceil(log2(n));
    up.assign(n, vector<int>(l + 1));
    dfs(root, root);
}
                
class Solution {
public:
    vector<int> findMedian(int _n, vector<vector<int>>& edges, vector<vector<int>>& queries) {
        n = _n;
        adj.clear();
        adj.resize(n);
        for(auto& edge : edges) {
            adj[edge[0]].push_back({edge[1], edge[2]});
            adj[edge[1]].push_back({edge[0], edge[2]});
        }
        preprocess(0);
        vector<long long> rootWeightDist(n), rootDist(n);
        [&](this auto&& go, int v, int p, long long cur, int d) -> void {
            rootWeightDist[v] = cur, rootDist[v] = d;
            for(auto& [ce, w] : adj[v]) {
                if(ce == p) continue;
                go(ce, v, cur + w, d + 1);
            }
        }(0, -1, 0, 0);

        auto pathSum = [&](int u, int v, int ancestor) -> long long {
            return rootWeightDist[u] + rootWeightDist[v] - 2 * rootWeightDist[ancestor];
        };

        int qSz = queries.size();
        vector<int> res(qSz);
        for(int i = 0; i < qSz; i++) {
            int u = queries[i][0], v = queries[i][1];
            int orU = u, orV = v;

            if(u == v) {
                res[i] = u;
                continue;
            }
            int ancestor = lca(u, v);
            long long median = (pathSum(u, v, ancestor) + 1) / 2;
            if(pathSum(u, ancestor, ancestor) >= median) {
                // somewhere between (u, ancestor) [go up from u to ancestor]
                for(int p = 0;; p++) {
                    int uUp = up[u][p];
                    if(pathSum(orU, uUp, uUp) >= median) {
                        // if this is over median, we need to backtrack -1 then go again from p = 0
                        // this ensures we get the exact front node
                        // if p is already 0, then `uUp` is the best node
                        if(p == 0) {
                            res[i] = uUp;
                            break;
                        }else {
                            u = up[u][p - 1];
                            p = -1;
                        }
                    }
                }
            }else {
                // somewhere between (v, ancestor) [go down from ancestor to v]
                // offset is path from (u, ancestor) which we're not accounting for
                long long offset = pathSum(u, ancestor, ancestor);
                for(int p = 0;; p++) {
                    int vUp = up[v][p];
                    // this time, we're going down from ancestor to v
                    // but we only have up[] so still go up
                    // once it goes below median, then `vUp` is a bad node
                    // so backtrack -1 then go again from p = 0
                    // if p = 0 then `v` is the best node and `vUp` is the "least bad node".
                    if(pathSum(ancestor, vUp, ancestor) + offset < median) {
                        if(p == 0) {
                            break;
                        }else {
                            v = up[v][p - 1];
                            p = -1;
                        }
                    }
                }
                res[i] = v;
            }
        }
        return res;
    }
};