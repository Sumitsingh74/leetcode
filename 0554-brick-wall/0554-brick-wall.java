class Solution {
    public static int leastBricks(List<List<Integer>> wall) {
		int n=wall.size();
		final int[] ans = {n};
		HashMap<Integer,Integer> map=new HashMap<>();
		int d=0;
		for(List<Integer> list:wall){
			int l=0;
			for(Integer e:list){
				l+=e;
				map.put(l,map.getOrDefault(l,0)+1);
			}
			d=l;
		}
		map.remove(d);
		map.forEach((u,v)->{
			ans[0]=Math.min(ans[0],n-v);
		});

		return ans[0];
	}
}