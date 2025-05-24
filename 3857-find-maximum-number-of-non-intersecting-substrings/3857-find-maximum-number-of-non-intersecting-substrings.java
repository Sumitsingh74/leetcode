class Solution {
    
    private static int recur(int i, int n, String word) {
		if (i >= n) return 0;
		if (vp[i] != -1) return (int)vp[i];

		int ans = recur(i + 1, n, word);

		char c = word.charAt(i);
		Integer end = set11.get(c).higher(i + 2);

		if (end != null) {
			if (end - i + 1 >= 4) {
				ans = Math.max(ans, 1 + recur(end + 1, n, word));
			}
		}

		return  (int)(vp[i] =ans);
	}
	static Map<Character, TreeSet<Integer>> set11;
    static long[]vp;
	public static int maxSubstrings(String word) {
			int n = word.length();
			set11 = new HashMap<>();

			for (int i = 0; i < n; i++) {
				if(!set11.containsKey(word.charAt(i)))set11.put(word.charAt(i), new TreeSet<>());
				set11.get(word.charAt(i)).add(i);
			}
			
			vp=new long[word.length()];Arrays.fill(vp,-1);
			return recur(0, n, word);
		}
}