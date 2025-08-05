class Solution {
    public static boolean check(int n,int mid,char[]str,int[]order,int k){
		int[]arr=new int[n];
		for(int i=0;i<=mid;i++)arr[order[i]]=1;
		long ans=0;
		int j=0;
		for(int i=0;i<n;i++){
			j=Math.max(j,i);
			while(j<n&&arr[j]!=1)j++;
			ans+=(n-j);
		}
		return ans>=k;
	}
	public int minTime(String s, int[] order, int k) {
		int ans=-1;char[]str=s.toCharArray();
		int l=0;int r=order.length-1;
		while (l<=r){
			int mid=(l+r)>>>1;
			if(check(s.length(),mid,str,order,k)){
				ans=mid;r=mid-1;
			}else l=mid+1;
		}
		return ans;
	}
}