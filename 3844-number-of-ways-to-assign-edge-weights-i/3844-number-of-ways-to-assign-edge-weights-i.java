class Solution {

    static int maxdep;static int[]vt;static ArrayList<Integer>[]lists;
    static int MoD = 1000000007;static long[][]dp;
	public static void dfs1(int node,int par,int dep){
		if(lists[node].size()==1&&node!=1){
			maxdep=Math.max(maxdep,dep);
			return;
		}
		for(Integer e:lists[node]){
			if(e==par)continue;
			dfs1(e,node,dep+1);
		}
	}
	public static void dfs2(int node,int par,int dep){
		if(lists[node].size()==1&&node!=1){
			if(dep==maxdep)vt[node]=1;
			return;
		}
		for(Integer e:lists[node]){
			if(e==par)continue;
			dfs2(e,node,dep+1);
			vt[node]|=vt[e];
		}
	}

	public static void dfs3(int node,int par){
		if(lists[node].size()==1&&node!=1){
			dp[node][0]=1;
			return;
		}
		dp[node][0]=dp[node][1]=1;
		for(Integer e:lists[node]){
			if(e==par||vt[e]==0)continue;
			dfs3(e,node);
			long f=dp[e][1]+dp[e][0];f%=MoD;
            dp[node][0]*=f;
			dp[node][0]%=MoD;
			dp[node][1]*=f;
			dp[node][0]%=MoD;
            break;
		}
	}

    
    public int assignEdgeWeights(int[][] edges) {
  
        int n=edges.length+1;
        lists=new ArrayList[n+1];
        for(int i=0;i<=n;i++){
            lists[i]=new ArrayList<>();
        }
        for(int i=0;i<n-1;i++){
            int u=edges[i][0];int v=edges[i][1];
            lists[u].add(v);lists[v].add(u);
        }
        maxdep=0;
        dfs1(1,0,0);
        vt=new int[n+1];
        dfs2(1,0,0);
        dp=new long[n+1][2];
        dfs3(1,0);
        return (int)dp[1][1];
    }
}