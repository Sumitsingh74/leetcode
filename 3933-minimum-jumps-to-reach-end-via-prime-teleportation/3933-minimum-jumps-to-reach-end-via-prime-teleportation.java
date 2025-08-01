import java.util.*;

class Solution {
    private static final int MAX = 1_000_001;
    private static final boolean[] prime = new boolean[MAX];
    static {
        Arrays.fill(prime, true);
        prime[0] = prime[1] = false;
        for (int i = 2; i * i < MAX; i++)
            if (prime[i])
                for (int j = i * i; j < MAX; j += i) prime[j] = false;
    }

    public int minJumps(int[] nums) {
        int n = nums.length, maxVal = 0;
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            maxVal = Math.max(maxVal, nums[i]);
            map.computeIfAbsent(nums[i], k -> new ArrayList<>()).add(i);
        }

        int[] dist = new int[n];
        Arrays.fill(dist, -1);
        dist[0] = 0;
        Queue<Integer> q = new ArrayDeque<>();
        q.add(0);
        boolean[] used = new boolean[MAX];

        while (!q.isEmpty()) {
            int i = q.poll();
            if (i == n - 1) return dist[i];

            if (i > 0 && dist[i - 1] == -1) { dist[i - 1] = dist[i] + 1; q.add(i - 1); }
            if (i + 1 < n && dist[i + 1] == -1) { dist[i + 1] = dist[i] + 1; q.add(i + 1); }

            int v = nums[i];
            if (!prime[v] || used[v]) continue;
            used[v] = true;

            for (long m = v; m <= maxVal; m += v) {
                List<Integer> idxs = map.get((int)m);
                if (idxs == null) continue;
                for (int j : idxs)
                    if (dist[j] == -1) { dist[j] = dist[i] + 1; q.add(j); }
            }
        }
        return -1;
    }
}
