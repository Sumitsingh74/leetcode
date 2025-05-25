class Solution {
    public long minCuttingCost(int n, int m, int k) {
        Long res = Long.MAX_VALUE;

		if (n <= k && m <= k) return 0L;

		for (int i = 1; i < n; i++) {
			int a = i, b = n - i;
			if (a <= k && b <= k && m <= k) {
				res = Math.min(res, (long) a * b);
			}
		}
		for (int i = 1; i < m; i++) {
			int a = i, b = m - i;
			if (a <= k && b <= k && n <= k) {
				res = Math.min(res, (long) a * b);
			}
		}

		return res == Long.MAX_VALUE ? -1 : res;
    }
}