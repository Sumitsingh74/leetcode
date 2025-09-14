class Solution { 
    static long[][][] dp = new long[(int) 1e5][2][3];
    
    static int n;
    static int modulo = (int) (1e9+7);

    public int countStableSubsequences(int[] nums) {
        n = nums.length; 
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 2; j++) {
                dp[i][j][0] = dp[i][j][1] = dp[i][j][2] = -1;
            }
        }
         
        return (int)(f(nums, 0, -1,0)%modulo)-1;
    }
 
    long f(int[] nums, int i, int isOdd, int time){
        if(i == n)return 1;
        if(isOdd != -1 && dp[i][isOdd][time] != -1)return dp[i][isOdd][time];
        long ans = f(nums, i+1, isOdd, time)%modulo;

        if((nums[i] & 1) == 0){
            if(isOdd == 0){
                if(time == 2)return dp[i][isOdd][time]=ans;
                ans += f(nums, i+1, 0, time+1)%modulo;
            } else {
                ans+=f(nums,i+1,0,1)%modulo;
            }
        } else {
            if(isOdd == 1){
                if(time == 2)return dp[i][isOdd][time] = ans;
                ans += f(nums, i+1, 1, time+1)%modulo;
            } else {
                ans += f(nums, i+1, 1, 1)%modulo;
            }
        }
        if(isOdd != -1)dp[i][isOdd][time] = ans;
        return ans;
    }
}