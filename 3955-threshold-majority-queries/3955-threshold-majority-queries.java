import java.util.*;

public class Solution {

    // ────────────────── one compact helper ──────────────────
    private static final class Buckets {
        final int[] freq, head, prev, next, min;
        final int[] val;             // rev[]  (compressed → original value)
        int maxFreq = 0;

        Buckets(int distinct, int n, int[] val) {
            this.val = val;
            freq = new int[distinct];
            head = new int[n + 2];           // head[f] = first node id in bucket f
            prev = new int[distinct];
            next = new int[distinct];
            min  = new int[n + 2];

            Arrays.fill(head, -1);
            Arrays.fill(prev, -1);
            Arrays.fill(next, -1);
            Arrays.fill(min,  Integer.MAX_VALUE);
        }

        private void addToBucket(int x, int f) {               // insert at front
            prev[x] = -1;
            next[x] = head[f];
            if (head[f] != -1) prev[head[f]] = x;
            head[f]   = x;
            if (x < min[f]) min[f] = x;
        }
        private void removeFromBucket(int x, int f) {
            int p = prev[x], n = next[x];
            if (p != -1) next[p] = n;      else head[f] = n;
            if (n != -1) prev[n] = p;
            if (min[f] == x) {             // need new minimum
                int m = Integer.MAX_VALUE, h = head[f];
                while (h != -1) { m = Math.min(m, h); h = next[h]; }
                min[f] = m;
            }
            prev[x] = next[x] = -1;
        }

        void insertIndex(int compVal) {                // freq ↑
            int f = freq[compVal];
            if (f > 0) removeFromBucket(compVal, f);
            freq[compVal] = ++f;
            addToBucket(compVal, f);
            if (f > maxFreq) maxFreq = f;
        }
        void eraseIndex(int compVal) {                 // freq ↓
            int f = freq[compVal];
            removeFromBucket(compVal, f);
            freq[compVal] = --f;
            if (f > 0) addToBucket(compVal, f);
            while (maxFreq > 0 && head[maxFreq] == -1) maxFreq--;
        }

        int bestFreq() { return maxFreq; }
        int bestValue() { return maxFreq == 0 ? -1 : val[min[maxFreq]]; }
    }

    // ────────────────── main API ──────────────────
    public int[] subarrayMajority(int[] a, int[][] queries) {

        final int n = a.length, q = queries.length;
        final int B = (int) Math.sqrt(n);

        /* 1. sort queries in Mo order */
        Integer[] order = new Integer[q];
        for (int i = 0; i < q; i++) order[i] = i;
        Arrays.sort(order, (i, j) -> {
            int b1 = queries[i][0] / B, b2 = queries[j][0] / B;
            return b1 != b2 ? b1 - b2
                            : queries[i][1] - queries[j][1];
        });

        /* 2. coordinate-compress the array */
        int[] sorted = a.clone();
        Arrays.sort(sorted);
        Map<Integer, Integer> map = new HashMap<>();
        int id = 0;
        for (int v : sorted)
            map.putIfAbsent(v, id++);
        int[] rev = new int[id];           // compressed → original
        int[] comp = new int[n];           // original → compressed
        for (int i = 0; i < n; i++) {
            comp[i] = map.get(a[i]);
            rev[comp[i]] = a[i];
        }

        /* 3. Mo’s sweep with O(1) updates */
        Buckets bkt = new Buckets(id, n, rev);
        int[] ans = new int[q];
        Arrays.fill(ans, -1);

        int L = 0, R = -1;                 // current window [L, R]

        for (int qi : order) {
            int l = queries[qi][0], r = queries[qi][1], th = queries[qi][2];

            while (L > l) bkt.insertIndex(comp[--L]);
            while (R < r) bkt.insertIndex(comp[++R]);
            while (L < l) bkt.eraseIndex(comp[L++]);
            while (R > r) bkt.eraseIndex(comp[R--]);

            if (bkt.bestFreq() >= th) ans[qi] = bkt.bestValue();
        }
        return ans;
    }
}
