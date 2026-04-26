class Solution {
    int[] dir = {0, 1, 0, -1, 0};
    boolean[][] vis;
    private boolean dfs(char[][] grid, int row, int col, int prow, int pcol) {
        vis[row][col] = true;
        int M = grid.length;
        int N = grid[0].length;

        for(int i=0;i<4;i++) {
            int nrow = dir[i] + row;
            int ncol = dir[i+1] + col;

            if(nrow < M && nrow >= 0 && ncol < N && ncol >= 0 && grid[nrow][ncol] == grid[row][col] && (prow != nrow || ncol != pcol)) {
                if(vis[nrow][ncol]) {
                    return true;
                } else if(!vis[nrow][ncol] && dfs(grid, nrow, ncol, row, col)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean containsCycle(char[][] grid) {
        int M = grid.length;
        int N = grid[0].length;

        vis = new boolean[M][N];

        for(int i=0;i<M;i++) {
            for(int j=0;j<N;j++) {
                if(!vis[i][j] && dfs(grid, i, j, -1, -1)) {
                    return true;
                }
            }
        }
        return false;

    }
}