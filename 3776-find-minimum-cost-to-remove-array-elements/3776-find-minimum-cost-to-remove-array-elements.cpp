class Solution {
public:
    int cal(int ext, int ind, vector<int>& nums, vector<vector<int>> &dp){
        // Single element left
        if(ind == nums.size()) return nums[ext];

        // 2 Elements left
        if(ind == nums.size()-1) return max(nums[ext], nums[ind]);

        if(dp[ind][ext] != -1) return dp[ind][ext];

        // Trying all 3 cases of choosing 2 elements
        int f = max(nums[ind], nums[ind+1]) + cal(ext, ind+2, nums, dp);
        int s = max(nums[ext], nums[ind+1]) + cal(ind, ind+2, nums, dp);
        int t = max(nums[ext], nums[ind]) + cal(ind+1, ind+2, nums, dp);

        //Returning min among the 3
        return dp[ind][ext] = min({f, s, t});
    }

    int minCost(vector<int>& nums) {
        int n = nums.size();

        // Memoization Table
        vector<vector<int>> dp(n+1, vector<int>(n+1, -1));
        return cal(0, 1, nums, dp);
    }
};