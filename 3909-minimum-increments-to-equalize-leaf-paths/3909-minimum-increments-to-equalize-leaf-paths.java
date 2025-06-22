class Solution {
        int res = 0;
    List<List<Integer>> G;

    public int minIncrease(int n, int[][] edges, int[] cost) {
        G = new ArrayList<>();
        for (int i = 0; i < n; i++) G.add(new ArrayList<>());
        for (int[] e : edges) {
            int u = e[0], v = e[1];
            G.get(u).add(v);
            G.get(v).add(u);
        }
        dfs(0, -1, cost);
        return res;
    }

    private long dfs(int i, int f, int[] cost) {
        List<Long> score = new ArrayList<>();
        long ma = 0;
        for (int j : G.get(i)) {
            if (j == f) continue;
            long val = dfs(j, i, cost);
            if (val > ma) ma = val;
            score.add(val);
        }
        if (score.isEmpty()) {
            return cost[i];
        }
        for (long val : score)
            if (val < ma)
                res++;
        return ma + cost[i];
    }
}