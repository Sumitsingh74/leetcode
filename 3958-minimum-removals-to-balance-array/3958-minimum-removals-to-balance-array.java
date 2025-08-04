import java.util.Arrays;

class Solution {
    public int minRemoval(int[] nums, int k) {
        Arrays.sort(nums); 

        int n = nums.length;
        int maxSize = 0;
        int left = 0;
        for (int right = 0; right < n; right++) {
            while (nums[right] > (long) k * nums[left]) left++;
            maxSize = Math.max(maxSize, right - left + 1);
        }
        return n - maxSize;
    }
}