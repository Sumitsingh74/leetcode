class Solution {
    static constexpr int MOD = 1'000'000'007;

    struct SegmentTree {
        int size;
        std::vector<int> mins, maxs;

        SegmentTree(int n) {
            size = 1;
            while (size < n) size <<= 1;
            mins.assign(2 * size, INT_MAX);
            maxs.assign(2 * size, INT_MIN);
        }

        void build(const std::vector<int>& arr) {
            int n = (int)arr.size();
            for (int i = 0; i < n; i++) {
                mins[size + i] = arr[i];
                maxs[size + i] = arr[i];
            }
            for (int i = size - 1; i > 0; i--) {
                mins[i] = std::min(mins[i << 1], mins[i << 1 | 1]);
                maxs[i] = std::max(maxs[i << 1], maxs[i << 1 | 1]);
            }
        }

        int query_min(int l, int r) {
            l += size; r += size;
            int res = INT_MAX;
            while (l <= r) {
                if ((l & 1) == 1) res = std::min(res, mins[l++]);
                if ((r & 1) == 0) res = std::min(res, mins[r--]);
                l >>= 1; r >>= 1;
            }
            return res;
        }

        int query_max(int l, int r) {
            l += size; r += size;
            int res = INT_MIN;
            while (l <= r) {
                if ((l & 1) == 1) res = std::max(res, maxs[l++]);
                if ((r & 1) == 0) res = std::max(res, maxs[r--]);
                l >>= 1; r >>= 1;
            }
            return res;
        }
    };

public:
    int countPartitions(std::vector<int>& nums, int k) {
        int n = (int)nums.size();
        SegmentTree st(n);
        st.build(nums);
        vector<long long> dp(n + 1, 0);
        dp[n] = 1; 
        vector<long long> prefixSum(n + 2, 0); 
        prefixSum[n] = 1;
        for (int i = n - 1; i >= 0; i--) {
            int left = i, right = n - 1, best = i - 1;
            while (left <= right) {
                int mid = (left + right) / 2;
                int cur_min = st.query_min(i, mid);
                int cur_max = st.query_max(i, mid);
                if (cur_max - cur_min <= k) {
                    best = mid;
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }
            dp[i] = (prefixSum[i + 1] - prefixSum[best + 2] + MOD) % MOD;
            prefixSum[i] = (dp[i] + prefixSum[i + 1]) % MOD;
        }
        
        return (int)dp[0];
    }
};