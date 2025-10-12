class Solution {
    public long sumOfAncestors(int n, int[][] edges, int[] nums) {
    int mx = 1;
    for (int v : nums) if (v > mx) mx = v;
    int M = Math.max(2, mx);
    int[] spf = new int[M + 1];
    for (int i = 2; i <= M; ++i) {
        if (spf[i] == 0) {
            spf[i] = i;
            if ((long)i * i <= M) {
                for (int j = i * i; j <= M; j += i) if (spf[j] == 0) spf[j] = i;
            }
        }
    }
    long[] sf = new long[n];
    for (int i = 0; i < n; ++i) {
        int x = nums[i];
        long res = 1L;
        while (x > 1) {
            int p = spf[x];
            if (p == 0) p = x;
            int cnt = 0;
            while (x % p == 0) { x /= p; ++cnt; }
            if ((cnt & 1) == 1) res *= p;
        }
        sf[i] = res;
    }
    List<Integer>[] g = new ArrayList[n];
    for (int i = 0; i < n; ++i) g[i] = new ArrayList<>();
    for (int[] e : edges) {
        g[e[0]].add(e[1]);
        g[e[1]].add(e[0]);
    }
    long ans = 0L;
    Map<Long, Integer> freq = new HashMap<>();
    Deque<int[]> st = new ArrayDeque<>();
    st.push(new int[]{0, -1, 0}); 
    while (!st.isEmpty()) {
        int[] cur = st.pop();
        int u = cur[0], p = cur[1], state = cur[2];
        if (state == 0) {
            if (u != 0) {
                ans += freq.getOrDefault(sf[u], 0);
            }
            freq.put(sf[u], freq.getOrDefault(sf[u], 0) + 1);
            st.push(new int[]{u, p, 1});
            for (int v : g[u]) if (v != p) st.push(new int[]{v, u, 0});
        } else {
            int c = freq.getOrDefault(sf[u], 0) - 1;
            if (c == 0) freq.remove(sf[u]); else freq.put(sf[u], c);
        }
    }
    return ans;
}
// public long sumOfAncestors(int n, int[][] edges, int[] nums) {
        
//     }
}