class Solution {
    private int solve(int[] a, int target) {
        int n = a.length;
        int cnt = 0;
        for (int i = 0; i < n - 1; i++) {
            if (a[i] != target) {
                cnt++;
                a[i] = -a[i];
                a[i + 1] = -a[i + 1];
            }
        }
        return (a[n - 1] == target ? cnt : Integer.MAX_VALUE);
    }

    public boolean canMakeEqual(int[] nums, int k) {
        // Try making all 1s or all -1s
        return solve(nums.clone(), 1) <= k || solve(nums.clone(), -1) <= k;
    }
}