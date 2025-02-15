class Solution {
    int mod = 1e9 + 7;

public:
    int totalSubsets(vector<int>& nums, int n, int target, vector<vector<int>>& dp) {
        // Base cases
        if (target == 0) return 1; // Empty subset is valid
        if (n == 0) return 0; // No elements left

        // Check if the result is already computed
        if (dp[n][target] != -1) return dp[n][target];

        // Skip the current element
        int skip = totalSubsets(nums, n - 1, target, dp);

        // Take the current element (if it is <= target)
        int take = 0;
        if (nums[n - 1] <= target) {
            take = totalSubsets(nums, n - 1, target - nums[n - 1], dp);
        }

        // Store the result in the DP table
        dp[n][target] = (take + skip) % mod;
        return dp[n][target];
    }

    int countPartitions(vector<int>& nums, int k) {
        int n = nums.size();
        int totalSum = 0;

        // Calculate the total sum of the array
        for (int i = 0; i < n; i++) {
            totalSum = (totalSum + nums[i]) % mod;
        }

        // Rule out invalid cases
        if (totalSum < 2 * k) return 0;

        // Calculate the total number of subsets (2^n)
        int totalSubsetCount = 1;
        for (int i = 1; i <= n; i++) {
            totalSubsetCount = (totalSubsetCount * 2) % mod;
        }

        // Initialize the DP table
        vector<vector<int>> dp(n + 1, vector<int>(k, -1));

        // Calculate the number of invalid subsets (sum < k)
        int invalidSubsetCount = 0;
        for (int i = 0; i < k; i++) {
            invalidSubsetCount = (invalidSubsetCount + totalSubsets(nums, n, i, dp)) % mod;
        }

        // Calculate the number of great partitions
        int greatPartition = (totalSubsetCount - 2 * invalidSubsetCount + mod) % mod;
        return greatPartition;
    }
};