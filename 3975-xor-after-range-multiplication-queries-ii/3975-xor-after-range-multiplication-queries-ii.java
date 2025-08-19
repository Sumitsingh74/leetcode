class Solution {
    static final long MOD = 1000000007L;

	long modmul(long a, long b) {
		return ((a % MOD) * (b % MOD)) % MOD;
	}

	long modexp(long a, long e) {
		a %= MOD;
		long r = 1;
		while (e > 0) {
			if ((e & 1) == 1) r = modmul(r, a);
			a = modmul(a, a);
			e >>= 1;
		}
		return r;
	}

	long modinv(long a) {
		return modexp(a, MOD - 2);
	}
	public int xorAfterQueries(int[] arr, int[][] queries) {
		int ans=0;
		int B=200;int n=arr.length;
		long[][] mul = new long[B][n];
		for (int i = 0; i < B; i++) Arrays.fill(mul[i], 1);
		for (int[] q : queries) {
			int l = q[0], r = q[1], k = q[2], v = q[3];
			if (k >= B) {
				for (int i = l; i <= r; i += k) {
					arr[i] = (int) modmul(arr[i], v);
				}
			}else{
				mul[k][l] = modmul(mul[k][l], v);
				for (int i = r + 1; i < n; i++) {
					if (i % k == l % k) {
						mul[k][i] = modmul(mul[k][i], modinv(v));
						break;
					}
				}
			}
		}
		for (int k = 1; k < B; k++) {
			for (int st = 0; st < k; st++) {
				long val = 1;
				for (int i = st; i < n; i += k) {
					val = modmul(val, mul[k][i]);
					arr[i] = (int) modmul(val, arr[i]);
				}
			}
		}
		for (int x : arr) ans ^= x;
		return ans;
	}
}