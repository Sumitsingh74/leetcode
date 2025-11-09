class Solution {
    public int minMoves(int[] nums) {
        int maxVal = Arrays.stream(nums).max().getAsInt();
        int cnt = 0;

        for (int num : nums) {
            cnt += maxVal - num;
        }
        return cnt;
    }
}