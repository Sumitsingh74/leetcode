class Solution {
    static long[]vp;static ArrayList<Integer>[]lists;static char[] str,str1;static long[]pre,suf;static int[]arr;static int[]brr;

    static long[][]dp;
    static long max,ans;
	public static void dfs(int node,int par,int[]cost){
		pre[node]=cost[node];
		if(par>=0)pre[node]+=pre[par];
		if(lists[node].size()==1&&node!=0){
			dp[node][0]=cost[node];dp[node][1]=cost[node];
			return;
		}
		dp[node][0]=Long.MAX_VALUE;
		for(Integer e:lists[node]){
			if(e==par)continue;;
			dfs(e,node,cost);
			dp[node][0]=Math.min(dp[e][0],dp[node][0]);
			dp[node][1]=Math.max(dp[e][1],dp[node][1]);
		}
		dp[node][0]+=cost[node];
		dp[node][1]+=cost[node];
	}
	public static void dfs1(int node,int par,int[]cost){
		if(dp[node][0]==dp[node][1]){
			long d=Math.max(dp[0][0],dp[0][1]);
			long e=Math.max(dp[node][0],dp[node][1])+pre[node]-cost[node];
			if(e!=d)ans++;
			return;
		}
		for(Integer e:lists[node]){
			if(e==par)continue;
			dfs1(e,node,cost);
		}
	}
    public static int minIncrease(int n, int[][] edges, int[] cost) {

		lists=new ArrayList[n+1];for(int i=0;i<=n;i++)lists[i]=new ArrayList<>();
		for(int[]a:edges){
			int u=a[0];int v=a[1];
			lists[u].add(v);lists[v].add(u);
		}
		dp=new long[n+1][2];
		pre=new long[n+1];
		max=0;ans=0;
		dfs(0,-1,cost);
		dfs1(0,-1,cost);

		return (int) ans;
	}


}