class Solution {
    public int[] queryResults(int n, int[][] queries) {
		TreeMap<Integer, Integer> freq = new TreeMap();
		TreeSet<Integer> set=new TreeSet<>();
		int[]brr=new int[queries.length];
		HashMap<Integer,Integer> arr=new HashMap<>();
		for (int i = 0; i < queries.length ; i++) {
			int l=queries[i][0];int c=queries[i][1];
			if(!arr.containsKey(l)){
				arr.put(l,c);
				set.add(c);freq.put(c,freq.getOrDefault(c,0)+1);
			}else {
				int e=arr.getOrDefault(l,0);
				int d=freq.getOrDefault(e,0);
				if(d==1)set.remove(e);
				freq.put(e,d-1);
				arr.remove(l);
				arr.put(l,c);
				set.add(c);
				freq.put(c,freq.getOrDefault(c,0)+1);
			}
			brr[i]=set.size();
		}

		return brr;

	}
}