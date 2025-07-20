import java.util.*;

class Solution {

    static class Pair {
        int v, w;
        long d;
        Pair(int v, int w) { this.v = v; this.w = w; }
        Pair(long d, int v) { this.d = d; this.v = v; }
    }

    public int findMaxPathScore(int[][] edges, boolean[] online, long k) {
        int n = online.length;
        @SuppressWarnings("unchecked")
        ArrayList<Pair>[] g = new ArrayList[n];
        for (int i = 0; i < n; ++i) g[i] = new ArrayList<>();
        int hi = 0;
        for (int[] e : edges) {
            g[e[0]].add(new Pair(e[1], e[2]));
            hi = Math.max(hi, e[2]);
        }

        int ans = -1, lo = 0, r = hi;
        while (lo <= r) {
            int mid = (lo + r) >>> 1;
            if (reachable(mid, g, online, k)) {
                ans = mid;
                lo = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return ans;
    }

    private boolean reachable(int thr, ArrayList<Pair>[] g, boolean[] online, long k) {
        int n = g.length;
        long[] dist = new long[n];
        Arrays.fill(dist, Long.MAX_VALUE);
        dist[0] = 0;
        PriorityQueue<Pair> pq = new PriorityQueue<>(Comparator.comparingLong(p -> p.d));
        pq.add(new Pair(0L, 0));

        while (!pq.isEmpty()) {
            Pair cur = pq.poll();
            int u = cur.v;
            if (cur.d != dist[u]) continue;
            if (u == n - 1) return cur.d <= k;
            for (Pair e : g[u]) {
                if (e.w < thr) continue;
                if (e.v != n - 1 && !online[e.v]) continue;
                long nd = cur.d + e.w;
                if (nd < dist[e.v] && nd <= k) {
                    dist[e.v] = nd;
                    pq.add(new Pair(nd, e.v));
                }
            }
        }
        return false;
    }
}
