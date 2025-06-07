class Solution {
    public static long binaryExponentiation(long a, long n) {if(n==0) {return 1;}if(n==1) {return a;}long val = binaryExponentiation(a,n/2);val = (val*val);val = (val*binaryExponentiation(a, n%2));return val;}

    public static long gcd(long a, long b) {if (b==0) return a;return gcd(b,a%b);}
	


    public static long maxGCDScore(int[] nums, int k) {
		long ans=0;int n=nums.length;
		int[]arr=new int[n+1];int[]brr=new int[n+1];
		for(int i=0;i<n;i++){
			int l=nums[i];int cou=0;
			while (l>1&&l%2==0){
				l/=2;cou++;
			}
			arr[i+1]=l;brr[i+1]=cou;
		}
		for(int i=1;i<=n;i++){
			long gc=arr[i];int m2=brr[i];
			HashMap<Integer,Integer> map=new HashMap<>();
			for(int j=i;j<=n;j++){
				map.put(brr[j],map.getOrDefault(brr[j],0)+1);
				m2=Math.min(m2,brr[j]);
				gc=gcd(arr[j],gc);
				int l=j-i+1;
				int c=l-map.getOrDefault(m2,0);
				int d=m2;
				if(c+k>=l)d++;
				ans=Math.max(ans,gc*(binaryExponentiation(2,d))*(long)l);
			}
		}
		return ans;
	}
}