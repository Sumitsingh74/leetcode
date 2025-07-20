class Solution {
    public static long stick(int ind,int j,int f,long g) {
		if(ind==-1){
			return (j==0)?1:0;
		}
		if(cp[ind][j][f]!=-1)return cp[ind][j][f];
		int t=f;
		if((g&(1L <<ind))!=0)t=0;
		long ans=stick(ind-1,j,t,g);
		if(f==1){
			if((g&(1L <<ind))!=0&&j>0){
				ans+=stick(ind-1,j-1,1,g);
			}
		}else if(j>0){
			ans+=stick(ind-1,j-1,0,g);
		}
		return cp[ind][j][f]=ans;
	}

	public static int pop(int l){
		int ans=0;
		while(l!=1){
			int d=0;
			for(int i=0;i<7;i++){
				if((1<<i&l)!=0)d++;
			}
			l=d;ans++;
		}
		return ans;
	}
    static long[][][]cp;
    public static long popcountDepth(long n, int k) {
		long ans=0;
		if(k==0){
			return 1;
		}else if(k==1){
			long f=1;
			while(f*2<=n){
				f*=2;ans++;
			}
			return ans;
		}
		for (int i=2;i<60;i++) {
			int l=pop(i);
			if(l==k-1){
				cp=new long[60][i+1][2];
				for(int j=0;j<60;j++){
					for(int h=0;h<=i;h++)Arrays.fill(cp[j][h],-1);
				}
				ans+=stick(59,i,1,n);
			}
		}

		return ans;
    }
}