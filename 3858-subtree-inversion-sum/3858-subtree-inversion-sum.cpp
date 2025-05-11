class Solution {
public:
    int k;
    vector<int> nums;
    vector<vector<int>> adj;
    vector<vector<pair<long long, long long>>> memo;

    pair<long long, long long> dfs(int node, int dist_since_inversion, int parent = -1){
        if(memo[node][dist_since_inversion].first != INT64_MIN) 
            return memo[node][dist_since_inversion];
        
        // Do not invert the current node
        long long min_sum = nums[node], max_sum = nums[node];
        for(int neighbor: adj[node]){
            if(neighbor != parent){
                auto [child_min, child_max] = dfs(neighbor, min(dist_since_inversion + 1, k), node);
                min_sum += child_min;
                max_sum += child_max;
            }
        }
        
        // Invert the current node (if distance constraint allows)
        if(dist_since_inversion >= k){
            long long min_sum_inv = -nums[node], max_sum_inv = -nums[node];
            for(int neighbor: adj[node]){
                if(neighbor != parent){
                    auto [child_min, child_max] = dfs(neighbor, 1, node);
                    min_sum_inv += -child_max; // Invert child's min/max
                    max_sum_inv += -child_min;
                }
            }
            min_sum = min(min_sum, min_sum_inv);
            max_sum = max(max_sum, max_sum_inv);
        }

        return memo[node][dist_since_inversion] = {min_sum, max_sum};
    }
    
    long long subtreeInversionSum(vector<vector<int>>& edges, vector<int>& nums, int k) {
        this -> k = k;
        this -> nums = nums;
        adj.resize(nums.size());
        memo.assign(nums.size(), vector<pair<long long, long long>>(k + 1, {INT64_MIN, INT64_MIN}));
        
        for(auto& e: edges){
            adj[e[0]].push_back(e[1]);
            adj[e[1]].push_back(e[0]);
        }
        
        return dfs(0, k).second;
    }
};