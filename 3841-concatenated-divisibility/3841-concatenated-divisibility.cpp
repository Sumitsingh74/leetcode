class Solution {
public:
    vector<int> concatenatedDivisibility(vector<int>& nums, int k) {
        vector<long long> pow10(nums.size());
        
        for (int i = 0; i < nums.size(); ++i) {
            long long p = 1;
            int len = to_string(nums[i]).length();
            for (int j = 0; j < len; ++j) 
                p = (p * 10) % k;
            
            pow10[i] = p;
        }

        vector<unordered_map<int,vector<int>>> dp(1 << nums.size());
        dp[0][0] = {};

        for (int mask = 0; mask < (1 << nums.size()); ++mask) {
            for (auto&[rem, seq] : dp[mask]) {
                for (int i = 0; i < nums.size(); ++i) {
                    if (!(mask & (1 << i))) {
                        int nm = mask | (1 << i);
                        int nr = (rem * pow10[i] + nums[i]) % k;
                        vector<int> temp = seq;
                        temp.push_back(nums[i]);
                        if (dp[nm].find(nr) == dp[nm].end() || temp < dp[nm][nr]) 
                            dp[nm][nr] = temp;
                    }
                }
            }
        }
        int all = (1 << nums.size()) - 1;
        return dp[all].count(0) ? dp[all][0] : vector<int>{};
    }
};