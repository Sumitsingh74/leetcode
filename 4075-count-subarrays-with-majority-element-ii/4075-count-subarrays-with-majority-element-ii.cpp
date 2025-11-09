class Solution {
    class FenwickTree {
        vector<int> range;
        public:
        FenwickTree(int n) {
            range.resize(n + 1, 0);
        }
        int getSum(int x) {
            int sum = 0;
            while (x > 0) {
                sum += range[x];
                int lsb = x & (-x);
                x -= lsb;
            }
            return sum;
        }
        void update(int x, int delta) {
            while (x < range.size()) {
                range[x] += delta;
                int lsb = x & (-x);
                x += lsb;
            }
        }
    };
public:
    long long countMajoritySubarrays(vector<int>& nums, int target) {
        int n = nums.size();
        vector<int> prefix(n + 1, 0);
        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + (nums[i] == target ? 1 : -1);
        }
        long long ans = 0;
        const int shift = n + 1;
        FenwickTree ft(n + shift);
        ft.update(0 + shift, 1);
        for (int i = 0; i < n; i++) {
            int count = prefix[i + 1];
            ans += ft.getSum(count - 1 + shift);
            ft.update(count + shift, 1);
        }
        return ans;
    }
};