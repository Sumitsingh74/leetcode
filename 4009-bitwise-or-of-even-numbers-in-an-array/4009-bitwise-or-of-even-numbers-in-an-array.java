class Solution {
    public int evenNumberBitwiseORs(int[] nums) {
        int result = 0;
        for (int i = 1; i <= 6; i++) {
            for (int num : nums) {
                if ((num & 1) == 0 && ((num >> i & 1) == 1)) {
                    result |= (1 << i);
                    break;
                }
            }
        }
        return result;
    }
}