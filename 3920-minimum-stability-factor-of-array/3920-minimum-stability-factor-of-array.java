class Solution {
    static class SparseTableGCD {
		long[][] table;
		int[] logTable;
		int[] powers;
		int[] a;

		public SparseTableGCD(int[] a) {
			this.a = a;
			this.logTable = new int[a.length + 1];

			int maxLog;
			for(maxLog = 2; maxLog <= a.length; ++maxLog) {
				this.logTable[maxLog] = this.logTable[maxLog / 2] + 1;
			}

			maxLog = this.logTable[a.length] + 1;
			this.table = new long[a.length][maxLog];
			this.powers = new int[maxLog];
			this.powers[0] = 1;

			int i;
			for(i = 1; i < this.powers.length; ++i) {
				this.powers[i] = this.powers[i - 1] * 2;
			}

			for(i = 0; i < a.length; ++i) {
				this.table[i][0] = (long)a[i];
			}

			for(i = 1; i < maxLog; ++i) {
				for(int j = 0; j + this.powers[i] - 1 < a.length; ++j) {
					this.table[j][i] = gcd(this.table[j][i - 1], this.table[j + this.powers[i - 1]][i - 1]);
				}
			}

		}

		long query(int l, int r) {
			int log = this.logTable[r - l + 1];
			return gcd(this.table[l][log], this.table[r - this.powers[log] + 1][log]);
		}

		public static long gcd(long a, long b) {
			return b == 0L ? a : gcd(b, a % b);
		}
	}
	public static long stick(int i,int j) {
		return 0;
	}
	public static boolean df(int n,int[]arr,int L,int c){
		int ch = 0, i = 0, cover = -1;
		while (i + L <= n) {
			if (i <= cover) {
				++i; continue;
			}
			if (sg.query(i, Math.min(i + L,n-1)) >= 2&&i!=Math.min(i+L,n-1)) {
				cover = i + L ;
                if(i+L>n)continue;
				if (++ch >c) return false;
			} else i+=L;
		}
		return true;
	}
    private static class SparseGCD {
        int[][] st;
        int[] lg;
        SparseGCD(int[] a) {
            int n = a.length, K = 32 - Integer.numberOfLeadingZeros(n);
            st = new int[K][n];
            System.arraycopy(a, 0, st[0], 0, n);
            for (int k = 1; k < K; ++k)
                for (int i = 0; i + (1 << k) <= n; ++i)
                    st[k][i] = gcd(st[k - 1][i], st[k - 1][i + (1 << (k - 1))]);
            lg = new int[n + 1];
            for (int i = 2; i <= n; ++i) lg[i] = lg[i >> 1] + 1;
        }
        int gcd(int x, int y) { while (y != 0) { int t = x % y; x = y; y = t; } return Math.abs(x); }
        int query(int l, int r) {
            int len = r - l + 1, k = lg[len];
            return gcd(st[k][l], st[k][r - (1 << k) + 1]);
        }
    }
	static SparseTableGCD sg;
	public static int minStable(int[] nums, int maxC) {
        int n = nums.length;
        SparseGCD sg = new SparseGCD(nums);

        IntPredicate ok = L -> {
            int i = 0, used = 0, cover = -1;
            while (i + L <= n) {
                if (i <= cover) { ++i; continue; }
                if (sg.query(i, i + L - 1) >= 2) {
                    cover = i + L - 1;
                    if (++used > maxC) return false;
                } else ++i;
            }
            return true;
        };

        int lo = 1, hi = n + 1;
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (ok.test(mid)) hi = mid;
            else lo = mid + 1;
        }
        return lo == n + 1 ? n : lo - 1;
	}
}