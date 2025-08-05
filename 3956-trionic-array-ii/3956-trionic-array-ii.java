class Solution {
    static long[][]dp;
    public static long stick(int ind,int n,int f,int[]arr) {
		if(ind==n){
			if(f==5||f==4)return 0;
			return -(long)1e16;
		}
		if(dp[ind][f]!=-1)return dp[ind][f];
		long ans=-(long)1e16;
		if(f==0||f==5)ans=Math.max(ans,stick(ind+1,n,f,arr));
		if(f==0){
			ans=Math.max(ans,arr[ind]+stick(ind+1,n,1,arr));
		}else if(f==1){
			if(arr[ind]>arr[ind-1])ans=Math.max(ans,arr[ind]+stick(ind+1,n,2,arr));
		} else if (f==2) {
			if(arr[ind]>arr[ind-1])ans=Math.max(ans,arr[ind]+stick(ind+1,n,2,arr));
			else if(arr[ind]<arr[ind-1])ans=Math.max(ans,arr[ind]+stick(ind+1,n,3,arr));
		} else if (f==3) {
			if(arr[ind]>arr[ind-1])ans=Math.max(ans,arr[ind]+stick(ind+1,n,4,arr));
			else if(arr[ind]<arr[ind-1])ans=Math.max(ans,arr[ind]+stick(ind+1,n,3,arr));
		} else if (f==4) {
			if(arr[ind]>arr[ind-1])ans=Math.max(ans,arr[ind]+stick(ind+1,n,4,arr));
			ans=Math.max(ans,stick(ind+1,n,5,arr));
		}
		return dp[ind][f]=ans;
	}
	public static long maxSumTrionic(int[] nums) {
		long ans=0;
		dp=new long[nums.length][6];
		for(int i=0;i<dp.length;i++)Arrays.fill(dp[i],-1);
		ans=stick(0,nums.length,0,nums);
		return ans;
	}
}