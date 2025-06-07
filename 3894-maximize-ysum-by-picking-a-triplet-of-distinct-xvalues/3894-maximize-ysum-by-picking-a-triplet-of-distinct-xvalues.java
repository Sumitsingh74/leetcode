class Solution {
    public static int maxSumDistinctTriplet(int[] x, int[] y) {
		long ans=0;
		HashMap<Integer,Integer> map=new HashMap();TreeSet<Integer> set1=new TreeSet<>();
	
    for(int i=0;i<x.length;i++){
			int l=x[i];int r=y[i];
			set1.add(l);
			int c=map.getOrDefault(l,0);
			map.put(l,Math.max(c,r));
		}
		ArrayList<Integer> list=new ArrayList<>();
		for(Integer e:set1)list.add(map.getOrDefault(e,0));
		Collections.sort(list,Collections.reverseOrder());
//		dbg.print(map);
//		dbg.print(list);
		if(list.size()>=3){
			ans=list.get(0)+list.get(1)+list.get(2);
		}else return -1;
		return (int) ans;
	}

}