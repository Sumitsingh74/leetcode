class Solution {
public:
   int dfs(int i, int p, vector<vector<int>> &al, int k) {
    if (k <= 0)
        return k == 0;
    int res = 1;
    for (int j : al[i])
        if (j != p)
            res += dfs(j, i, al, k - 1);
    return res;
}
vector<vector<int>> adjacencyList(vector<vector<int>>& edges) {
    vector<vector<int>> al(edges.size() + 1);
    for (auto &e: edges) {
        al[e[0]].push_back(e[1]);
        al[e[1]].push_back(e[0]);            
    }        
    return al;
}
vector<int> maxTargetNodes(vector<vector<int>>& e1, vector<vector<int>>& e2, int k) {
    int m = e1.size() + 1, n = e2.size() + 1, max2 = 0;
    auto al1 = adjacencyList(e1), al2 = adjacencyList(e2);
    vector<int> res(m);
    for (int i = 0; i < n; ++i)
        max2 = max(max2, dfs(i, -1, al2, k - 1));
    for (int i = 0; i < m; ++i)
        res[i] = max2 + dfs(i, -1, al1, k);        
    return res;
}

};