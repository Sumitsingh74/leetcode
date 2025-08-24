class Solution {
    public boolean partitionArray(int[] nums, int k) {
        HashMap<Integer, Integer> mpp = new HashMap<>();
        for (int x : nums) {
            mpp.put(x, mpp.getOrDefault(x, 0) + 1);
        }

        for (int count : mpp.values()) {
            if (count > nums.length / k) {
                return false;
            }
        }
        return nums.length % k == 0;
    }
}