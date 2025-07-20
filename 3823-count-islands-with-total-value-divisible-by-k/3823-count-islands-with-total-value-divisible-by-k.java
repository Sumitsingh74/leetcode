class Solution {
    static boolean[][]vis;static int val;
    public void dfs(int i,int j,int n,int m,int[][]grid){
     int[][] dir = {{-1,0} , {0,-1} , {0,1}, {1, 0}};
     val+=grid[i][j];
     vis[i][j]=true;
	for(int[] dire : dir) {
		int nrow = i + dire[0];
		int ncol = j + dire[1];
        if(nrow>=0&&nrow<n&&ncol>=0&&ncol<m&&grid[nrow][ncol]!=0&&!vis[nrow][ncol])
		dfs(nrow,ncol,n,m, grid);
        }
    }
    public int countIslands(int[][] grid, int k) {
        int n=grid.length;
        int m=grid[0].length;
        vis=new boolean[n][m];
        int ans=0;
        for(int i=0;i<n;i++){
            for(int j=0;j<m;j++){
                if(vis[i][j]||grid[i][j]==0)continue;
                val=0;
                dfs(i,j,n,m,grid);
                if(val%k==0)ans++;
            }
        }

        return ans;
    }
}