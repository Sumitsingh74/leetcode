class Solution {
public:
       vector<bool> pathExistenceQueries(int n, vector<int>& A, int maxDiff, vector<vector<int>>& queries) {
        vector<int> g(n, 0);
        for (int i = 1; i < n; ++i) {
            g[i] = g[i - 1] + ((A[i] - A[i - 1]) > maxDiff ? 1 : 0);
        }
        
        vector<bool> res;
        for (const auto& q : queries) {
            int i = q[0], j = q[1];
            res.push_back(g[i] == g[j]);
        }
        return res;
    }
};