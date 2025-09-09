class Solution {
        public int minOperations(String s) {
        int res = 0, n = s.length();
        for (int i = 0; i < n; ++i) {
            res = Math.max(res, ('a' + 26 - s.charAt(i)) % 26);
        }
        return res;
    }
}