class Solution {
        public int deleteString(String s) {
        int n = s.length();
        int[][] lcs = new int[n + 1][n + 1];
        int[] dp = new int[n];
        for (int i = n - 1; i >= 0; --i) {
            lcs[i] = new int[n + 1];
            dp[i] = 1;
            for (int j = i + 1; j < n; ++j) {
                if (s.charAt(i) == s.charAt(j))
                    lcs[i][j] = lcs[i + 1][j + 1] + 1;
                if (lcs[i][j] >= j - i)
                    dp[i] = Math.max(dp[i], dp[j] + 1);
            }
        }
        return dp[0];
    }
}