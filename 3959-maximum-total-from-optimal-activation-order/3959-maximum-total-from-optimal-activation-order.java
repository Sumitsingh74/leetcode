class Solution {
    static class pair { int a;int b;long w;int t;pair(int x, int y){this.a=x;this.b=y;}pair(int x, int y, int t){this.a=x;this.b=y;this.t=t;}pair(long w,int x){this.a=x;this.w=w;}public int getFirst(){return a;}public int getSecond() {return b;}@Override public String toString(){String str="";str="{ "+a+" "+b+"  "+"  "+w +"}";return str;}}
	
    public static long maxTotal(int[] value, int[] limit) {
		long ans=0;
		ArrayList<pair> list=new ArrayList<>();
		for(int i=0;i<value.length;i++){
			list.add(new pair(value[i],limit[i]));
		}
		Collections.sort(list, new Comparator<pair>() {
			@Override
			public int compare(pair o1, pair o2) {
				if(o1.b>o2.b)return 1;
				else if(o1.b==o2.b){
					if(o1.a<o2.a)return 0;
					else return -1;
				}
				else return -1;
			}
		});
//		dbg.print(list);
		PriorityQueue<Integer> pq=new PriorityQueue<>();
		int cou=0;HashSet<Integer> set=new HashSet<>();
		for(pair e:list){
			while (!pq.isEmpty()&&pq.peek()<=cou){
				set.add(cou);
				cou--;pq.poll();
			}
			if(set.contains(e.b))continue;
			if(e.b>cou){
				ans+=e.a;
				cou++;
				pq.add(e.b);
			}
		}

		return ans;
	}
}