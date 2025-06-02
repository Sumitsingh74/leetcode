class Solution {

    public int[][] minAbsDiff(int[][] grid, int k) {

        int m = grid.length, n = grid[0].length;

        int[][] res = new int[m - k + 1][n - k + 1];

        if (k == 1) {
            // Sub-grids of size 1 have no pairs to compare, difference is 0
            return res;
        }

        for (int i = 0; i < m - k + 1; i++) {
            for (int j = 0; j < n - k + 1; j++) {

                ArrayList<Integer> ar = new ArrayList<>();

                // Collect unique elements in the k x k sub-grid
                for (int l = i; l < i + k; l++) {
                    for (int kk = j; kk < j + k; kk++) {
                        if (!ar.contains(grid[l][kk])) {
                            ar.add(grid[l][kk]);
                        }
                    }
                }

                Collections.sort(ar);

                int d = Integer.MAX_VALUE;

                // Find minimum difference between consecutive elements
                for (int ii = 0; ii < ar.size() - 1; ii++) {
                    d = Math.min(d, Math.abs(ar.get(ii) - ar.get(ii + 1)));
                }

                if (d == Integer.MAX_VALUE) {
                    d = 0; // Only one unique element in the sub-grid
                }

                res[i][j] = d;
            }
        }

        return res;
    }
}