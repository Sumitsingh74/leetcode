class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        // corner case
        if (nums == null || k <= 0 || nums.length < k) return new int[]{};

        int[] result = new int[nums.length - k + 1];
        int index = 0;

        // initialize Sliding Window
        Deque<Integer> mdQueue = new ArrayDeque<>();

        for (int right = 0; right < nums.length; right++) {
            // add right
            while (!mdQueue.isEmpty() && nums[mdQueue.peekLast()] <= nums[right]) {
                mdQueue.pollLast();
            }
            mdQueue.offerLast(right);

            // remove left
            if (right >= k && mdQueue.peekFirst() < right - k + 1) {
                mdQueue.pollFirst();
            }

            // update result[]
            if (right >= k - 1) result[index++] = nums[mdQueue.peekFirst()];
        }

        return result;
    }
}