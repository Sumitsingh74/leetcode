class Solution {
    public static long stick(int ind,int n,int k,int c){
		if(ind==n){
			if(c!=0)return (long)-1e16;
			return 0;
		}
		if(cp[ind][k][c]!=-1)return cp[ind][k][c];
		long ans=stick(ind+1,n,k,c);
		if(c==0){
			if (k > 0) {
				ans=Math.max(ans,stick(ind+1,n,k-1,1)-arr1[ind]);
				ans=Math.max(ans,stick(ind+1,n,k-1,2)+arr1[ind]);
			}
		}else if(c==1){
			ans=Math.max(ans,stick(ind+1,n,k,0)+arr1[ind]);
		}else {
			ans=Math.max(ans,stick(ind+1,n,k,0)-arr1[ind]);
		}
//		dbg.print(ind," c  ",c," k ",k,"  ans  " ,ans);
		return cp[ind][k][c]=ans;
	}
	static int[]arr1;static long[][][]cp;
	public static long maximumProfit(int[] prices, int k) {
		long ans=0;int n=prices.length;
		arr1=new int[n];for(int i=0;i<n;i++)arr1[i]=prices[i];
		cp=new long[n+1][k+1][3];
		for(int i=0;i<=n;i++)for(int j=0;j<=k;j++)Arrays.fill(cp[i][j],-1);
		ans=stick(0,n,k,0);
		return ans;
	}
}