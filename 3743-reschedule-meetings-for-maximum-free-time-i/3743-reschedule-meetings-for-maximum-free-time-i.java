class Solution {
    public static int maxFreeTime(int eventTime, int k, int[] startTime, int[] endTime) {
		int ans=startTime[0];
		int n=startTime.length;int[]pre=new int[n];
		for(int i=0;i<n;i++){
			pre[i]=endTime[i]-startTime[i];
			if(i>0)pre[i]+=pre[i-1];
		}
		int prev=0;
		for(int i=0;i<n;i++){
			int l=i;int r=i+k-1;
			int an=Math.min(r,n-1);
			// while(l<=r){
			// 	int mid=(l+r)/2;
			// 	if(mid<n&&endTime[mid]<=eventTime){
			// 		an=mid;l=mid+1;
			// 	}else r=mid-1;
			// }
			if(an!=-1){
				int end=eventTime;
				if(an+1<n)end=Math.min(end,startTime[an+1]);
				int d=end-prev;
				d-=pre[an];
				if(i>0)d+=pre[i-1];
				ans=Math.max(d,ans);
			}
			prev=endTime[i];
		}

		return ans;
	}
}


