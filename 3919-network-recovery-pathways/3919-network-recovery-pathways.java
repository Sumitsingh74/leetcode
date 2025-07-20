class Solution {
    static class pair { int a;int b;long w;int t;pair(int x, int y){this.a=x;this.b=y;}pair(int x, int y, int t){this.a=x;this.b=y;this.t=t;}pair(long w,int x){this.a=x;this.w=w;}public int getFirst(){return a;}public int getSecond() {return b;}@Override public String toString(){String str="";str="{ "+a+" "+b+"  "+"  "+w +"}";return str;}}
	//	public static class Tree<T> extends TreeSet<T> { public Tree() {super();}}

	static long[]vp;static ArrayList<pair>[]lists;static char[] str,str1;static long[]pre,suf;static int[]arr;static int[]brr;

    public static boolean dis(int n,int mid,long k,boolean[]online){
		long[]d=new long[n];
		Arrays.fill(d,Long.MAX_VALUE);
		PriorityQueue<pair> pq=new PriorityQueue<>(new Comparator<pair>() {
			@Override
			public int compare(pair o1, pair o2) {
				if(o1.w>o2.w)return 1;
				else if(o1.w==o2.w)return 0;
				else return -1;
			}
		});
		pq.add(new pair(0L,0));
		while(pq.size()>0){
			pair e=pq.poll();
			long w=e.w;int u=e.a;
			for(pair g:lists[u]){
				if(g.b>=mid&&online[g.a]){
					long w1=w+g.b;
					if(d[g.a]>w1){
						d[g.a]=w1;
						pq.add(new pair(w1,g.a));
					}
				}
			}
		}
        return d[n - 1] <= k;
    }
	public int findMaxPathScore(int[][] edges, boolean[] online, long k) {
		int ans=-1;
		int n=online.length;
		lists=new ArrayList[n+1];
		for(int i=0;i<n;i++)lists[i]=new ArrayList<>();
		for(int[]a:edges){
			int u=a[0],v=a[1],w=a[2];
			lists[u].add(new pair(v,w));
			// lists[v].add(new pair(u,w));
		}
		int l=1;int r=(int)1e9+1;
		while (l<=r){
			int mid=(l+r)>>>1;
			if(dis(n,mid,k,online)){
				ans=mid;l=mid+1;
			}else r=mid-1;
		}
		return ans;
	}
}