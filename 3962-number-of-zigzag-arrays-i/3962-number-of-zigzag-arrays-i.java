class Solution {
       public int zigZagArrays(int n, int l, int r) {
        int MOD = 1_000_000_007;
        r -= l;
        int[] dp = new int[r + 1];
        Arrays.fill(dp, 1);
        for (long i = 1; i < n; i++) {
            long pre = 0L;
            if ((i & 1) == 1) {
                for (int v = 0; v <= r; v++) {
                    long pre2 = pre + dp[v];
                    dp[v] = (int)(pre % MOD);
                    pre = pre2 % MOD;
                }
            } else {
                for (int v = r; v >= 0; v--) {
                    long pre2 = pre + dp[v];
                    dp[v] = (int)(pre % MOD);
                    pre = pre2 % MOD;
                }
            }
        }
        long res = 0;
        for (int v : dp)
            res += v;
        return (int)((res % MOD) * 2 % MOD);
    }
}