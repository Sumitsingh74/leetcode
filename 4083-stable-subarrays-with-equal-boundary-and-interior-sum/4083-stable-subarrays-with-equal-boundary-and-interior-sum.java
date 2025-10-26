class Solution {
       public long countStableSubarrays(int[] capacity) {
        long ans = 0, zeroCount = 0;
        long[] prefixSum = new long[capacity.length+1];
        prefixSum[0] = 0;
        for(int i = 0; i < capacity.length; ++i){
            prefixSum[i+1] = prefixSum[i] + capacity[i];  
            if(i > 0 && capacity[i] == 0 && capacity[i-1] == 0) zeroCount++;
        }
        Map<String, Long> map = new HashMap<>();
        for(int i = 0; i < capacity.length; ++i){
            String key = Long.toString(prefixSum[i] - (long)capacity[i]*2L) + "#" + Integer.toString(capacity[i]);
            ans += map.getOrDefault(key, 0L);
            String newKey = Long.toString(prefixSum[i]) + "#" + Integer.toString(capacity[i]);
            map.put(newKey, map.getOrDefault(newKey, 0L) + 1L);
        }
        return ans - zeroCount;
    }
}