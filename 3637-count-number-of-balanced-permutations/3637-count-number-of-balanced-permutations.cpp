class Solution {
    static const int MOD = 1e9 + 7;
    using ll = long long;
    vector<ll> fact, inv, invfact;

    void precomp(int n) {
        fact.assign(n + 1, 1);
        for (int i = 1; i <= n; i++)
            fact[i] = fact[i - 1] * i % MOD;

        inv.assign(n + 1, 1);
        for (int i = 2; i <= n; i++)
            inv[i] = MOD - (MOD/ i) * inv[MOD% i] % MOD;

        invfact.assign(n + 1, 1);
        for (int i = 1; i <= n; i++)
            invfact[i] = invfact[i - 1] * inv[i] % MOD;
    }

public:
        int countBalancedPermutations(string num) {
        int n = num.size();
        int freq[10] = {};
        int total = 0;
        for (char ch : num) {
            int d = ch - '0';
            freq[d]++;
            total += d;
        }
        if (total & 1) return 0;
        int half = total / 2;
        int even = (n + 1) / 2;
        int odd  = n / 2;
        precomp(100000);
        static long long dp[41][361], newdp[41][361];
        for (int i = 0; i <= even; i++)
            for (int s = 0; s <= half; s++)
                dp[i][s] = 0;
        dp[0][0] = 1;
        for (int d = 0; d < 10; d++) {
            int c = freq[d];
            if (c == 0) continue;
            static long long w[81];
            for (int e = 0; e <= c; e++)
                w[e] = invfact[e] * invfact[c - e] % MOD;
            for (int i = 0; i <= even; i++)
                for (int s = 0; s <= half; s++)
                    newdp[i][s] = 0;
            for (int e = 0; e <= c; e++) {
                int dk = e;
                int ds = d * e;
                if (dk > even || ds > half) break;
                long long we = w[e];
                for (int i = 0; i + dk <= even; i++) {
                    for (int s = 0; s + ds <= half; s++) {
                        long long v = dp[i][s];
                        if (v)
                            newdp[i + dk][s + ds] = (newdp[i + dk][s + ds] + v * we) % MOD;
                    }
                }
            }

            for (int i = 0; i <= even; i++)
                for (int s = 0; s <= half; s++)
                    dp[i][s] = newdp[i][s];
        }

        long long weight = dp[even][half];
        long long ans = weight * fact[even] % MOD * fact[odd] % MOD;
        return int(ans);
    }

};