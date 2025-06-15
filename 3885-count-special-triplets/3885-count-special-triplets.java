class Solution {
        public int specialTriplets(int[] A) {
        int mod = 1_000_000_007, res = 0;
        Map<Integer, Integer> left = new HashMap<>(), right = new HashMap<>();
        for (int a : A) {
            right.put(a, right.getOrDefault(a, 0) + 1);
        }
        for (int a : A) {
            right.put(a, right.get(a) - 1);
            int ci = left.getOrDefault(a * 2, 0);
            int ck = right.getOrDefault(a * 2, 0);
            res = (int)((res + 1L * ci * ck) % mod);
            left.put(a, left.getOrDefault(a, 0) + 1);
        }
        return res;
    }
}