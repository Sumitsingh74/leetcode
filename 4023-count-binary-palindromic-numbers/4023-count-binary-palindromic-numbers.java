public class Solution {
    private long makePal(long half, int bits) {
        long x = half << (bits / 2);
        for (int i = 0; i < bits / 2; i++) {
            if ((x & (1L << (bits - i - 1))) != 0) x |= (1L << i);
        }
        return x;
    }

    public int countBinaryPalindromes(long n) {
        if (n == 0) return 1;
        int bc = 64 - Long.numberOfLeadingZeros(n);
        long ans = 1;  

        for (int b = 1; b < bc; b++) {
            ans += (1L << ((b + 1) / 2)) / 2;  
        }

        long l = 1L << ((bc - 1) / 2);
        long r = (1L << ((bc + 1) / 2)) - 1;
        long best = 0;

        while (l <= r) {
            long mid = (l + r) >>> 1;
            if (makePal(mid, bc) <= n) {
                best = Math.max(best, mid);
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }

        if (best != 0) ans += best - (1L << ((bc - 1) / 2)) + 1;
        return (int) ans;
    }
}
