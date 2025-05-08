 class pair { int a;int b;long w;int t;pair(int x, int y){this.a=x;this.b=y;}pair(int x, int y, long w){this.a=x;this.b=y;this.w=w;}pair(int x, int y, long w,int t){this.a=x;this.b=y;this.w=w;this.t=t;}public int getFirst(){return (int) a;}public int getSecond() {return (int) b;}}


class Solution {
   public static boolean isv(int i,int j,int n,int m){
        return i < n && j < m && i >= 0 && j >= 0;
    }

	public static int bfs(int n,int m , int[][]move){
		long[][][]arr=new long[n][m][2];
		for(int i=0;i<n;i++)for(int j=0;j<m;j++)Arrays.fill(arr[i][j],Integer.MAX_VALUE);


		PriorityQueue<pair> pq=new PriorityQueue<>(new Comparator<pair>() {
			@Override
			public int compare(pair o1, pair o2) {
				if(o1.w>o2.w)return 1;
				else if(o1.w==o2.w)return 0;
				else return -1;
			}
		});
		pq.add(new pair(0,0,0,0));
		arr[0][0][0]=0;

		while (!pq.isEmpty()){
			pair e=pq.poll();
			int i=e.a;int j=e.b;long w=e.w;
			int t=e.t;
			int dir[][] = {{-1,0} , {0,-1} , {0,1}, {1, 0}};
			for(int dire[] : dir) {
				int nrow = i + dire[0];
				int ncol = j + dire[1];
				if(isv(nrow,ncol,n,m)){

					long we=0;
					if(t==0){
						we=1+Math.max(move[nrow][ncol],w);
					}else we=2+Math.max(move[nrow][ncol],w);

					if(arr[nrow][ncol][t^1]>we){
						arr[nrow][ncol][t^1]=we;
						pq.add(new pair(nrow,ncol,we,t^1));
					}
				}

			}
		}

		return (int) Math.min(arr[n-1][m-1][0],arr[n-1][m-1][1]);
	}
	public static int minTimeToReach(int[][] moveTime) {
		long ans=0;
		int n=moveTime.length;int m=moveTime[0].length;
		
		ans=bfs(n,m,moveTime);


		return (int) ans;
	}
}