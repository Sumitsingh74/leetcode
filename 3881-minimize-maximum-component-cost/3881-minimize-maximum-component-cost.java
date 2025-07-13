class Solution {
    static class DisjointSet {
		int[]rank;int[]parent;int[]size;int com;
		public DisjointSet(int n) {
			rank=new int[n+1];parent=new int[n+1];size=new int[n+1];
			for(int i = 0; i <= n; ++i) {
				this.rank[i]=0;
				this.parent[i]=i;
				this.size[i]=1;
				this.com=n;
			}
		}
		public int findUPar(int node) {
			if (node == (Integer)this.parent[node]) {
				return node;
			} else {
				int ulp = this.findUPar((Integer)this.parent[node]);
				this.parent[node]=ulp;
				return (Integer)this.parent[node];
			}
		}
		public void unionByRank(int u, int v) {
			int ulp_u = this.findUPar(u);
			int ulp_v = this.findUPar(v);
			if (ulp_u != ulp_v) {
				if ((Integer)this.rank[ulp_u] < (Integer)this.rank[ulp_v]) {
					this.parent[ulp_u]= ulp_v;
				} else if ((Integer)this.rank[ulp_v] < (Integer)this.rank[ulp_u]) {
					this.parent[ulp_v]= ulp_u;
				} else {
					this.parent[ulp_v]= ulp_u;
					int rankU = (Integer)this.rank[ulp_u];
					this.rank[ulp_u]=( rankU + 1);
				}
			}
		}
		public void unionBySize(int u, int v) {
			int ulp_u = this.findUPar(u);
			int ulp_v = this.findUPar(v);
			if (ulp_u != ulp_v) {
				com--;
				if ((Integer)this.size[ulp_u] < (Integer)this.size[ulp_v]) {
					this.parent[ulp_u]=ulp_v;
					this.size[ulp_v]=(Integer)this.size[ulp_v] + (Integer)this.size[ulp_u];
				} else {
					this.parent[ulp_v]= ulp_u;
					this.size[ulp_u]= (Integer)this.size[ulp_u] + (Integer)this.size[ulp_v];
				}
			}
		}
	}
	public static int minCost(int n, int[][] edges, int k) {
		int ans=0;
		DisjointSet ds=new DisjointSet(n);
		Arrays.sort(edges, new Comparator<int[]>() {
			@Override
			public int compare(int[] o1, int[] o2) {
				if(o1[2]>o2[2])return 1;
				else if(o1[2]==o2[2])return 0;
				else return -1;
			}
		});
		// dbg.print(edges,"  ");
		for(int[]a:edges){
			if(ds.com<=k)break;
			int u=a[0],v=a[1],w=a[2];
			ds.unionBySize(u,v);
			ans=w;
		}
		return ans;
	}
}