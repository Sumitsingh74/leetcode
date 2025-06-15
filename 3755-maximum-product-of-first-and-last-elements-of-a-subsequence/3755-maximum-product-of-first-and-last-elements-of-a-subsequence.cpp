class Solution {
public:
        long long maximumProduct(vector<int>& A, int m) {
        long long ma = A[0], mi = A[0], res = 1LL * A[0] * A[m - 1];
        for (int i = m - 1; i < A.size(); ++i) {
            ma = fmax(ma, A[i - m + 1]);
            mi = fmin(mi, A[i - m + 1]);
            res = max({res, mi * A[i], ma * A[i]});
        }
        return res;
    }
};