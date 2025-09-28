class Solution {
    public long splitArray(int[] nums) {
        int n = nums.length;
        boolean[] inc = new boolean[n];
        boolean[] dec = new boolean[n];
        inc[0] = true;
        for(int i = 1; i < n; i++) {
            if(nums[i] > nums[i - 1] && inc[i -1]) inc[i] = true;
            else inc[i] = false;
        }
        dec[n - 1] = true;
        for(int i = n - 2; i >= 0; i--) {
            if(nums[i] > nums[i + 1] && dec[i + 1]) dec[i] = true;
            else dec[i] = false;
        }
        long[] prefix = new long[n];
        prefix[0] = nums[0];
        for(int i = 1; i < n; i++) {
            prefix[i] = prefix[i - 1] + nums[i];
        }
        long ans = Long.MAX_VALUE;
        for(int i = 0; i < n - 1; i++) {
            if(inc[i] && dec[i + 1]) {
                long leftSum = prefix[i];
                long rightSum = prefix[n - 1] - prefix[i];
                ans = Math.min(ans, Math.abs(leftSum - rightSum));
            }
        }
        return ans == Long.MAX_VALUE ? -1 : ans;
    }
}