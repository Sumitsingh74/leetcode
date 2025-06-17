class Solution {
    private static final int MOD = 1_000_000_007;
    private int M, K, N;
    private int[] nums;
    private long[][] comb;               // comb[n][r] = nCr % MOD
    private Map<State, Integer> memo;    // memoization cache

    public int magicalSum(int M, int K, int[] nums) {
        this.M = M;
        this.K = K;
        this.nums = nums;
        this.N = nums.length;
        
        // 1) Precompute nCr up to M via Pascal’s triangle
        comb = new long[M+1][M+1];
        for (int n = 0; n <= M; n++) {
            comb[n][0] = comb[n][n] = 1;
            for (int r = 1; r < n; r++) {
                comb[n][r] = (comb[n-1][r-1] + comb[n-1][r]) % MOD;
            }
        }

        memo = new HashMap<>();
        return dp(M, K, 0, 0);
    }

    // Recursive DP
    private int dp(int m, int k, int i, int flag) {
        if (m < 0 || k < 0 || m + Integer.bitCount(flag) < k) {
            return 0; // Prune invalid states
        }
        if (m == 0) { // Base case: no picks left
            return (k == Integer.bitCount(flag)) ? 1 : 0;
        }
        if (i >= N) return 0; // No more numbers to pick

        State st = new State(m, k, i, flag);
        if (memo.containsKey(st)) 
            return memo.get(st);

        long res = 0;
        // Try picking 'c' copies of nums[i], for c = 0..m
        for (int c = 0; c <= m; c++) {
            // ways to choose positions * nums[i]^c
            long mul = (comb[m][c] * modPow(nums[i], c)) % MOD;
            // update flag: add c to flag, then shift right by 1
            int newFlag = flag + c;
            int nextFlag = newFlag >>> 1;          // divide by 2
            int bitContribution = newFlag & 1;     // newFlag % 2

            // recurse on remaining picks and adjusted k/flag
            res = (res + mul * dp(m - c, k - bitContribution, i + 1, nextFlag)) % MOD;
        }

        int ans = (int) res;
        memo.put(st, ans);
        return ans;
    }

    // Fast modular exponentiation
    private long modPow(long base, int exp) {
        long result = 1;
        base %= MOD;
        while (exp > 0) {
            if ((exp & 1) == 1) 
                result = (result * base) % MOD;
            base = (base * base) % MOD;
            exp >>= 1;
        }
        return result;
    }

    // State key for memoization
    private static class State {
        final int m, k, i, flag;
        State(int m, int k, int i, int flag) {
            this.m = m; this.k = k; this.i = i; this.flag = flag;
        }
        @Override
        public boolean equals(Object o) {
            if (!(o instanceof State)) return false;
            State s = (State) o;
            return m == s.m && k == s.k && i == s.i && flag == s.flag;
        }
        @Override
        public int hashCode() {
            int h = m;
            h = h * 31 + k;
            h = h * 31 + i;
            h = h * 31 + flag;
            return h;
        }
    }
}