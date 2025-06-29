class Solution {
    static long[][][]cp;static long[]arr;
   public static long stick(int ind,int j,int n,int sum,int k){
		if(ind==n){
			if(sum!=k-1)return Integer.MAX_VALUE;
			return arr[ind]^arr[j-1];
		}
		if( cp[ind][j][sum]!=-1)return  cp[ind][j][sum];
		long not=stick(ind+1,j,n,sum,k);
		long take=Integer.MAX_VALUE;
		if(sum<k-1){
			take=Math.max(arr[ind]^arr[j-1],stick(ind+1,ind+1,n,sum+1,k));
		}
		return cp[ind][j][sum]=Math.min(take,not);
	}
	public static int minXor(int[] nums, int k) {
		long ans=0;
		int n=nums.length;
		arr=new long[n+1];for(int i=0;i<n;i++){
			arr[i+1]=nums[i]^arr[i];
		}
		cp=new long[n+1][n+1][k+1];for(int i=0;i<=n;i++)for(int j=0;j<=n;j++)Arrays.fill(cp[i][j],-1);
		ans=stick(1,1,n,0,k);
		return (int) ans;
	}

}