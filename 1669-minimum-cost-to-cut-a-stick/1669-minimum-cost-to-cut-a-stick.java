class Solution {
    public int minCost(int n, int[] cuts) {
        int temp[] = new int[cuts.length+2];
        temp[0] = 0;
        
        for(int i = 1; i< temp.length-1; i++){
            temp[i] = cuts[i-1];
        }
        
        temp[temp.length-1] = n;
        Arrays.sort(temp);
        
        int dp[][] = new int[temp.length+1][temp.length+1];
        
        for(int a[]: dp)
            Arrays.fill(a, -1);
        
        return func(1, cuts.length, temp, dp);
    }
	
    int func(int i, int j, int[] temp, int[][] dp){
        if(i > j)
            return 0;
            if(dp[i][j]!=-1)return dp[i][j];
        
        int min = Integer.MAX_VALUE;
        for(int ind = i; ind <= j; ind++){
            int cost = temp[j+1] - temp[i-1] + func(i, ind-1, temp, dp) + func(ind+1, j, temp, dp);
            min = Math.min(min, cost);
        }
        return  dp[i][j]=min;
    }
}