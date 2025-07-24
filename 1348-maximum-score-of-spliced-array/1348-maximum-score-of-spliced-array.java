class Solution {
    public int maximumsSplicedArray(int[] nums1, int[] nums2) {
		int ans=0;
		PriorityQueue<Long> pq=new PriorityQueue<>();
		pq.add(0L);long sum=0;long ans1=0;
		for(int i=0;i<nums1.length;i++){
			sum+=nums2[i]-nums1[i];
			long d=sum-pq.peek();
			d=Math.max(ans1,d);
			ans1=Math.max(ans1,d);
			ans+=nums1[i];
			pq.add(sum);
		}
		ans+=ans1;
		long c=ans;
		ans=0;ans1=0;sum=0;pq=new PriorityQueue<>();pq.add(0L);
		for(int i=0;i<nums1.length;i++){
			sum+=nums1[i]-nums2[i];
			long d=sum-pq.peek();
			d=Math.max(ans1,d);
			ans1=Math.max(ans1,d);
			ans+=nums2[i];
			pq.add(sum);
		}
		ans+=ans1;
		c=Math.max(c,ans);
		
		return (int)c;
	}
}