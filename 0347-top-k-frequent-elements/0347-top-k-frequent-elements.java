class Solution {
    public int[] topKFrequent(int[] nums, int k) {
        if (nums.length == k) {
            return nums;
        }

        Map<Integer, Integer> counts = new HashMap<>();

        for (int num : nums) {
            counts.put(num, counts.getOrDefault(num, 0) + 1);
        }

        List<Integer>[] frequencies = new ArrayList[nums.length + 1];

        for (int num : counts.keySet()) {
            int count = counts.get(num);
            if (frequencies[count] == null) {
                frequencies[count] = new ArrayList<>();
            }
            frequencies[count].add(num);
        }

        List<Integer> result = new ArrayList<>();
        for (int i = frequencies.length - 1; i > 0; i--) {
            if (frequencies[i] != null) {
                result.addAll(frequencies[i]);

                if (result.size() == k) {
                    break;
                }
            }
        }
        int[] topK = new int[k];
        for (int i = 0; i < k; i++) {
            topK[i] = result.get(i);
        }

        return topK;
    }
}
