class Solution {
    long long MOD = 1000000007;
    vector<vector<int>> dp;
public:
    int countHousePlacements(int n) {
        dp.resize(n + 1, vector<int> (2, -1));
        long long ways_for_one_side = (Memoization(n, 0) + Memoization(n, 1)) % MOD;
        
        return (ways_for_one_side * ways_for_one_side) % MOD;
    }
    
    long long Memoization(int n, bool is_filled)
    {
        if (n == 1)
            return 1;
        
        if (dp[n][is_filled] != -1)
            return dp[n][is_filled];
        
        if (is_filled)
            return dp[n][1] = Memoization(n - 1, !is_filled);
        else
            return dp[n][0] = (Memoization(n - 1, !is_filled) + Memoization(n - 1, is_filled)) % MOD;
    }
};