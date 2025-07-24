class Solution {
    public static long stick(int ind,int n,int c,int f) {
		if(ind==-1)return 1;
		long ans=0;
		if( cp[ind][c][f]!=-1)return  cp[ind][c][f];
		int t=(n&(1<<ind))>0?0:c;
		ans+=stick(ind-1,n,t,0)%MoD;
		 if(f!=1) {
			if(c==1&&(n&(1<<ind))==0){
			}else {
				ans+=stick(ind-1,n,c,1)%MoD;
			}
		}
		return cp[ind][c][f]=ans;
	}
    static long[][][]cp;static long MoD=1000000007;
	public static int findIntegers(int n) {
		int ans=0;
		cp=new long[30][2][2];
		for(int i=0;i<30;i++)for(int j=0;j<2;j++)Arrays.fill(cp[i][j],-1);
		ans=(int) stick(29,n,1,0);
		return ans;
	}
}