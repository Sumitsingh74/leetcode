class Solution {
    public static int maxFreeTime(int eventTime, int[] startTime, int[] endTime) {
		int n=startTime.length;	int ans=0;
		int[]pre=new int[n];int[]suf=new int[n];
        pre[0]=startTime[0];
		for(int i=1;i<n;i++){
			pre[i]=startTime[i]-endTime[i-1];
			pre[i]=Math.max(pre[i],pre[i-1]);
		}
        suf[n-1]=eventTime-endTime[n-1];
		for(int i=n-2;i>=0;i--){
			suf[i]=startTime[i+1]-endTime[i];
			suf[i]=Math.max(suf[i],suf[i+1]);
		}
		int prev=0;
		for(int i=0;i<n;i++){
			int end=eventTime;
			if(i+1<n)end=startTime[i+1];
			int d=endTime[i]-startTime[i];
			if(i>0&&pre[i-1]>=d)d=0;
			if(i<n-1&&suf[i+1]>=d)d=0;
			ans=Math.max(ans,end-prev-d);
			prev=endTime[i];
		}
		return ans;
	}
}