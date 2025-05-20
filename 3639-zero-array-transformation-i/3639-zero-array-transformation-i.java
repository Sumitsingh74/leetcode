class Solution {
    public boolean isZeroArray(int[] nums, int[][] queries) {
	int n = nums.length;
	Arrays.sort(queries, (a,b)->{
		if(a[0] != b[0]) return a[0]-b[0];
		return a[1]-b[1];
	});

	int l=0, r=0, len = queries.length;
	PriorityQueue<int[]> pq = new PriorityQueue<>((a,b)->a[1]-b[1]);
	for(int i=0; i<n; i++) {
		while(r<len && queries[r][0]<=i && i<=queries[r][1]) {
			pq.add(queries[r++]);
		}
		while(!pq.isEmpty() && pq.peek()[1] < i) pq.remove();

		if(pq.size() < nums[i]) return false;
	}
	return true;
}
}