class Solution:
    def minimumIncrements(self, nums, target):
        m = len(target)
        full_mask = (1 << m) - 1  # All subsets represented as bitmasks
        
        # Precompute LCM for each subset of `target`
        lcm_arr = [0] * (1 << m)
        for mask in range(1, 1 << m):
            L = 1
            for j in range(m):
                if mask & (1 << j):
                    L = self.lcm(L, target[j])
            lcm_arr[mask] = L
        
        INF = float('inf')
        dp = [INF] * (1 << m)
        dp[0] = 0  # Base case: No elements selected, so cost is 0
        
        for x in nums:
            new_dp = dp[:]
            
            for mask in range(1, 1 << m):
                L = lcm_arr[mask]
                r = x % L
                cost = 0 if r == 0 else L - r  # Adjust x to be a multiple of L
                
                # Update DP array
                for old in range(1 << m):
                    new_mask = old | mask  # Merge current subset with previous subsets
                    new_dp[new_mask] = min(new_dp[new_mask], dp[old] + cost)
            
            dp = new_dp
        
        return int(dp[full_mask])  # Minimum cost to satisfy all LCM conditions

    def lcm(self, a, b):
        return a * b // gcd(a, b)