class Solution {
    static long[]vp;static ArrayList<Integer>[]lists;static char[] str,str1;static long[]pre,suf;static int[]arr;static int[]brr;

	public static long stick(int ind,int j,int f,long g) {
		return 0;
	}
	public static void dfs(int node,int parent,int[]arr){
		intime[node]=++time;
		brr[node]=arr[node];
		for(Integer e:lists[node]){
			if(e==parent)continue;
			dfs(e,node,arr);
			brr[node]^=brr[e];
		}
		outime[node]=++time;
	}
	static int time;
	static int[]intime,outime;

	public static int minimumScore(int[] nums, int[][] edges) {
		int ans=Integer.MAX_VALUE;
		int n=nums.length;lists=new ArrayList[n];
		int mc=0;
		for(int i=0;i<n;i++){
			mc^=nums[i];
			lists[i]=new ArrayList<>();
		}
		for(int i=0;i<n-1;i++){
			int u=edges[i][0];int v=edges[i][1];
			lists[u].add(v);lists[v].add(u);
		}
		brr=new int[n];time=0;intime=new int[n];outime=new int[n];
		dfs(0,-1,nums);
		// dbg.print(intime,"   ");
		// dbg.print(outime,"  ");

		for(int i=0;i<n-1;i++){
			int u=edges[i][0];int v=edges[i][1];
			for(int j=i+1;j<n-1;j++){
				int u1=edges[j][0];int v1=edges[j][1];
				int an=-1;int an1=-1;
				if(intime[u]<intime[v]&&outime[u]>outime[v])an=v;
				else an=u;
				if(intime[u1]<intime[v1]&&outime[u1]>outime[v1])an1=v1;
				else an1=u1;
				int[]crr=new int[3];
				crr[0]=brr[an];
				crr[1]=brr[an1];
				if(intime[an]<intime[an1]&&outime[an]>outime[an1]){
					crr[0]^=brr[an1];
				}
				if(intime[an1]<intime[an]&&outime[an1]>outime[an]){
					crr[1]^=brr[an];
				}
				crr[2]=mc^crr[0]^crr[1];
				Arrays.sort(crr);
				// dbg.print(i,"   ",j,"  ",crr,"   ","  ",an,"  ",an1);
				ans=Math.min(ans,crr[2]-crr[0]);
			}
		}

		return ans;
	}
}