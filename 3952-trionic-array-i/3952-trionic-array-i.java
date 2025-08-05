class Solution {
        public boolean isTrionic(int[] A) {
        int p = 0, q = 0, n = A.length;
        for (int i = 1; i < n; ++i) {
            if (A[i - 1] == A[i]) {
                return false;
            }
            if (i >= 2 && A[i - 2] < A[i - 1] && A[i - 1] > A[i]) {
                if (p != 0 || q != 0) {
                    return false;
                }
                p = i;
            }
            if (i >= 2 && A[i - 2] > A[i - 1] && A[i - 1] < A[i]) {
                if (p == 0 || q != 0) {
                    return false;
                }
                q = i;
            }
        }
        return q > 0;
    }
}