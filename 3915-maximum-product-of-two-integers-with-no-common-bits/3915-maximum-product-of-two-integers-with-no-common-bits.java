import java.util.*;

class Solution {
    public static long maxProduct(int[] nums) {
        int max_n = 0;
        for (int x : nums) if (x > max_n) max_n = x;
        int msb = (max_n == 0) ? -1 : 31 - Integer.numberOfLeadingZeros(max_n);
        int max_mask = (msb == -1) ? 0 : ((1 << (msb + 1)) - 1);

        int[] dp = new int[max_mask + 1];
        for (int x : nums) dp[x] = Math.max(dp[x], x);

        for (int b = 0; b <= msb; ++b) {
            int bit = 1 << b;
            for (int mask = 0; mask <= max_mask; ++mask) {
                if ((mask & bit) != 0) {
                    int prev = mask ^ bit;
                    if (dp[prev] > dp[mask]) dp[mask] = dp[prev];
                }
            }
        }

        long ans = 0;
        for (int n : nums) {
            int comp = max_mask ^ n;
            long prod = (long) n * dp[comp];
            if (prod > ans) ans = prod;
        }
        return ans;
    }
}
