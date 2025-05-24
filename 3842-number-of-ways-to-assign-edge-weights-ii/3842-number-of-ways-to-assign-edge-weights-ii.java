class Solution {
    static int[][] ap;static int MoD = 1000000007;
    public static  long binpow(long  a, long  b,  long m) {a %= m;long res = 1;while (b > 0) {if ((b & 1)==1) res = res * a % m;a = a * a % m;b >>= 1;}return res%m;}
	
	static ArrayList<Integer>[] list;
	static int[] depth;

		public static void build(int n, ArrayList<Integer>[] listw) {
			list = new ArrayList[n + 1];
			for (int i = 0; i <= n; i++) {
				list[i] = new ArrayList<>();
			}
			for (int i = 0; i <= n; i++) {
				for (Integer e : listw[i]) {
					list[i].add(e);
				}
			}
			ap = new int[n + 1][18];
			depth = new int[n + 1];
			dfs(1, 0);
		}

		public static void dfs(int u, int par) {
			ap[u][0] = par;
			for (int i = 1; i <= 17; i++) {
				if (ap[u][i - 1] != 0) {
					ap[u][i] = ap[ap[u][i - 1]][i - 1];
				}
			}
			for (int v : list[u]) {
				if (v != par) {
					depth[v] = depth[u] + 1;
					dfs(v, u);
				}
			}
		}

		public static int find_kth_parent(int u, int k) {
			int count = 0;
			while (k != 0) {
				if (k % 2 == 1) {
					u = ap[u][count];
				}
				count++;
				k = k >> 1;
			}
			return u;
		}

		public static int lca(int node1, int node2) {
			if (depth[node1] > depth[node2]) {
				int temp = node1;
				node1 = node2;
				node2 = temp;
			}

			int diff = depth[node2] - depth[node1];
			node2 = find_kth_parent(node2, diff);

			if (node1 == node2) return node1;

			for (int i = 17; i >= 0; i--) {
				if (ap[node1][i] != ap[node2][i]) {
					node1 = ap[node1][i];
					node2 = ap[node2][i];
				}
			}
			return ap[node1][0];
		}
    static ArrayList<Integer>[]lists;

    public int[] assignEdgeWeights(int[][] edges, int[][] queries) {
        int n=edges.length+1;
			lists=new ArrayList[n+1];
			for(int i=0;i<=n;i++){
				lists[i]=new ArrayList<>();
			}
			for(int i=0;i<n-1;i++){
				int u=edges[i][0];int v=edges[i][1];
				lists[u].add(v);lists[v].add(u);
			}
        int q=queries.length;
        build(n,lists);
			int[]ans=new int[q];
			for(int i=0;i<q;i++){
				int u=queries[i][0];
				int v=queries[i][1];
                if(u==v)continue ;

				int par=lca(u,v);
				long[]u1=new long[2];
				long[]u2=new long[2];

				int l1=depth[u]-depth[par];
				int l2=depth[v]-depth[par];
				long e=binpow(2,l1-1,MoD);
				long e2=binpow(2,l2-1,MoD);

                long d=0;
				if(par==u){
					d=e2%MoD;
				}else if(par==v){
					d=e%MoD;
				}else {
					d=(e*e2%MoD)*2;
					d%=MoD;
				}
				ans[i]=(int)d;
			}
        return ans;
    }
}