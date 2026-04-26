class Solution {
    static class SegmentTree {
        int n;
        long[] tree;
        public SegmentTree(int size) {
            n = size;
            tree = new long[4 * n];
            java.util.Arrays.fill(tree, 0L);
        }
        public void update(int idx, long val) {
            update(1, 1, n, idx, val);
        }
        private void update(int node, int start, int end, int idx, long val) {
            if (start == end) {
                tree[node] = Math.max(tree[node], val);
            } else {
                int mid = (start + end) >> 1;
                if (idx <= mid) update(2 * node, start, mid, idx, val);
                else update(2 * node + 1, mid + 1, end, idx, val);
                tree[node] = Math.max(tree[2 * node], tree[2 * node + 1]);
            }
        }
        public long query(int l, int r) {
            if (l > r) return Long.MIN_VALUE;
            return query(1, 1, n, l, r);
        }
        private long query(int node, int start, int end, int l, int r) {
            if (r < start || end < l) return Integer.MIN_VALUE;
            if (l <= start && end <= r) return tree[node];
            int mid = (start + end) >> 1;
            Long left = query(2 * node, start, mid, l, r);
            Long right = query(2 * node + 1, mid + 1, end, l, r);
            return Math.max(left, right);
        }
    }

    public static long maxAlternatingSum(int[] arr, int k) {
        long ans = 0;
        for (int x : arr) ans = Math.max(ans, x);
        int n = arr.length;
        if (n > k) {
            long[][] brr = new long[2][n];
            long[][] crr = new long[2][n];
            SegmentTree smin = new SegmentTree((int)1e5 + 5);
            SegmentTree smax = new SegmentTree((int)1e5 + 5);
            for (int i = 0; i < n; i++) crr[0][i] = crr[1][i] = (long)arr[i];
            for (int i = k; i < n; i++) {
                int l = arr[i - k];
                long min = crr[0][i - k];
                long max = crr[1][i - k];
                smax.update(l, min);
                smin.update(l, max);
                min = smin.query(1, arr[i] - 1);
                max = smax.query(arr[i] + 1, (int)1e5);
                int l1 = arr[i];
                brr[0][i] = l1 + min;
                brr[1][i] = l1 + max;
                crr[0][i] = brr[0][i];
                crr[1][i] = brr[1][i];
                ans = Math.max(ans, l1 + Math.max(min, max));
            }
            // java.util.Arrays.fill(smin.tree, 0L);
            // java.util.Arrays.fill(smax.tree, 0L);
            // for (int i = 0; i < n; i++) crr[0][i] = crr[1][i] = arr[i];
            // for (int i = n - k - 1; i >= 0; i--) {
            //     int l = arr[i + k];
            //     Long min = crr[0][i + k];
            //     Long max = crr[1][i + k];
            //     smax.update(l, min);
            //     smin.update(l, max);
            //     Long min1 = smin.query(1, arr[i] - 1);
            //     Long max1 = smax.query(arr[i] + 1, (int)1e5);
            //     int l1 = arr[i];
            //     crr[0][i] = l1 + min1;
            //     crr[1][i] = l1 + max1;
            //     ans = Math.max(ans, min1 + brr[0][i]);
            //     ans = Math.max(ans, max1 + brr[1][i]);
            // }
        }
        return ans;
    }
}