class Solution {
        public int climbStairs(int n, int[] costs) {
        int[] dp = new int[3];
        for (int i = 1; i <= n; i++) {
            int best = Integer.MAX_VALUE;
            if (i >= 1) best = Math.min(best, dp[(i - 1) % 3] + 1);
            if (i >= 2) best = Math.min(best, dp[(i - 2) % 3] + 4);
            if (i >= 3) best = Math.min(best, dp[(i - 3) % 3] + 9);
            dp[i % 3] = best + costs[i - 1];
        }
        return dp[n % 3];
    }
}