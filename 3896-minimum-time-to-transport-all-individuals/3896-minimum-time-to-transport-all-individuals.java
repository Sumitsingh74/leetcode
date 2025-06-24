public class Solution {
    private double[][][][] dp;
    private int[] maxTime, bitCount;
    private int n, k, m;
    private int[] time;
    private double[] mul;

    public double minTime(int n, int k, int m, int[] time, double[] mul) {
        if (k == 1 && n > 1) return -1;

        // save parameters
        this.n = n; this.k = k; this.m = m;
        this.time = time; this.mul = mul;

        int fullMask = (1 << n) - 1;

        // allocate dp: [mask][state][across?0/1][singlesUsed 0..3]
        dp = new double[1 << n][m][2][4];
        maxTime  = new int[1 << n];
        bitCount = new int[1 << n];

        // precompute bit counts and maxTime for every subset
        for (int mask = 1; mask <= fullMask; mask++) {
            bitCount[mask] = bitCount[mask >> 1] + (mask & 1);
            int mx = 0;
            // find the slowest task in this subset
            for (int j = 0; j < n; j++) {
                if ((mask & (1 << j)) != 0) {
                    mx = Math.max(mx, time[j]);
                }
            }
            maxTime[mask] = mx;
        }

        // fill dp with -1 to mark “not yet computed”
        for (double[][][] a : dp)
            for (double[][] b : a)
                for (double[] c : b)
                    Arrays.fill(c, -1);

        // start the recursion
        return dfs(fullMask, 0, 0, 0);
    }

    private double dfs(int mask, int st, int across, int singles) {
        if (mask == 0) return 0;
        if (singles > 3) return Double.MAX_VALUE;

        double cached = dp[mask][st][across][singles];
        if (cached >= 0) return cached;

        double best = Double.MAX_VALUE;

        if (across == 0) {
            // pick any non‐empty submask of `mask` of size ≤ k
            for (int sub = mask; sub > 0; sub = (sub - 1) & mask) {
                int cnt = bitCount[sub];
                if (cnt > k) continue;

                double took = mul[st] * maxTime[sub];
                int nextSt = (st + (int)took) % m;
                double tail = dfs(mask ^ sub, nextSt, 1, singles + (cnt == 1 ? 1 : 0));
                if (tail != Double.MAX_VALUE) {
                    best = Math.min(best, took + tail);
                }
            }
        } else {
            // “across” phase: try doing a single task you've already completed
            for (int i = 0; i < n; i++) {
                int bit = 1 << i;
                if ((mask & bit) == 0) {
                    double took = mul[st] * time[i];
                    int nextSt = (st + (int)took) % m;
                    double tail = dfs(mask | bit, nextSt, 0, singles);
                    if (tail != Double.MAX_VALUE) {
                        best = Math.min(best, took + tail);
                    }
                }
            }
        }

        return dp[mask][st][across][singles] = best;
    }
}
