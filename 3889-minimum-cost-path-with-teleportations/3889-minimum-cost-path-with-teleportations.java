class Solution {
    final static int INF = (int)1e9;
	static void solve(int[][] curr, int[][] grid) {
		int m = grid.length, n = grid[0].length;
		curr[0][0] = 0;
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (i > 0) curr[i][j] = Math.min(curr[i][j], curr[i-1][j] + grid[i][j]);
				if (j > 0) curr[i][j] = Math.min(curr[i][j], curr[i][j-1] + grid[i][j]);
			}
		}
	}
	public static int minCost(int[][] grid, int k) {
		int ans=0;
		int m = grid.length, n = grid[0].length;

		int[][] prev = new int[m][n];
		int[][] curr = new int[m][n];
		for (int i = 0; i < m; i++) Arrays.fill(prev[i], INF);
		for (int i = 0; i < m; i++) Arrays.fill(curr[i], INF);

		// Coordinate compression
		List<Integer> A = new ArrayList<>();
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				A.add(grid[i][j]);
			}
		}
		Collections.sort(A);
		A = new ArrayList<>(new LinkedHashSet<>(A)); // unique
		Map<Integer, Integer> MP = new HashMap<>();
		for (int i = 0; i < A.size(); i++) {
			MP.put(A.get(i), i);
		}
		int sz = A.size();
		solve(prev, grid);
		ans=prev[m-1][n-1];
		for (int t = 1; t <= k; t++) {
			int[] best = new int[sz];
			Arrays.fill(best, INF);
			for (int i = 0; i < m; i++) Arrays.fill(curr[i], INF);

			for (int i = 0; i < m; i++) {
				for (int j = 0; j < n; j++) {
					best[MP.get(grid[i][j])] = Math.min(best[MP.get(grid[i][j])], prev[i][j]);
				}
			}

			for (int i = sz - 2; i >= 0; i--) {
				best[i] = Math.min(best[i], best[i+1]);
			}

			for (int i = 0; i < m; i++) {
				for (int j = 0; j < n; j++) {
					curr[i][j] = Math.min(prev[i][j], best[MP.get(grid[i][j])]);
				}
			}

			solve(curr, grid);

			int[][] temp = prev;
			prev = curr;
			curr = temp;

			ans = prev[m-1][n-1];
		}

		return ans;
	}
}