class Solution {
    static int MoD = 1000000007;static int MoD1=998244353;
    public static long stick(int ind,int n,int sum,int k){
		int r = list.size();
		if (k <= 0) return 0;
		long[][] dp = new long[r + 1][k];
		Arrays.fill(dp[r], 1);
		for (int i = r - 1; i >= 0; --i) {
			int cap = list.get(i);
			long[] suf = new long[k + 1];
			for (int s = k - 1; s >= 0; --s) {
				suf[s] = (dp[i + 1][s] + suf[s + 1]) % MoD;
			}
			for (int s = 0; s < k; ++s) {
				int hi = Math.min(k - 1, s + cap);
				dp[i][s] = (suf[s] - suf[hi + 1] + MoD) % MoD;
			}
		}
		return dp[0][0];
	}
	static ArrayList<Integer>list;static long[]pre1;
    static long[][]dp;
	public static int possibleStringCount(String word, int k) {
		long ans=1;
		list=new ArrayList<>();int cou=0;
		for(int i=0;i<word.length();i++){
			int j=i;
			while (j<word.length()&&word.charAt(i)==word.charAt(j)){
				j++;
			}
			cou++;
			int l=j-i-1;
			if(l>0) list.add(l);
			i=j-1;
		}
		pre1=new long[list.size()];
		for(int i=list.size()-1;i>=0;i--){
			pre1[i]=list.get(i)+1;
			if(i+1<list.size()){
				pre1[i]*=pre1[i+1];
				pre1[i]%=MoD;
			}
		}
		k-=cou;
        if(list.size()==0)return 1;
		dp=new long[list.size()+1][Math.max(k,0)+1];
		for(int i=0;i<=list.size();i++)Arrays.fill(dp[i],-1);
		ans=stick(0,list.size(),0,k);
        ans=(pre1[0]-ans+MoD)%MoD;
		return (int)ans;
	}
}