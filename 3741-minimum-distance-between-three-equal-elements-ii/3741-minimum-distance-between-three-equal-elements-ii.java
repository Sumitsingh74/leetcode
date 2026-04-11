class Solution {
    public int minimumDistance(int[] nums) {
        int n = nums.length;
        Map<Integer, List<Integer>> map = new HashMap<>();
        for(int i = 0; i < n; i++) {
            if(!map.containsKey(nums[i])) map.put(nums[i], new ArrayList<>());
            map.get(nums[i]).add(i);
        }
        int ans = Integer.MAX_VALUE;
        for(int key : map.keySet()) {
            List<Integer> list = map.get(key);
            if(list.size() >= 3) ans = Math.min(ans, fun(list));
        }
        if(ans == Integer.MAX_VALUE) return -1;
        return ans;
    }
    int fun(List<Integer> list) {
        int ans = Integer.MAX_VALUE;
        for(int i = 1; i < list.size() - 1; i++) {
            int dif1 = list.get(i) - list.get(i - 1);
            int dif2 = list.get(i + 1) - list.get(i);
            int dif3 = list.get(i + 1) - list.get(i - 1);
            int sum = dif1 + dif2 + dif3;
            ans = Math.min(ans, sum);
        }
        return ans;
    }
}