class Solution {
    static ArrayList<Integer> []lists;

    public static   void dfs(int node,int par){
		parn[node]=par;
		for(Integer e:lists[node]){
			if(e==par)continue;
			dfs(e,node);
		}
	}
	static int[]parn,time;

	public static long dfs2(int node,int par,int l,int[] amount){
		long ans=0;
		if(l<time[node])ans+=amount[node];
		else if(l==time[node])ans+=(amount[node]/2);
		if(node!=0&&lists[node].size()==1){
			return ans;
		}

		long ans1=Integer.MIN_VALUE;
		for(Integer e:lists[node]){
			if(e==par)continue;
			long d=dfs2(e,node,l+1,amount);
			ans1=Math.max(ans1,d);
		}
		return ans+ans1;
	}

	public static int mostProfitablePath(int[][] edges, int bob, int[] amount) {
		int n=amount.length;parn=new int[n+1];time=new int[n+1];
		lists=new ArrayList[n+1];Arrays.fill(time,n+2);
		for(int i=0;i<=n;i++)lists[i]=new ArrayList();
		for(int[]a:edges){
			int u=a[0];int v=a[1];lists[u].add(v);lists[v].add(u);
		}
		dfs(0,-1);
		int node=bob;int l=0;
		while(node!=-1){
			time[node]=l++;
			node=parn[node];
		}
		long ans=dfs2(0,-1,0,amount);

		return (int) ans;
	}
}