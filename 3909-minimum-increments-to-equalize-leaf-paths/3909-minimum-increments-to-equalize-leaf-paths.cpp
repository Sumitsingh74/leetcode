class Solution {
    vector<vector<int>> adj;
    int result = 0;
    // DFS
    long long dfs(int cur, int parent, vector<int>& costs){
        // post order
        vector<long long> child;
        for(auto next : adj[cur]){
            if(next != parent){
                child.push_back(dfs(next, cur, costs));
            }
        }
        long long maxChild = 0;
        if(child.size() > 0){
            maxChild = *max_element(child.begin(), child.end());
            for(auto val : child){
                if(val != maxChild){ // need to add subtree root value
                    ++result; 
                }
            }
        }
        return maxChild + costs[cur];
    }
public:
    int minIncrease(int n, vector<vector<int>>& edges, vector<int>& costs) {
        // create graph
        adj.resize(n);
        for(auto& edge : edges){
            adj[edge[0]].push_back(edge[1]);
            adj[edge[1]].push_back(edge[0]);
        }
        // run DFS for each subtree node, it cost need to be the same
        dfs(0, -1, costs);
        return result;
    }
};