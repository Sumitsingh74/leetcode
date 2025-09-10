import java.util.*;

class Solution {
    public long bowlSubarrays(int[] nums) {
        int n = nums.length;
        int[] left = new int[n];
        Arrays.fill(left, -1);

        Deque<Integer> st = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            int x = nums[i];
            while (!st.isEmpty() && nums[st.peek()] < x) st.pop();
            if (!st.isEmpty()) left[i] = st.peek();
            st.push(i);
        }

        int[] right = new int[n];
        Arrays.fill(right, n);
        st.clear();
        for (int i = n - 1; i >= 0; i--) {
            while (!st.isEmpty() && nums[st.peek()] < nums[i]) st.pop();
            if (!st.isEmpty()) right[i] = st.peek();
            st.push(i);
        }

        long ans = 0;
        for (int i = 0; i < n; i++) {
            if (left[i] != -1 && right[i] != n) ans++;
        }
        return ans;
    }
}
