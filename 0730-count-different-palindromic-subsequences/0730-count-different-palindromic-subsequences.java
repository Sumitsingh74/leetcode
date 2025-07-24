class Solution {
    public static int countPalindromicSubsequences(String s) {
		int n=s.length();
		char[]str=s.toCharArray();
		cp=new long[n][n][4];
		for(int i=0;i<n;i++)for(int j=0;j<n;j++)Arrays.fill(cp[i][j],-1);
		long ans=0;
		for(int i=0;i<4;i++){
			ans+=dfs(0,n-1,i,str);
			ans%=MoD;
		}
		return (int)ans;
	}
    static long[][][]cp;static long MoD=1000000007;
    public static long dfs(int l,int r,int n,char[]str){
        if(l>r)return 0;
		if(l==r)return (str[l]-'a'==n)?1:0;
		if(cp[l][r][n]!=-1)return cp[l][r][n];
		long ans=0;
		if(str[l]==str[r]&&str[l]-'a'==n){
			ans+=2;
			for(int i=0;i<4;i++) {
				ans+=dfs(l+1,r-1,i,str);
				ans%=MoD;
			}
		}else {
			ans+=dfs(l+1,r,n,str);
			ans+=dfs(l,r-1,n,str);
			ans%=MoD;
			ans-=dfs(l+1,r-1,n,str);
			ans+=MoD;ans%=MoD;
		}
		ans%=MoD;
		return cp[l][r][n]=ans;
    }
}