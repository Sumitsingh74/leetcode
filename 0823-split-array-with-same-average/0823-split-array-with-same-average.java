class Solution {
   private int[] nums;
    private int n, total;
    private Map<Long, Boolean> memo;

    public boolean splitArraySameAverage(int[] nums) {
        this.nums = nums.clone();
        Arrays.sort(this.nums);
        n = this.nums.length;
        total = Arrays.stream(this.nums).sum();

        boolean feasible = false;
        for (int k = 1; k < n && !feasible; ++k)
            if ((total * k) % n == 0) feasible = true;
        if (!feasible) return false;

        memo = new HashMap<>();
        for (int k = 1; k < n; ++k) {
            if ((total * k) % n != 0) continue;
            int target = (total * k) / n;
            if (dfs(0, k, target)) return true;
        }
        return false;
    }

    private boolean dfs(int idx, int cnt, int sum) {
        if (cnt == 0) return sum == 0;
        if (idx == n || sum < 0) return false;
        if (sum < cnt * nums[idx]) return false;
        if (sum > cnt * nums[n - 1]) return false;

        long key = (((long) idx) << 40) | (((long) cnt) << 20) | sum;
        Boolean cached = memo.get(key);
        if (cached != null) return cached;

        if (dfs(idx + 1, cnt - 1, sum - nums[idx])) {
            memo.put(key, true);
            return true;
        }
        boolean res = dfs(idx + 1, cnt, sum);
        memo.put(key, res);
        return res;
    }
}