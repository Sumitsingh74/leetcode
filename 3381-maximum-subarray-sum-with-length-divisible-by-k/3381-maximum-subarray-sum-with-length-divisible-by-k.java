import java.util.HashMap;

class Solution {
    public long maxSubarraySum(int[] nums, int k) {
        int size = nums.length;
        long[] prefixSum = new long[size + 1];

        for (int index = 0; index < size; index++) {
            prefixSum[index + 1] = prefixSum[index] + nums[index];
        }

        HashMap<Integer, Long> remainderToMinPrefixSum = new HashMap<>();
        long maxSubarraySum = Long.MIN_VALUE;

        for (int index = 0; index <= size; index++) {
            int remainder = index % k;

            if (remainderToMinPrefixSum.containsKey(remainder)) {
                maxSubarraySum = Math.max(maxSubarraySum, prefixSum[index] - remainderToMinPrefixSum.get(remainder));
            }

            if (!remainderToMinPrefixSum.containsKey(remainder)) {
                remainderToMinPrefixSum.put(remainder, prefixSum[index]);
            } else {
                remainderToMinPrefixSum.put(remainder, Math.min(remainderToMinPrefixSum.get(remainder), prefixSum[index]));
            }
        }

        return maxSubarraySum == Long.MIN_VALUE ? 0 : maxSubarraySum;
    }
}