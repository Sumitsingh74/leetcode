class Solution {
    static class pair { int a;int b;long w;int t;pair(int x, int y){this.a=x;this.b=y;}pair(int x, int y, long w){this.a=x;this.b=y;this.w=w;}pair(int x, int y, long w,int t){this.a=x;this.b=y;this.w=w;this.t=t;}public int getFirst(){return (int) a;}public int getSecond() {return (int) b;}@Override public String toString(){String str="";str="{ "+a+" "+b+"  "+"  "+w +"}";return str;}}
	
	static long[]vp;static ArrayList<pair>[]lists;

    private static boolean isValid(String grid[], int r, int c) {
			int m = grid.length, n = grid[0].length();
			return r >= 0 && r < m && c >= 0 && c < n &&grid[r].charAt(c)!= '#';
		}

    public int minMoves(String[] str) {
        int n=str.length;
        int m=str[0].length();
        long[][] dp=new long[n][m];
        lists=new ArrayList[26];

        if(str[n - 1].charAt(m-1) == '#') return -1;    

            for(int i=0;i<26;i++)lists[i]=new ArrayList<>();
            for(int i=0;i<n;i++){
                Arrays.fill(dp[i],Integer.MAX_VALUE);
                for(int j=0;j<m;j++){
                    if(str[i].charAt(j)!='.'&&str[i].charAt(j)!='#'){
                        int l=str[i].charAt(j)-'A';
                        lists[l].add(new pair(i,j));
                    }
                }
            }

            PriorityQueue<pair> pq=new PriorityQueue<>(new Comparator<pair>() {
                @Override
                public int compare(pair o1, pair o2) {
                    if(o1.w>o2.w)return 1;
                    else if (o1.w==o2.w) {
                        return 0;
                    }else return -1;
                }
            });
            int[][] dirs = {{0,1}, {1,0}, {0,-1}, {-1,0}};
            pq.add(new pair(0,0,0));
            dp[0][0]=0;
            HashSet<Integer> set1=new HashSet<>();
            while (!pq.isEmpty()){
					pair current = pq.poll();
                    if(current.w > dp[current.a][current.b]) continue;
					for (int[] dir : dirs) {

						int newRow=current.a;
						int newCol=current.b;
						if(str[newRow].charAt(newCol)!='.'&&str[newRow].charAt(newCol)!='#'){
							int l=str[newRow].charAt(newCol)-'A';
							if(!set1.contains(l)) {
								set1.add(l);
								ArrayList<pair> list = lists[l];
								for (pair e : list) {
									long newDist = dp[newRow][newCol];
									newRow = e.a;
									newCol = e.b;
									if (newDist < dp[newRow][newCol]) {
										dp[newRow][newCol] = newDist;
										pq.add(new pair(newRow, newCol, newDist));
									}
								}
							}
						}

						newRow = current.a + dir[0];
						newCol = current.b + dir[1];

						if (isValid(str, newRow, newCol)) {
							long newDist = current.w + 1;
							if (newDist < dp[newRow][newCol]) {
								dp[newRow][newCol] = newDist;
								pq.add(new pair(newRow, newCol, newDist));
							}
						}
					}
				}
            if(dp[n-1][m-1]==Integer.MAX_VALUE)return -1;
            return (int)dp[n-1][m-1];
    }
}