class Solution {
    
    private long mod = 1000000007;
    
    private long process(int i,int delay, int end , int target,int[] dp){
        
        if(i>target)return 0;
        
        long crt = 0l;
        
        for(int j=i+delay;j<i+end;j++){
            
            
            if(j<=target && dp[j]!=-1l)
                crt = crt + dp[j];
            else
                crt  = crt + process(j,delay,end,target,dp);
            
            
            crt = crt%mod;
        }
            
        
        if(i+end>target) crt+= 1l;
        
        return dp[i]=(int)(crt % mod);
    }
    public int peopleAwareOfSecret(int n, int delay, int forget) {
        //recursive solution 
        int[] dp = new int[n+1];
        
        Arrays.fill(dp,-1);
        
        return (int)  process(1,delay,forget,n,dp);
    }
}