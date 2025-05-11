class Solution {
    static long MoD=(long)(1e9+7);
    static long[][][]cp;
    public static  long binpow(long  a, long  b,  long m) {a %= m;long res = 1;while (b > 0) {if ((b & 1)==1) res = res * a % m;a = a * a % m;b >>= 1;}return res%m;}
	static long[] facts, factInvs;
	static long add(long a, long b,long Mod) {return (a+b)%Mod;}
	static long mul(long a, long b,long Mod) {return a*b%Mod;}
	static long modInv(long x,long Mod) {return binpow(x, Mod-2,Mod);}
	static void precomp(long Mod) {facts=new long[1001];factInvs=new long[1001];factInvs[0]=facts[0]=1;for (int i=1; i<facts.length; i++) facts[i]=mul(facts[i-1], i,Mod);factInvs[facts.length-1]=modInv(facts[facts.length-1],Mod);for (int i=facts.length-2; i>=0; i--) factInvs[i]=mul(factInvs[i+1], i+1,Mod);}
	

    public static long stick(int i,int n,int sum,int op,int[]arr){
		if(i==10){
			if(sum==0&&op==0)return 1;return 0;
		}
		if(op<0)return 0;
		if(cp[i][sum][op]!=-1)return cp[i][sum][op];
		long ans=0;
		for(int j=0;j<=Math.min(arr[i],op);j++){
			if(sum-j*i<0)break;
			long d =stick(i+1,n,sum-j*i,op-j,arr)%MoD;
			d*=factInvs[j];d%=MoD;
			d*=factInvs[arr[i]-j];d%=MoD;
			ans+=d;ans%=MoD;
		}
		return cp[i][sum][op]=ans;
	}

    public int countBalancedPermutations(String str) {
        precomp(MoD);
        int n=str.length();
        long ans=0;
        int[]fre=new int[10];int t=0;
        for(int i=0;i<n;i++){
            t+=str.charAt(i)-'0';
            fre[str.charAt(i)-'0']++;
        }
        cp=new long[10][t+1][n+1];
        for(int i=0;i<10;i++){
            for(int j=0;j<=t;j++)Arrays.fill(cp[i][j],-1);
        }
        if(t%2==0)ans=stick(0,n,t/2,(n+1)/2,fre);
        ans*=facts[(n+1)/2];ans%=MoD;
        ans*=facts[n/2];ans%=MoD;
        return (int)ans;
}
}