class Solution {
    public int sumDivisibleByK(int[] nums, int k) {
        Map<Integer, Integer> freq = new HashMap<>();
        for (int i : nums) {
            freq.put(i, freq.getOrDefault(i, 0) + 1);
        }
        int ans = 0;
        for (Map.Entry<Integer, Integer> x : freq.entrySet()) {
            int f = x.getValue();
            int v = x.getKey();
            ans = ans + ((f % k == 0) ? (v*f) : 0);
        }
        return ans;
    }
}