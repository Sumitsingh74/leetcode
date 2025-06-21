class Solution {
    static int n, LOG;
	static int[][] stMin, stMax;
	static int[] log2, arr;
	public static void buildSparseTables() {
		LOG = 32 - Integer.numberOfLeadingZeros(n);
		stMin = new int[n][LOG + 1];
		stMax = new int[n][LOG + 1];
		log2 = new int[n + 1];
		log2[1] = 0;
		for (int i = 2; i <= n; i++) log2[i] = log2[i >> 1] + 1;
		for (int i = 0; i < n; i++) {
			stMin[i][0] = arr[i] == 0 ? Integer.MAX_VALUE : arr[i];
			stMax[i][0] = arr[i];
		}
		for (int j = 1; j <= LOG; j++) {
			for (int i = 0; i + (1 << j) <= n; i++) {
				stMin[i][j] = Math.min(stMin[i][j - 1], stMin[i + (1 << (j - 1))][j - 1]);
				stMax[i][j] = Math.max(stMax[i][j - 1], stMax[i + (1 << (j - 1))][j - 1]);
			}
		}
	}

	public static int queryMin(int l, int r) {
		int j = log2[r - l + 1];
		return Math.min(stMin[l][j], stMin[r - (1 << j) + 1][j]);
	}

	public static int queryMax(int l, int r) {
		int j = log2[r - l + 1];
		return Math.max(stMax[l][j], stMax[r - (1 << j) + 1][j]);
	}

	public static boolean isPrime(int x) {
		if (x < 2) return false;
		if (x % 2 == 0) return x == 2;
		for (int i = 3; i * (long) i <= x; i += 2)
			if (x % i == 0) return false;
		return true;
	}
	public static int primeSubarray(int[] nums, int k) {
		n = nums.length;
		arr = new int[n];
		arr = new int[n];int[]pre=new int[n];
		for (int i = 0; i < n; i++) {
			arr[i] = isPrime(nums[i]) ? nums[i] : 0;
			if(arr[i]==nums[i])pre[i]=1;
			if(i>0)pre[i]+=pre[i-1];
		}
		buildSparseTables();
		long ans = 0;
		for (int i = 0; i < n; i++) {
			if (queryMin(i, n - 1) == Integer.MAX_VALUE || queryMax(i, n - 1) == 0) continue;
			int lo = i, hi = n - 1, j1 = n;
			while (lo <= hi) {
				int mid = (lo + hi) >>> 1;
				int l=pre[mid];if(i>0)l-=pre[i-1];
				if (l>=2) {
					j1 = mid;
					hi = mid - 1;
				} else lo = mid + 1;
			}
			if (j1 == n) continue;
			lo = j1;
			hi = n - 1;
			int j2 = j1 - 1;
			while (lo <= hi) {
				int mid = (lo + hi) >>> 1;
				if (queryMax(i, mid) - queryMin(i, mid) <= k) {
					j2 = mid;
					lo = mid + 1;
				} else hi = mid - 1;
			}
			if (j2 >= j1) ans += j2 - j1 + 1;
		}
		return (int) ans;
	}
}