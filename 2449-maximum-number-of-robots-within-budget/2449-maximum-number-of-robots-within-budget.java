class Solution {
    public int maximumRobots(int[] chargeTimes, int[] runningCosts, long budget) {
        int n = chargeTimes.length;
        long[] prefixSum = new long[n+1];
        for(int i=1; i<=n; i++){
            prefixSum[i] = prefixSum[i-1] + runningCosts[i-1];
        }

        Deque<Integer> maxDeque = new LinkedList<>();

        int maxRobots = 0; 

        int left = 0;

        for(int right=0; right<n; right++){
            while(!maxDeque.isEmpty() && maxDeque.peekLast() < chargeTimes[right]){
                maxDeque.pollLast();
            }
            maxDeque.offerLast(chargeTimes[right]);

            while(!maxDeque.isEmpty() && (maxDeque.peekFirst() + (right-left+1)*(prefixSum[right+1] - prefixSum[left])) > budget){
                if(maxDeque.peekFirst() == chargeTimes[left]){
                    maxDeque.pollFirst();
                }
                left++;
            }

            maxRobots = Math.max(maxRobots, right-left+1);
        }

        return maxRobots;
    }
}