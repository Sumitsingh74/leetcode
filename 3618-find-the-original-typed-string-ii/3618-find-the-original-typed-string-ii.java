import java.util.*;

class Solution {

    private static final int MOD = 1_000_000_007;

    /** Returns (len – runs + 1)-bounded compositions whose total
        extra repeats ≥ k, mod 1e9+7.                                 */
    public static int possibleStringCount(String word, int k) {

        /* 1.  Scan the string – collect run lengths and how many runs we saw. */
        List<Integer> extra = new ArrayList<>();   // len_i − 1 for every run
        int runs = 0, n = word.length();
        for (int i = 0; i < n; ) {
            char ch = word.charAt(i);
            int j = i;
            while (j < n && word.charAt(j) == ch) ++j;
            int runLen = j - i;
            runs++;
            if (runLen > 1) extra.add(runLen - 1);
            i = j;
        }

        /* 2.  Total ways if we put no restriction on the final length. */
        long totalWays = 1;
        long totalExtra = 0;
        for (int e : extra) {
            totalWays = totalWays * (e + 1) % MOD;
            totalExtra += e;
        }

        /* 3.  How many extra characters do we still need? */
        int need = k - runs;          // ≥ 0 means we need that many extras
        if (need <= 0)            return (int) totalWays;  // already long enough
        if (need > totalExtra)    return 0;                // impossible

        int S = need - 1;         // we will count sums < need
        long[] dp = new long[S + 1];   // dp[s] = ways to get exactly s extras
        dp[0] = 1;                        // start with no runs processed

        /* 4.  Bounded-knapsack DP with a sliding window. */
        for (int cap : extra) {
            long[] ndp = new long[S + 1];
            long window = 0;              // prefix sum over last (cap+1) dp cells
            for (int s = 0; s <= S; ++s) {
                window = (window + dp[s]) % MOD;
                if (s - cap - 1 >= 0)        // drop term that slid past the window
                    window = (window - dp[s - cap - 1] + MOD) % MOD;
                ndp[s] = window;             // ways with current run considered
            }
            dp = ndp;
        }

        /* 5.  Ways whose total extra < need  =  Σ dp[s], 0 ≤ s < need */
        long less = 0;
        for (long v : dp) less = (less + v) % MOD;

        /* 6.  Desired answer = totalWays − less (mod M). */
        return (int) ((totalWays - less + MOD) % MOD);
    }
}
