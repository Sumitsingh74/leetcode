import java.util.*;

class Solution {
    public int maxWalls(int[] robots, int[] distance, int[] walls) {
        int n = robots.length;

        // Pair robots with distances and sort
        int[][] rpair = new int[n][2];
        for (int i = 0; i < n; i++) {
            rpair[i][0] = robots[i];
            rpair[i][1] = distance[i];
        }
        Arrays.sort(rpair, (a, b) -> Integer.compare(a[0], b[0]));
        int[] r = new int[n];
        int[] d = new int[n];
        for (int i = 0; i < n; i++) {
            r[i] = rpair[i][0];
            d[i] = rpair[i][1];
        }

        Arrays.sort(walls);

        // Count walls at robot positions
        int base = 0;
        for (int i = 0; i < n; i++) {
            int idx = Arrays.binarySearch(walls, r[i]);
            if (idx >= 0) base++;
        }

        // Tail walls
        int leftTail = countRange(walls, r[0] - d[0], r[0] - 1);
        int rightTail = countRange(walls, r[n - 1] + 1, r[n - 1] + d[n - 1]);

        // Precompute segment ranges
        int segs = n - 1;
        int[] A = new int[Math.max(0, segs)];
        int[] B = new int[Math.max(0, segs)];
        int[] C = new int[Math.max(0, segs)];
        for (int i = 0; i < segs; i++) {
            int segL = r[i] + 1;
            int segR = r[i + 1] - 1;
            if (segL > segR) {
                A[i] = B[i] = C[i] = 0;
                continue;
            }
            int A_high = Math.min(segR, r[i] + d[i]);
            A[i] = countRange(walls, segL, A_high);

            int B_low = Math.max(segL, r[i + 1] - d[i + 1]);
            B[i] = countRange(walls, B_low, segR);

            int C_low = Math.max(segL, r[i + 1] - d[i + 1]);
            int C_high = Math.min(segR, r[i] + d[i]);
            C[i] = countRange(walls, C_low, C_high);
        }

        // DP initialization
        int[][] dp = new int[n][2];
        Arrays.fill(dp[0], Integer.MIN_VALUE / 4);
        dp[0][0] = base + leftTail; // first fires left
        dp[0][1] = base;            // first fires right

        // DP transitions
        for (int i = 0; i < n - 1; i++) {
            Arrays.fill(dp[i + 1], Integer.MIN_VALUE / 4);
            for (int choice = 0; choice <= 1; choice++) {
                int cur = dp[i][choice];
                if (cur < 0) continue;

                // Next fires left
                int addIfNextLeft = (choice == 1)
                        ? A[i] + B[i] - C[i]
                        : B[i];
                dp[i + 1][0] = Math.max(dp[i + 1][0], cur + addIfNextLeft);

                // Next fires right
                int addIfNextRight = (choice == 1) ? A[i] : 0;
                dp[i + 1][1] = Math.max(dp[i + 1][1], cur + addIfNextRight);
            }
        }

        // Final result
        int res;
        if (n == 1) {
            res = Math.max(dp[0][0], dp[0][1] + rightTail);
        } else {
            res = Math.max(dp[n - 1][0], dp[n - 1][1] + rightTail);
        }
        return res;
    }

    // Count walls in range [L, R]
    private static int countRange(int[] arr, long L, long R) {
        if (L > R || arr.length == 0) return 0;
        int leftIdx = lowerBound(arr, L);
        int rightIdx = upperBound(arr, R);
        return Math.max(0, rightIdx - leftIdx);
    }

    private static int lowerBound(int[] a, long x) {
        int l = 0, r = a.length;
        while (l < r) {
            int m = (l + r) >>> 1;
            if (a[m] < x) l = m + 1;
            else r = m;
        }
        return l;
    }

    private static int upperBound(int[] a, long x) {
        int l = 0, r = a.length;
        while (l < r) {
            int m = (l + r) >>> 1;
            if (a[m] <= x) l = m + 1;
            else r = m;
        }
        return l;
    }
}