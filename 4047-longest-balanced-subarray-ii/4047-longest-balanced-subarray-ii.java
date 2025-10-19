import java.util.*;

public class Solution {
    static class SegmentTree {
        final int n;
        final int[] minTree;
        final int[] maxTree;
        final int[] lazy;

        SegmentTree(int n) {
            this.n = n;
            minTree = new int[4 * n];
            maxTree = new int[4 * n];
            lazy = new int[4 * n];
        }

        void push(int node, int l, int r) {
            if (lazy[node] != 0) {
                minTree[node] += lazy[node];
                maxTree[node] += lazy[node];
                if (l != r) {
                    lazy[node << 1] += lazy[node];
                    lazy[node << 1 | 1] += lazy[node];
                }
                lazy[node] = 0;
            }
        }

        void updateRange(int node, int l, int r, int ql, int qr, int val) {
            push(node, l, r);
            if (l > r || l > qr || r < ql) return;
            if (ql <= l && r <= qr) {
                lazy[node] += val;
                push(node, l, r);
                return;
            }
            int mid = (l + r) >>> 1;
            updateRange(node << 1, l, mid, ql, qr, val);
            updateRange(node << 1 | 1, mid + 1, r, ql, qr, val);
            minTree[node] = Math.min(minTree[node << 1], minTree[node << 1 | 1]);
            maxTree[node] = Math.max(maxTree[node << 1], maxTree[node << 1 | 1]);
        }

        int findLeftmostZero(int node, int l, int r) {
            push(node, l, r);
            if (minTree[node] > 0 || maxTree[node] < 0) return -1;
            if (l == r) return (minTree[node] == 0) ? l : -1;
            int mid = (l + r) >>> 1;
            int left = findLeftmostZero(node << 1, l, mid);
            if (left != -1) return left;
            return findLeftmostZero(node << 1 | 1, mid + 1, r);
        }
    }

    public int longestBalanced(int[] nums) {
        int n = nums.length;
        if (n == 0) return 0;
        SegmentTree st = new SegmentTree(n);
        Map<Integer, Integer> prev = new HashMap<>();
        int res = 0;

        for (int r = 0; r < n; ++r) {
            int v = nums[r];
            int val = (v % 2 == 0) ? 1 : -1;

            Integer p = prev.get(v);
            if (p != null) {
                st.updateRange(1, 0, n - 1, 0, p, -val);
            }

            st.updateRange(1, 0, n - 1, 0, r, val);
            prev.put(v, r);

            int l = st.findLeftmostZero(1, 0, n - 1);
            if (l != -1 && l <= r) {
                res = Math.max(res, r - l + 1);
            }
        }
        return res;
    }

}
