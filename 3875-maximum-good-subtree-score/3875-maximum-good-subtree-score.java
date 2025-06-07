class Solution {
    static final int MOD = 1_000_000_007;
	static List<Integer>[] tree;
	static int[] vals, valMask;
	static int n;
    static long[][]dp;
	private static void collectSubtree(int u, List<Integer> nodes) {
		nodes.add(u);
		for (int child : tree[u]) {
			collectSubtree(child, nodes);
		}
	}
	private static int fg(int val) {
		int mask = 0;
		while (val > 0) {
			int d = val % 10;
			if (((mask >> d) & 1) == 1) return -1;
			mask |= (1 << d);
			val /= 10;
		}
		return mask;
	}
	private static long stick(List<Integer> nodes, int idx, int digitMask, int sum) {
		if (idx == nodes.size()) return 0;
		if(dp[idx][digitMask]!=-1)return dp[idx][digitMask];
		long skip = stick(nodes, idx + 1, digitMask, sum);
		int node = nodes.get(idx);
		int mask = valMask[node];
		long take = 0;
		if (mask != -1 && (digitMask & mask) == 0) {
			take = Math.max(take,vals[node]+stick(nodes, idx + 1, digitMask | mask, sum));
		}
		return dp[idx][digitMask]=Math.max(skip, take);
	}
	public static int goodSubtreeSum(int[] val, int[] par) {
		long ans=0;
		n = val.length;
		vals=new int[n];
		for(int i=0;i<n;i++)vals[i]=val[i];
		tree = new ArrayList[n];
		valMask = new int[n];
		for (int i = 0; i < n; i++) tree[i] = new ArrayList<>();
		for (int i = 1; i < n; i++) {
			tree[par[i]].add(i);
		}
		long[] maxScore = new long[n];
		for(int i=0;i<n;i++)valMask[i]=fg(val[i]);
		for (int u = 0; u < n; u++) {
			List<Integer> subtree = new ArrayList<>();
			collectSubtree(u, subtree);
			dp=new long[subtree.size()+1][1<<10];
			for(int i=0;i<=subtree.size();i++)Arrays.fill(dp[i],-1);
			maxScore[u] = stick(subtree, 0, 0, 0);
		}
		long result = 0;
		for (long score : maxScore) {
			result = (result + score) % MOD;
		}
		return (int) result;
	}
}