class Solution {
        public long maximumProduct(int[] A, int m) {
        long ma = A[0], mi = A[0], res = 1L * A[0] * A[m - 1];
        for (int i = m - 1; i < A.length; ++i) {
            ma = Math.max(ma, A[i - m + 1]);
            mi = Math.min(mi, A[i - m + 1]);
            res = Math.max(res, Math.max(mi * A[i], ma * A[i]));
        }
        return res;
    }
}