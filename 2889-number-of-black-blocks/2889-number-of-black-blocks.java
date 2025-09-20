class Solution {
    public static long h(int u,int v){
		return (long)((long)u*1e6+v);
	}
	public static long[] countBlackBlocks(int n, int m, int[][] coordinates) {
		long[]ans=new long[5];int d=coordinates.length;
		HashSet<Long> set1=new HashSet<>();
		for(int[]a:coordinates){
			int u=a[0];int v=a[1];
			set1.add(h(u,v));
		}
		for(int[]a:coordinates){
			int u=a[0];int v=a[1];
			if(u-1>=0&&v+1<m){
				int cou=0;
				if(set1.contains(h(u,v)))cou++;
				if(set1.contains(h(u-1,v)))cou++;
				if(set1.contains(h(u-1,v+1)))cou++;
				if(set1.contains(h(u,v+1)))cou++;
				ans[cou]++;
			}
			if(u+1<n&&v+1<m){
				int cou=0;
				if(set1.contains(h(u,v)))cou++;
				if(set1.contains(h(u+1,v)))cou++;
				if(set1.contains(h(u,v+1)))cou++;
				if(set1.contains(h(u+1,v+1)))cou++;
				ans[cou]++;
			}if(u+1<n&&v-1>=0){
				int cou=0;
				if(set1.contains(h(u,v)))cou++;
				if(set1.contains(h(u,v-1)))cou++;
				if(set1.contains(h(u+1,v-1)))cou++;
				if(set1.contains(h(u+1,v)))cou++;
				ans[cou]++;
			}if(u-1>=0&&v-1>=0){
				int cou=0;
				if(set1.contains(h(u,v)))cou++;
				if(set1.contains(h(u-1,v-1)))cou++;
				if(set1.contains(h(u,v-1)))cou++;
				if(set1.contains(h(u-1,v)))cou++;
				ans[cou]++;
			}
		}
		ans[2]/=2;ans[3]/=3;ans[4]/=4;
		ans[0]=(long)n*m-n-m+1-ans[1]-ans[2]-ans[3]-ans[4];
//		dbg.print(ans,"  ");


		return ans;
	}

}