class Solution {
     public long countNoZeroPairs(long n) {
        String s = Long.toString(n);
        int len = s.length();
        int[] target = new int[len];
        for (int i = 0; i < len; i++) target[i] = s.charAt(len - 1 - i) - '0';

        Map<String, Long> memo = new HashMap<>();

        return solve(0, 0, len, target, memo);
    }

    private long solve(int pos, int carry, int len, int[] target, Map<String, Long> memo) {
        if (pos == len) return carry == 0 ? 1 : 0;

        String key = pos + "-" + carry;
        if (memo.containsKey(key)) return memo.get(key);

        long total = 0;
        for (int len1 = 1; len1 <= len; len1++) {
            for (int len2 = 1; len2 <= len; len2++) {
                total += dfs(pos, carry, len1, len2, target, memo);
            }
        }

        memo.put(key, total);
        return total;
    }

    private long dfs(int pos, int carry, int len1, int len2, int[] target, Map<String, Long> memo) {
        if (pos == target.length) return carry == 0 ? 1 : 0;

        String key = pos + "-" + carry + "-" + len1 + "-" + len2;
        if (memo.containsKey(key)) return memo.get(key);

        long total = 0;
        int[] range1 = (pos < len1) ? getRange() : new int[]{0};
        int[] range2 = (pos < len2) ? getRange() : new int[]{0};

        for (int a : range1) {
            for (int b : range2) {
                int sum = a + b + carry;
                int digit = sum % 10;
                int nextCarry = sum / 10;
                if (digit == target[pos]) {
                    total += dfs(pos + 1, nextCarry, len1, len2, target, memo);
                }
            }
        }

        memo.put(key, total);
        return total;
    }

    private int[] getRange() {
        int[] arr = new int[9];
        for (int i = 0; i < 9; i++) arr[i] = i + 1;
        return arr;
    }
}