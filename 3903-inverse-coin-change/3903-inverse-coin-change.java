class Solution {
    static long[][]dp;
    public static long stick(int ind,int n,int tar,ArrayList<Integer>list){
		if(ind<0)return 0;
		if(dp[ind][tar]!=-1)return dp[ind][tar];
		long ans=0;
		int k=list.get(ind);
		if(tar%k==0)ans++;
		for(int i=0;i<=tar;i++){
			long d=(long)i*k;
			if(d>=tar)break;
			ans+=stick(ind-1,n,(int)(tar-d),list);
		}
		return dp[ind][tar]=ans;
	}
	public static List<Integer> findCoins(int[] arr) {
		ArrayList<Integer>list=new ArrayList<>();
		int n=arr.length;

		long[]brr=new long[n];
		for(int g=0;g<n;g++){
            int i=g+1;
			int t=i;
			dp=new long[list.size()][i+1];
			for(int j=0;j<list.size();j++)Arrays.fill(dp[j],-1);
			long c=stick(list.size()-1,n,t,list);
			if(c==arr[g]){
				continue;
			}
			if(c+1==arr[g]){
				list.add(i);
			}else return new ArrayList<>();
		}
		return list;
	}
}