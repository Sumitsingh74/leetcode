class Solution {
    public int[] maxValue(int[] nums) {
        int n = nums.length;
        if (n == 0) return new int[0];

        int[] pre = new int[n], suff = new int[n], res = new int[n];

        pre[0] = nums[0];
        for (int i = 1; i < n; i++) pre[i] = Math.max(nums[i], pre[i - 1]);

        suff[n - 1] = nums[n - 1];
        for (int i = n - 2; i >= 0; i--) suff[i] = Math.min(nums[i], suff[i + 1]);

        res[n - 1] = pre[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            res[i] = pre[i];
            if (pre[i] > suff[i + 1]) res[i] = res[i + 1];
        }
        return res;
    }
}