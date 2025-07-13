class Solution {
    static int n, time = 0;
	static List<Integer>[] adj;
	static int[] disc, low, parent;
	static boolean[] visited, isArt;
	static List<List<Integer>> list;

	static void dfs(int u,int p) {
		visited[u] = true;
		disc[u] = low[u] = ++time;
		int children = 0;
		for (int v : adj[u]) {
			if (p==v)continue;
			if (!visited[v]) {
				parent[v] = u;
				children++;
				dfs(v,u);
				low[u] = Math.min(low[u], low[v]);
				if (low[v] >disc[u]){
					List<Integer> tem=new ArrayList<>();
					tem.add(u);tem.add(v);
					list.add(tem);
				}
			} else if (v != parent[u]) {
				low[u] = Math.min(low[u], disc[v]);
			}
		}
		
	}
	public List<List<Integer>> criticalConnections(int n, List<List<Integer>> connections) {
		adj = new ArrayList[n];
		for (int i = 0; i < n; i++) adj[i] = new ArrayList<>();
		for (List<Integer> c:connections) {
			int u=c.get(0);int v=c.get(1);
			adj[u].add(v);adj[v].add(u);
		}
		list=new ArrayList<>();
		disc = new int[n];
		low = new int[n];
		parent = new int[n];
		Arrays.fill(parent, -1);
		visited = new boolean[n];
		isArt = new boolean[n];
		dfs(0,parent[0]);
		return list;
	}
}