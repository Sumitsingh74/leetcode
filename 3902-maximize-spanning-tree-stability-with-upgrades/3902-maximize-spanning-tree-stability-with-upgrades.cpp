class Solution {
public:
    int find(int i, vector<int> &ds) {
        return ds[i] < 0 ? i : ds[i] = find(ds[i], ds);
    }
    int maxStability(int n, vector<vector<int>>& edges, int k) {
        vector<array<int, 3>> s_u_v;
        vector<int> ds(n, -1);
        int used_e = 0, res = 200000, min_single = 200000, min_double = 100000;
        for (const auto &e : edges) {
            if (e[3]) {
                if (int a = find(e[0], ds), b = find(e[1], ds); a == b)
                    return -1;
                else {
                    ++used_e;
                    ds[b] = a;
                    res = min(res, e[2]);                    
                }
            }
            else
                s_u_v.push_back({e[2], e[1], e[0]});
        }
        sort(begin(s_u_v), end(s_u_v), greater<>());
        for (auto &[s, u, v] : s_u_v)                 
            if (int a = find(u, ds), b = find(v, ds); a != b) {
                ds[b] = a;
                if (++used_e == n - 1 - k)
                    min_single = s;
                min_double = s;
            }
        return used_e == n - 1 ? min({res, min_single, min_double * (k ? 2 : 1)}) : -1;
    }
};